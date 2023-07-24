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
  private var default:Array<Int> = arrayOf()
  init {

    // Observer number of players
    kinaPokerViewModel.kinaPoker.observe(life) {
      println("Debug" + checkBox.size)
      arrayOf(
        0 to Player.You,
        1 to Player.Left,
        2 to Player.Opposite,
        3 to Player.Right).forEach {

        checkBox[it.first].visibility =
          if (kinaPokerViewModel.kinaPoker.value?.isPlayerInTheRound(it.second) == true) {
            if (kinaPokerViewModel.kinaPoker.value?.getPlayerPlayType(it.second) == PlayType.Play) {
              // Special case TODO move
              if (bonus == BonusType.Win && player != null && it.second == player) {
                View.INVISIBLE
              } else {
                View.VISIBLE
              }
              // Special case end
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
        if (!(kinaPokerViewModel.kinaPoker.value?.isPlayerInTheRound(player) == true
          && kinaPokerViewModel.kinaPoker.value?.getPlayerPlayType(player) == PlayType.Play)) {
          checkBox.forEach { it.visibility = View.INVISIBLE }
        }
      }
      // Special case end
      // Set Default
      default = checkBox.map { it.visibility }.toTypedArray()
    }


    // Set listener on each checkbox
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
        if (kinaPokerViewModel.kinaPoker.value?.isPlayerInTheRound(playerPair.first) == true) {
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
    // Set listener for Win (remove checkbox that cannot exist)
    if (bonus == BonusType.Win && player != null) {
      kinaPokerViewModel.score.observe(life) {
        // 1) Set default
        Log.d("kpsc", "Default  ${default[0]}, ${default[1]}, ${default[2]}, ${default[3]}")
        checkBox[0].visibility = default[0]
        checkBox[1].visibility = default[1]
        checkBox[2].visibility = default[2]
        checkBox[3].visibility = default[3]
        // 2) Remove pressed
        kinaPokerViewModel.kinaPoker.value?.getPlayerBonus(player)
          ?.filter { it.second == BonusType.Win && it.first != player && it.third == hand }!!.forEach {
          when(it.first) {
            Player.You -> checkBox[0].visibility = View.INVISIBLE
            Player.Left -> checkBox[1].visibility = View.INVISIBLE
            Player.Opposite -> checkBox[2].visibility = View.INVISIBLE
            Player.Right -> checkBox[3].visibility = View.INVISIBLE
          }
        }
      }
    }
  }
}
