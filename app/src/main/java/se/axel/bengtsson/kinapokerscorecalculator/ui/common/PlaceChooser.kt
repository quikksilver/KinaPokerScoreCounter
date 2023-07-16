package se.axel.bengtsson.kinapokerscorecalculator.ui.common

import android.util.Log
import android.view.View
import android.view.View.GONE
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import se.axel.bengtsson.kinapokerscorecalculator.Hand
import se.axel.bengtsson.kinapokerscorecalculator.KinaPoker
import se.axel.bengtsson.kinapokerscorecalculator.PlayType
import se.axel.bengtsson.kinapokerscorecalculator.Player
import se.axel.bengtsson.kinapokerscorecalculator.ui.home.KinaPokerViewModel

class PlaceChooser(
  private val radioGroup: RadioGroup,
  val player: Player,
  val hand: Hand,
  private val kinaPokerViewModel: KinaPokerViewModel,
  private val buttons: Array<RadioButton>,
  life: LifecycleOwner,
  private val text:TextView
) {
  private val TAG:String = "kpsc"
  init {
    // setup observers on player change
    kinaPokerViewModel.kinaPoker.observe(life) {
      radioGroup.visibility = isVisible(it, player)
      text.visibility = isVisible(it, player)
      when (Player.values().filter { p ->
        kinaPokerViewModel.kinaPoker.value?.getPlayerPlayType(p) == PlayType.Play }.size) {
        1 -> {
          buttons[1].visibility = GONE
          buttons[2].visibility = GONE
          buttons[3].visibility = GONE
        }
        2 -> {
          buttons[2].visibility = GONE
          buttons[3].visibility = GONE
        }
        3-> {
          buttons[3].visibility = GONE
        }
        4-> {

        }
      }
    }
    // Init actions
    radioGroup.setOnCheckedChangeListener { group:RadioGroup, checkedId:Int ->
      Log.d(TAG,
        "PlaceChooser: $group  ${group.id} ID $checkedId checked ID ${group.checkedRadioButtonId} R ${se.axel.bengtsson.kinapokerscorecalculator.R.id.four}"
      )
      val place = when (checkedId) {
        buttons[0].id -> {
          Log.d(TAG, "PlaceChooser: Player: $player chose place 1")
          1
        }
        buttons[1].id -> {
          Log.d(TAG, "PlaceChooser: Player: $player chose place 2")
          2
        }
        buttons[2].id -> {
          Log.d(TAG, "PlaceChooser: Player: $player chose place 3")
          3
        }
        buttons[3].id -> {
          Log.d(TAG, "PlaceChooser: Player: $player chose place 4")
          4
        }
        else -> {
          Log.d(TAG, "PlaceChooser: Player: $player chose place 0")
        }
      }
      kinaPokerViewModel.kinaPoker.value?.setPlayersPlace(player,hand,place)
      kinaPokerViewModel.updateModel()
    }
  }

  private fun isVisible(kp: KinaPoker, player:Player): Int {
    return if (kp != null && kp.isPlayerPlaying(player) && kp.getPlayerPlayType(player) == PlayType.Play) {
      View.VISIBLE
    } else {
      View.GONE
    }
  }
}
