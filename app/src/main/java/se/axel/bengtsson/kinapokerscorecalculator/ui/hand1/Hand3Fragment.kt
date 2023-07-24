package se.axel.bengtsson.kinapokerscorecalculator.ui.hand1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import se.axel.bengtsson.kinapokerscorecalculator.BonusType
import se.axel.bengtsson.kinapokerscorecalculator.Hand
import se.axel.bengtsson.kinapokerscorecalculator.Player
import se.axel.bengtsson.kinapokerscorecalculator.R
import se.axel.bengtsson.kinapokerscorecalculator.databinding.FragmentHand3Binding
import se.axel.bengtsson.kinapokerscorecalculator.ui.common.BonusChooser
import se.axel.bengtsson.kinapokerscorecalculator.ui.common.ScoreShower
import se.axel.bengtsson.kinapokerscorecalculator.ui.home.KinaPokerViewModel

class Hand3Fragment : Fragment() {
  private val hand: Hand = Hand.Hand3
  private var _binding: FragmentHand3Binding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  private val placeChoosers: MutableList<BonusChooser> = mutableListOf()
  private lateinit var scoreShower: ScoreShower

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val kinaPokerViewModel: KinaPokerViewModel by activityViewModels()
    kinaPokerViewModel.updateModel()
    //val kinaPokerViewModel =
    //  ViewModelProvider(this).get(KinaPokerViewModel::class.java)
    Log.d("kpsc", "$hand: Number of players ${kinaPokerViewModel.numberOfPlayer.value}")
    _binding = FragmentHand3Binding.inflate(inflater, container, false)
    val root: View = binding.root

    // Add places
    placeChoosers.add(BonusChooser(
      hand,
      BonusType.Win,
      arrayOf(binding.hand1You1, binding.hand1You2, binding.hand1You3, binding.hand1You4),
      viewLifecycleOwner,
      kinaPokerViewModel,
      Player.You,
      ))
      placeChoosers.add(BonusChooser(
      hand,
      BonusType.Win,
      arrayOf(binding.hand1Left1, binding.hand1Left2,binding.hand1Left3, binding.hand1Left4 ),
      viewLifecycleOwner,
      kinaPokerViewModel,
      Player.Left,
      ))
      placeChoosers.add(BonusChooser(
      hand,
      BonusType.Win,
      arrayOf(binding.hand1Opposite1, binding.hand1Opposite2,binding.hand1Opposite3, binding.hand1Opposite4 ) ,
      viewLifecycleOwner,
      kinaPokerViewModel,
      Player.Opposite,
      ))
      placeChoosers.add(BonusChooser(
      hand,
      BonusType.Win,
      arrayOf(binding.hand1Right1, binding.hand1Right2,binding.hand1Right3, binding.hand1Right4 ),
      viewLifecycleOwner,
      kinaPokerViewModel,
      Player.Right,
    ))
    // Four
    placeChoosers.add(BonusChooser(
      hand,
      BonusType.FourOfAKind,
      arrayOf(binding.fourofakindYou2, binding.fourofakindLeft2, binding.fourofakindOpposite2, binding.fourofakindRight2),
      viewLifecycleOwner,
      kinaPokerViewModel))
    placeChoosers.add(BonusChooser(
      hand,
      BonusType.StraightFlush,
      arrayOf(binding.straightflushYou2, binding.straightflushLeft2, binding.straightflushOpposite2, binding.straightflushRight2),
      viewLifecycleOwner,
      kinaPokerViewModel)
    )
    // Total score
    scoreShower = ScoreShower(binding.totalScore, viewLifecycleOwner, kinaPokerViewModel)
    // Next Button
    val nextButton: Button = binding.next
    kinaPokerViewModel.isHand3Done.observe(viewLifecycleOwner) {
      nextButton.isEnabled = it
    }
    nextButton.setOnClickListener {
      val navController = findNavController()
      navController.popBackStack()
      navController.navigate(R.id.nav_slideshow)
    }

    return root
  }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
