package se.axel.bengtsson.kinapokerscorecalculator.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import se.axel.bengtsson.kinapokerscorecalculator.BonusType
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
          val summary = arrayOf(StringBuilder(),StringBuilder(),StringBuilder(),StringBuilder())
          summary.forEach {  it.append(" ".repeat(3))}
          var bonusSizeHand1 = 0;
          var bonusSizeHand2 = 0;
          var bonusSizeHand3 = 0;
          var bonusOther = 0
          kinaPokerViewModel.kinaPoker.value!!.round.playerRound.forEach {
            summary[it.player.index(4)].append(it.player.toString())
            bonusSizeHand1 = Math.max(bonusSizeHand1, it.getBonus().filter { it.third == Hand.Hand1 }.size)
            bonusSizeHand2 = Math.max(bonusSizeHand2, it.getBonus().filter { it.third == Hand.Hand2 }.size)
            bonusSizeHand3 = Math.max(bonusSizeHand3, it.getBonus().filter { it.third == Hand.Hand3 }.size)
            bonusOther = Math.max(bonusOther, it.getBonus().filter { it.third == Hand.Other }.size)
          }
          summary[0].append("\nHand 1\n")
          summary[1].append("\n\n")
          summary[2].append("\n\n")
          summary[3].append("\n\n")
          for (i in 0..bonusSizeHand1) {
            //summary.append(" ".repeat(3))
            kinaPokerViewModel.kinaPoker.value!!.round.playerRound.forEach {
              val bonusString = hand(i, it, Hand.Hand1)
              summary[it.player.index(4)].append(bonusString + " ".repeat(if (20 - bonusString.length  < 0) {0} else {20 - bonusString.length}))
              summary[it.player.index(4)].append("\n");
            }
          }

          summary[0].append("Hand 2\n")
          summary[1].append("\n")
          summary[2].append("\n")
          summary[3].append("\n")
          for (i in 0..bonusSizeHand2) {
            //summary.append(" ".repeat(3))
            kinaPokerViewModel.kinaPoker.value!!.round.playerRound.forEach {
              val bonusString = hand(i, it, Hand.Hand2)
              summary[it.player.index(4)].append(bonusString + " ".repeat(if (20 - bonusString.length  < 0) {0} else {20 - bonusString.length}))
              summary[it.player.index(4)].append("\n")
            }

          }

          summary[0].append("Hand 1\n")
          summary[1].append("\n")
          summary[2].append("\n")
          summary[3].append("\n")
          for (i in 0..bonusSizeHand3) {
            //summary.append(" ".repeat(3))
            kinaPokerViewModel.kinaPoker.value!!.round.playerRound.forEach {
              val bonusString = hand(i, it, Hand.Hand3)
              summary[it.player.index(4)].append(bonusString + " ".repeat(if (20 - bonusString.length  < 0) {0} else {20 - bonusString.length}))
              summary[it.player.index(4)].append("\n")
            }

          }
          summary[0].append("Other\n")
          summary[1].append("\n")
          summary[2].append("\n")
          summary[3].append("\n")
          for (i in 0..bonusOther) {
            //summary.append(" ".repeat(3))
            kinaPokerViewModel.kinaPoker.value!!.round.playerRound.forEach {
              val bonusString = hand(i, it, Hand.Other)
              summary[it.player.index(4)].append(bonusString)
              summary[it.player.index(4)].append("\n")
            }
          }
          //summary[0].append("Total score\n You: ${it[0]} Left: ${it[1]} Opposite: ${it[2]} Right: ${it[3]}")
          summary[0].append("Total score:\nYou: ${it[0]}\n\n")
          summary[1].append("\nLeft: ${it[1]}\n\n")
          summary[2].append("\nOpposite: ${it[2]}")
          summary[3].append("\nRight: ${it[3]}")
          // Help
          BonusType.values().forEach {
            summary[1].append(it.toString() + "\n")
            summary[0].append("  " + it.short + "\n")
          }
          binding.textSummary1.text = summary[0].toString()
          binding.textSummary2.text = summary[1].toString()
          binding.textSummary3.text = summary[2].toString()
          binding.textSummary4.text = summary[3].toString()
        }
      }
        return root
    }

  private fun hand(i:Int, playerRound: PlayerRound, hand:Hand): String {
    return if (i < playerRound.getBonus().filter { it.third == hand }.size) {
      val bonus = playerRound.getBonus().filter { it.third == hand }[i];

        if (bonus.first == playerRound.player) {
          "" + bonus.second.points +"("+  bonus.second.short + ")"}
        else {"" + (bonus.second.points * -1) +"("+  bonus.second.short + " by " + bonus.first.toString().get(0) + ")     " }
    } else { "-" }

  }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
