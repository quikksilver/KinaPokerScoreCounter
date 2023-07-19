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

class BonusChooser(val hand: Hand, val bonus:BonusType, private val checkBox: Array<CheckBox>, life: LifecycleOwner, private val kinaPokerViewModel:KinaPokerViewModel, private val player:Player? = null) {
  init {

    // Observer number of players
    kinaPokerViewModel.kinaPoker.observe(life) {
      Player.values().forEach {
        checkBox[it.index(kinaPokerViewModel!!.numberOfPlayer!!.value!!)].visibility =
          if (kinaPokerViewModel.kinaPoker.value?.isPlayerPlaying(it) == true) {
            if (kinaPokerViewModel.kinaPoker.value?.getPlayerPlayType(it) == PlayType.Play) {
              if (bonus == BonusType.Win && player != null) { // Special case TODO move
                View.INVISIBLE
              } else {
                View.VISIBLE
              }

            } else {
              View.INVISIBLE
            }
          } else {
            View.GONE
          }
      }
      checkBox.forEach { it.isSelected = false }

      // Special case (TODO should be moved), notplaying and bonus in Win
      if (bonus == BonusType.Win && player != null) {
        if (kinaPokerViewModel.kinaPoker.value?.isPlayerPlaying(player) != true) {
          checkBox.forEach { it.visibility = View.INVISIBLE }
        }
      }
    }
    checkBox.forEach {
      it.setOnClickListener { view ->

        val playerPair:Pair<Player, Boolean> = when (view.id) {
          checkBox[0].id -> Pair(Player.You, checkBox[0].isChecked)
          checkBox[1].id -> Pair(Player.Left, checkBox[1].isChecked)
          checkBox[2].id -> Pair(Player.Opposite, checkBox[2].isChecked)
          checkBox[3].id -> Pair(Player.Right, checkBox[3].isChecked)
          else -> { Pair(Player.You, false) }
        }
        Log.d("kpsc", "Bonus chooser: " + playerPair + " " + view.id)
        if (kinaPokerViewModel.kinaPoker.value?.isPlayerPlaying(playerPair.first) == true) {
          if (playerPair.second) {
            Log.d("kpsc", "Bonus chooser: ${playerPair.first}  adding bonus $bonus")
            if (bonus == BonusType.Win && player != null) {
              kinaPokerViewModel.kinaPoker.value?.setPlayerWin(player, hand, playerPair.first)
            } else {
              kinaPokerViewModel.kinaPoker.value?.setPlayersBonus(playerPair.first, hand, bonus)
            }
          } else {
            Log.d("kpsc", "Bonus chooser: $playerPair  removing bonus $bonus")
            if (bonus == BonusType.Win && player != null) {
              kinaPokerViewModel.kinaPoker.value?.removePlayerWin(player, hand, playerPair.first)
            } else {
              kinaPokerViewModel.kinaPoker.value?.removePlayersBonus(playerPair.first, hand, bonus)
            }
          }
          kinaPokerViewModel.updateModel()
        }
      }
    }
  }
}
