package se.axel.bengtsson.kinapokerscorecalculator.ui.hand1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import se.axel.bengtsson.kinapokerscorecalculator.BonusType
import se.axel.bengtsson.kinapokerscorecalculator.Hand
import se.axel.bengtsson.kinapokerscorecalculator.Player
import se.axel.bengtsson.kinapokerscorecalculator.databinding.FragmentHand1Binding
import se.axel.bengtsson.kinapokerscorecalculator.ui.common.BonusChooser
import se.axel.bengtsson.kinapokerscorecalculator.ui.common.PlaceChooser
import se.axel.bengtsson.kinapokerscorecalculator.ui.common.ScoreShower
import se.axel.bengtsson.kinapokerscorecalculator.ui.home.KinaPokerViewModel

class Hand1Fragment() : Fragment() {

  private var _binding: FragmentHand1Binding? = null

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
    //val kinaPokerViewModel =
    //  ViewModelProvider(this).get(KinaPokerViewModel::class.java)
    Log.d("kpsc", "Hand1: Number of players ${kinaPokerViewModel.numberOfPlayer.value}")
    _binding = FragmentHand1Binding.inflate(inflater, container, false)
    val root: View = binding.root

    // Add places
    placeChoosers.add(PlaceChooser(
      binding.hand1YouRadio,
      Player.You,
      kinaPokerViewModel,
      arrayOf(binding.hand1You1, binding.hand1You2, binding.hand1You3, binding.hand1You4),
      viewLifecycleOwner,
      binding.hand1You
    ))
    placeChoosers.add(PlaceChooser(
      binding.hand1LeftRadio,
      Player.Left,
      kinaPokerViewModel,
      arrayOf(binding.hand1Left1, binding.hand1Left2,binding.hand1Left3, binding.hand1Left4 ),
      viewLifecycleOwner,
      binding.hand1Left
    ))
    placeChoosers.add(PlaceChooser(
      binding.hand1OppositeRadio,
      Player.Opposite,
      kinaPokerViewModel,
      arrayOf(binding.hand1Opposite1, binding.hand1Opposite2,binding.hand1Opposite3, binding.hand1Opposite4 ) ,
      viewLifecycleOwner,
      binding.hand1Opposite
    ))
    placeChoosers.add(PlaceChooser(
      binding.hand1RightRadio,
      Player.Right,
      kinaPokerViewModel,
      arrayOf(binding.hand1Right1, binding.hand1Right2,binding.hand1Right3, binding.hand1Right4 ),
      viewLifecycleOwner,
      binding.hand1Right
    ))
    // Add Bonus Kind
    val bonusChooser = BonusChooser(
      Hand.Hand1,
      BonusType.Kind,
      arrayOf(binding.kindYou, binding.kindLeft, binding.kindOpposite, binding.kindRight),
      viewLifecycleOwner,
      kinaPokerViewModel)
    // Total score
    val scoreShower = ScoreShower(binding.totalScore, viewLifecycleOwner, kinaPokerViewModel)
    return root
  }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
