package se.axel.bengtsson.kinapokerscorecalculator.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import se.axel.bengtsson.kinapokerscorecalculator.Hand
import se.axel.bengtsson.kinapokerscorecalculator.PlayerRound
import se.axel.bengtsson.kinapokerscorecalculator.databinding.FragmentSummaryBinding
import se.axel.bengtsson.kinapokerscorecalculator.ui.home.KinaPokerViewModel
import kotlin.math.round

class SummaryFragment : Fragment() {

    private var _binding: FragmentSummaryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
      val kinaPokerViewModel: KinaPokerViewModel by activityViewModels()
      kinaPokerViewModel.updateModel()

        _binding = FragmentSummaryBinding.inflate(inflater, container, false)
        val root: View = binding.root
      kinaPokerViewModel.score.observe(viewLifecycleOwner) { it ->
        if (it != null) {
          val summary = StringBuilder()
          summary.append(" ".repeat(3))
          var bonusSizeHand1 = 0;
          var bonusSizeHand2 = 0;
          var bonusSizeHand3 = 0;
          kinaPokerViewModel.kinaPoker.value!!.round.playerRound.forEach {
            summary.append(it.player.toString() + " ".repeat(15))
            bonusSizeHand1 = Math.max(bonusSizeHand1, it.getBonus().filter { it.third == Hand.Hand1 }.size)
            bonusSizeHand2 = Math.max(bonusSizeHand2, it.getBonus().filter { it.third == Hand.Hand2 }.size)
            bonusSizeHand3 = Math.max(bonusSizeHand3, it.getBonus().filter { it.third == Hand.Hand3 }.size)
          }
          summary.append("\nHand 1 (3 cards)\n")
          for (i in 0..bonusSizeHand1) {
            summary.append(" ".repeat(3))
            kinaPokerViewModel.kinaPoker.value!!.round.playerRound.forEach {
              val bonusString = hand(i, it, Hand.Hand1)
              summary.append(bonusString + " ".repeat(if (20 - bonusString.length  < 0) {0} else {20 - bonusString.length}))
            }
            summary.append("\n")
          }

          summary.append("Hand 2 (5 cards)\n")
          for (i in 0..bonusSizeHand2) {
            summary.append(" ".repeat(3))
            kinaPokerViewModel.kinaPoker.value!!.round.playerRound.forEach {
              val bonusString = hand(i, it, Hand.Hand2)
              summary.append(bonusString + " ".repeat(if (20 - bonusString.length  < 0) {0} else {20 - bonusString.length}))
            }
            summary.append("\n")
          }

          summary.append("Hand 1 (5 cards)\n")
          for (i in 0..bonusSizeHand3) {
            summary.append(" ".repeat(3))
            kinaPokerViewModel.kinaPoker.value!!.round.playerRound.forEach {
              val bonusString = hand(i, it, Hand.Hand3)
              summary.append(bonusString + " ".repeat(if (20 - bonusString.length  < 0) {0} else {20 - bonusString.length}))
            }
            summary.append("\n")
          }
          summary.append("Total score\n You: ${it[0]} Left: ${it[1]} Opposite: ${it[2]} Right: ${it[3]}")
          binding.textSummary.text = summary.toString()
        }
      }
        return root
    }

  private fun hand(i:Int, playerRound: PlayerRound, hand:Hand): String {
    return if (i < playerRound.getBonus().filter { it.third == hand }.size) {
      val bonus = playerRound.getBonus().filter { it.third == hand }[i]

        if (bonus.first == playerRound.player) {
          "" + bonus.second.points +"("+  bonus.second.toString()+ ")     "}
        else {"" + (bonus.second.points * -1) +"("+  bonus.second.toString()+ " to " + bonus.first.toString().get(0) + ")     " }
    } else { "-     " }

  }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
