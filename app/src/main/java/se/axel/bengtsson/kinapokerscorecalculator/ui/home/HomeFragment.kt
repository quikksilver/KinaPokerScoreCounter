package se.axel.bengtsson.kinapokerscorecalculator.ui.home

import android.R.attr.checked
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import se.axel.bengtsson.kinapokerscorecalculator.KinaPoker
import se.axel.bengtsson.kinapokerscorecalculator.Player
import se.axel.bengtsson.kinapokerscorecalculator.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
  private val TAG = "kpsc"
  private var _binding: FragmentHomeBinding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    //TODO: Check if need to get anything from the shared pref.

    val homeViewModel =
      ViewModelProvider(this).get(HomeViewModel::class.java)

    _binding = FragmentHomeBinding.inflate(inflater, container, false)

    val root: View = binding.root

    // Set Number of players listner
    val numberOfPlayers = binding.playersRadio
    numberOfPlayers.setOnCheckedChangeListener { group:RadioGroup, checkedId:Int -> Log.d(
      TAG,
      "asd $group  ${group.id} ID $checkedId ID ${group.checkedRadioButtonId} R ${se.axel.bengtsson.kinapokerscorecalculator.R.id.four}"
    )
      when (checkedId) {
        se.axel.bengtsson.kinapokerscorecalculator.R.id.one -> {
          Log.d(TAG, "chose 1 players")
          //setVisibilityOnGameTypeSpinner(View.VISIBLE, View.GONE, View.GONE, View.GONE)
          homeViewModel.setNumberOfPlayer(1)
        }
        se.axel.bengtsson.kinapokerscorecalculator.R.id.two -> {
          Log.d(TAG, "chose 2 players")
          //setVisibilityOnGameTypeSpinner(View.VISIBLE, View.VISIBLE, View.GONE, View.GONE)
          homeViewModel.setNumberOfPlayer(2)
        }
        se.axel.bengtsson.kinapokerscorecalculator.R.id.three -> {
          Log.d(TAG, "chose 3 players")
          //setVisibilityOnGameTypeSpinner(View.VISIBLE, View.VISIBLE, View.VISIBLE, View.GONE)
          homeViewModel.setNumberOfPlayer(3)
        }
        se.axel.bengtsson.kinapokerscorecalculator.R.id.four -> {
          Log.d(TAG, "chose 4 players")
          //setVisibilityOnGameTypeSpinner(View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE)
          homeViewModel.setNumberOfPlayer(4)
        }
      }

    }
    // Set text
    val playerText: TextView = binding.players
    homeViewModel.numberOfPlayer.observe(viewLifecycleOwner) {
      playerText.text = "Players $it"
    }
    // Game Type
    val youText: TextView = binding.you
    homeViewModel.kinaPoker.observe(viewLifecycleOwner) {
      youText.visibility = isVisible(it, Player.You)
    }
    val youSpinner: Spinner = binding.youSpinner
    homeViewModel.kinaPoker.observe(viewLifecycleOwner) {
      youSpinner.visibility = isVisible(it, Player.You)
    }
    val leftText: TextView = binding.left
    homeViewModel.kinaPoker.observe(viewLifecycleOwner) {
      leftText.visibility = isVisible(it, Player.Left)
    }
    val leftSpinner: Spinner = binding.leftSpinner
    homeViewModel.kinaPoker.observe(viewLifecycleOwner) {
      leftSpinner.visibility = isVisible(it, Player.Left)
    }
    val oppositeText: TextView = binding.opposite
    homeViewModel.kinaPoker.observe(viewLifecycleOwner) {
      oppositeText.visibility = isVisible(it, Player.Opposite)
    }
    val oppositeSpinner: Spinner = binding.oppositeSpinner
    homeViewModel.kinaPoker.observe(viewLifecycleOwner) {
      oppositeSpinner.visibility = isVisible(it, Player.Opposite)
    }
    val rightText: TextView = binding.right
    homeViewModel.kinaPoker.observe(viewLifecycleOwner) {
      rightText.visibility = isVisible(it, Player.Right)
    }
    val rightSpinner: Spinner = binding.rightSpinner
    homeViewModel.kinaPoker.observe(viewLifecycleOwner) {
      rightSpinner.visibility = isVisible(it, Player.Right)
    }
    return root
  }

  private fun isVisible(kp: KinaPoker, player:Player): Int {
    return if (kp != null && kp.isPlayerPlaying(player)) {
      VISIBLE
    } else {
      GONE
    }
  }

  override fun onDestroyView() {
     super.onDestroyView()
     _binding = null
  }

  //fun onSelectPlayersButtonClicked(view: View) {}
}
