package se.axel.bengtsson.kinapokerscorecalculator.ui.common

import android.util.Log
import android.view.View
import android.widget.CheckBox

import androidx.lifecycle.LifecycleOwner
import se.axel.bengtsson.kinapokerscorecalculator.BonusType
import se.axel.bengtsson.kinapokerscorecalculator.Hand
import se.axel.bengtsson.kinapokerscorecalculator.PlayType
import se.axel.bengtsson.kinapokerscorecalculator.Player
import se.axel.bengtsson.kinapokerscorecalculator.ui.home.KinaPokerViewModel

class BonusChooser(val hand: Hand, val bonus:BonusType, private val checkBox: Array<CheckBox>, life: LifecycleOwner, private val kinaPokerViewModel:KinaPokerViewModel) {
  init {

    // Observer number of players
    kinaPokerViewModel.kinaPoker.observe(life) {
      Player.values().forEach {
        checkBox[it.index(kinaPokerViewModel!!.numberOfPlayer!!.value!!)].visibility =
          if (kinaPokerViewModel.kinaPoker.value?.isPlayerPlaying(it) == true) {
            if (kinaPokerViewModel.kinaPoker.value?.getPlayerPlayType(it) == PlayType.Play) {
              View.VISIBLE
            } else {
              View.INVISIBLE
            }
          } else {
            View.GONE
          }
      }
      checkBox.forEach { it.isSelected = false }
    }
    checkBox.forEach {
      it.setOnClickListener { view ->

        val player:Pair<Player, Boolean> = when (view.id) {
          checkBox[0].id -> Pair(Player.You, checkBox[0].isChecked)
          checkBox[1].id -> Pair(Player.Left, checkBox[1].isChecked)
          checkBox[2].id -> Pair(Player.Opposite, checkBox[2].isChecked)
          checkBox[3].id -> Pair(Player.Right, checkBox[3].isChecked)
          else -> { Pair(Player.You, false) }
        }
        Log.d("kpsc", "Bonus chooser: " + player + " " + view.id)
        if (kinaPokerViewModel.kinaPoker.value?.isPlayerPlaying(player.first) == true) {
          if (player.second) {
            Log.d("kpsc", "Bonus chooser: ${player.first}  adding bonus $bonus")
            kinaPokerViewModel.kinaPoker.value?.setPlayersBonus(player.first, hand, bonus)
          } else {
            Log.d("kpsc", "Bonus chooser: $player  removing bonus $bonus")
            kinaPokerViewModel.kinaPoker.value?.removePlayersBonus(player.first, hand, bonus)
          }
          kinaPokerViewModel.updateModel()
        }
      }
    }
  }
}
