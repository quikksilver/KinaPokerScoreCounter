package se.axel.bengtsson.kinapokerscorecalculator.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import se.axel.bengtsson.kinapokerscorecalculator.KinaPoker
import se.axel.bengtsson.kinapokerscorecalculator.PlayType
import se.axel.bengtsson.kinapokerscorecalculator.Player
import se.axel.bengtsson.kinapokerscorecalculator.databinding.FragmentHomeBinding
import se.axel.bengtsson.kinapokerscorecalculator.ui.common.ScoreShower


class HomeFragment : Fragment() {
  private val _TAG = "kpsc"
  private var _binding: FragmentHomeBinding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!
  private val playTypeSpinners: MutableList<PlayTypeSpinner> = mutableListOf()
  private lateinit var scoreShower: ScoreShower

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    //TODO: Check if need to get anything from the shared pref.
    //TODO: https://stackoverflow.com/questions/51043428/handling-back-button-in-android-navigation-component

    val kinaPokerViewModel: KinaPokerViewModel by activityViewModels()
      //ViewModelProvider(this).get(KinaPokerViewModel::class.java)

    _binding = FragmentHomeBinding.inflate(inflater, container, false)

    val root: View = binding.root

    // Set Number of players listner
    val numberOfPlayers = binding.playersRadio
    numberOfPlayers.setOnCheckedChangeListener { group:RadioGroup, checkedId:Int ->
      Log.d(_TAG,
      "asd $group  ${group.id} ID $checkedId ID ${group.checkedRadioButtonId} R ${se.axel.bengtsson.kinapokerscorecalculator.R.id.four}"
      )
      resetSpinners()
      when (checkedId) {
        se.axel.bengtsson.kinapokerscorecalculator.R.id.one -> {
          Log.d(_TAG, "chose 1 players")
          kinaPokerViewModel.setNumberOfPlayer(1)
        }
        se.axel.bengtsson.kinapokerscorecalculator.R.id.two -> {
          Log.d(_TAG, "chose 2 players")
          kinaPokerViewModel.setNumberOfPlayer(2)
        }
        se.axel.bengtsson.kinapokerscorecalculator.R.id.three -> {
          Log.d(_TAG, "chose 3 players")
          kinaPokerViewModel.setNumberOfPlayer(3)
        }
        se.axel.bengtsson.kinapokerscorecalculator.R.id.four -> {
          Log.d(_TAG, "chose 4 players")
          kinaPokerViewModel.setNumberOfPlayer(4)
        }
      }
      setDefaultType(kinaPokerViewModel)
    }
    val radioButtons = arrayOf(binding.one, binding.two, binding.three, binding.four)
    // Set text
    val playerText: TextView = binding.players
    kinaPokerViewModel.numberOfPlayer.observe(viewLifecycleOwner) {
      Log.d(_TAG, "Number of player changed $it")
      playerText.text = "Players $it"
      numberOfPlayers.check(radioButtons[it - 1].id)
    }
    // Game Type
    val youText: TextView = binding.you
    kinaPokerViewModel.kinaPoker.observe(viewLifecycleOwner) {
      youText.visibility = isVisible(it, Player.You)
    }
    val youSpinner: Spinner = binding.youSpinner
    playTypeSpinners.add(PlayTypeSpinner(Player.You,kinaPokerViewModel,this.requireContext(), youSpinner))
    kinaPokerViewModel.kinaPoker.observe(viewLifecycleOwner) {
      youSpinner.visibility = isVisible(it, Player.You)
    }
    val leftText: TextView = binding.left
    kinaPokerViewModel.kinaPoker.observe(viewLifecycleOwner) {
      leftText.visibility = isVisible(it, Player.Left)
    }
    val leftSpinner: Spinner = binding.leftSpinner
    playTypeSpinners.add(PlayTypeSpinner(Player.Left,kinaPokerViewModel,this.requireContext(), leftSpinner))
    kinaPokerViewModel.kinaPoker.observe(viewLifecycleOwner) {
      leftSpinner.visibility = isVisible(it, Player.Left)
    }
    val oppositeText: TextView = binding.opposite
    kinaPokerViewModel.kinaPoker.observe(viewLifecycleOwner) {
      oppositeText.visibility = isVisible(it, Player.Opposite)
    }
    val oppositeSpinner: Spinner = binding.oppositeSpinner
    playTypeSpinners.add(PlayTypeSpinner(Player.Opposite,kinaPokerViewModel,this.requireContext(), oppositeSpinner))
    kinaPokerViewModel.kinaPoker.observe(viewLifecycleOwner) {
      oppositeSpinner.visibility = isVisible(it, Player.Opposite)
    }
    val rightText: TextView = binding.right
    kinaPokerViewModel.kinaPoker.observe(viewLifecycleOwner) {
      rightText.visibility = isVisible(it, Player.Right)
    }
    val rightSpinner: Spinner = binding.rightSpinner
    playTypeSpinners.add(PlayTypeSpinner(Player.Right,kinaPokerViewModel,this.requireContext(), rightSpinner))
    kinaPokerViewModel.kinaPoker.observe(viewLifecycleOwner) {
      rightSpinner.visibility = isVisible(it, Player.Right)
    }

    scoreShower = ScoreShower(binding.totalScore, viewLifecycleOwner, kinaPokerViewModel)

    val nextButton: Button = binding.next
    kinaPokerViewModel.isPlayTypeDone.observe(viewLifecycleOwner) {
      nextButton.isEnabled = it
    }
    nextButton.setOnClickListener {

      val navController = findNavController()
      navController.popBackStack()
      navController.navigate(se.axel.bengtsson.kinapokerscorecalculator.R.id.nav_hand1)

    }
    return root
  }

  private fun setDefaultType(kinaPokerViewModel: KinaPokerViewModel) {
    // Set default all to playing
    Player.values().filter { kinaPokerViewModel.kinaPoker.value?.isPlayerInTheRound(it) == true }
      .forEach {
        kinaPokerViewModel.kinaPoker.value?.setPlayersPlayType(it, PlayType.Play)
      }
    kinaPokerViewModel.updateModel()
  }

  private fun resetSpinners() {
    playTypeSpinners.forEach {it.reset()}
  }

  private fun isVisible(kp: KinaPoker, player:Player): Int {
    return if (kp.isPlayerInTheRound(player)) {
      VISIBLE
    } else {
      GONE
    }
  }


  override fun onDestroyView() {
     super.onDestroyView()
     _binding = null
  }

  override fun onResume() {
    super.onResume()
    val kinaPokerViewModel: KinaPokerViewModel by activityViewModels()
    kinaPokerViewModel.setNumberOfPlayer(4)
    setDefaultType(kinaPokerViewModel)
    resetSpinners()
  }
}
