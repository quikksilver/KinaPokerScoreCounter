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
import se.axel.bengtsson.kinapokerscorecalculator.databinding.FragmentHand1Binding
import se.axel.bengtsson.kinapokerscorecalculator.databinding.FragmentHand2Binding
import se.axel.bengtsson.kinapokerscorecalculator.ui.common.BonusChooser
import se.axel.bengtsson.kinapokerscorecalculator.ui.common.PlaceChooser
import se.axel.bengtsson.kinapokerscorecalculator.ui.common.ScoreShower
import se.axel.bengtsson.kinapokerscorecalculator.ui.home.KinaPokerViewModel

class Hand2Fragment() : Fragment() {
  private val hand: Hand = Hand.Hand2
  private var _binding: FragmentHand2Binding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  private val placeChoosers: MutableList<PlaceChooser> = mutableListOf<PlaceChooser>()
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val kinaPokerViewModel: KinaPokerViewModel by activityViewModels()
    kinaPokerViewModel.updateModel()
    //val kinaPokerViewModel =
    //  ViewModelProvider(this).get(KinaPokerViewModel::class.java)
    Log.d("kpsc", "Hand1: Number of players ${kinaPokerViewModel.numberOfPlayer.value}")
    _binding = FragmentHand2Binding.inflate(inflater, container, false)
    val root: View = binding.root

    // Add places
    placeChoosers.add(PlaceChooser(
      binding.hand1YouRadio,
      Player.You,
      hand,
      kinaPokerViewModel,
      arrayOf(binding.hand1You1, binding.hand1You2, binding.hand1You3, binding.hand1You4),
      viewLifecycleOwner,
      binding.hand1You
    ))
    placeChoosers.add(PlaceChooser(
      binding.hand1LeftRadio,
      Player.Left,
      hand,
      kinaPokerViewModel,
      arrayOf(binding.hand1Left1, binding.hand1Left2,binding.hand1Left3, binding.hand1Left4 ),
      viewLifecycleOwner,
      binding.hand1Left
    ))
    placeChoosers.add(PlaceChooser(
      binding.hand1OppositeRadio,
      Player.Opposite,
      hand,
      kinaPokerViewModel,
      arrayOf(binding.hand1Opposite1, binding.hand1Opposite2,binding.hand1Opposite3, binding.hand1Opposite4 ) ,
      viewLifecycleOwner,
      binding.hand1Opposite
    ))
    placeChoosers.add(PlaceChooser(
      binding.hand1RightRadio,
      Player.Right,
      hand,
      kinaPokerViewModel,
      arrayOf(binding.hand1Right1, binding.hand1Right2,binding.hand1Right3, binding.hand1Right4 ),
      viewLifecycleOwner,
      binding.hand1Right
    ))
    // Add Bonus Full-house
    val bonusChooserFullHause = BonusChooser(
      hand,
      BonusType.FullHouse,
      arrayOf(binding.fullhouseYou, binding.fullhouseLeft, binding.fullhouseOpposite, binding.fullhouseRight),
      viewLifecycleOwner,
      kinaPokerViewModel)

    val bonusChooserFour = BonusChooser(
      hand,
      BonusType.FourOfAKind,
      arrayOf(binding.fourofakindYou, binding.fourofakindLeft, binding.fourofakindOpposite, binding.fourofakindRight),
      viewLifecycleOwner,
      kinaPokerViewModel)

    val bonusChoosersf = BonusChooser(
      hand,
      BonusType.StraightFlush,
      arrayOf(binding.straightflushYou, binding.straightflushLeft, binding.straightflushOpposite, binding.straightflushRight),
      viewLifecycleOwner,
      kinaPokerViewModel)

    // Total score
    val scoreShower = ScoreShower(binding.totalScore, viewLifecycleOwner, kinaPokerViewModel)
    // Next Button
    val nextButton: Button = binding.next
    kinaPokerViewModel.isHand2Done.observe(viewLifecycleOwner) {
      nextButton.isEnabled = it
    }
    nextButton.setOnClickListener(View.OnClickListener {

      val navController = findNavController();
      navController.popBackStack()
      navController.navigate(R.id.nav_hand3)

    })

    return root
  }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
