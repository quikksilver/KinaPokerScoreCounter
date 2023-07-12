package se.axel.bengtsson.kinapokerscorecalculator.ui.common

import android.util.Log
import android.view.View
import android.view.View.GONE
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import se.axel.bengtsson.kinapokerscorecalculator.KinaPoker
import se.axel.bengtsson.kinapokerscorecalculator.Player
import se.axel.bengtsson.kinapokerscorecalculator.ui.home.KinaPokerViewModel

class PlaceChooser(
  private val radioGroup: RadioGroup,
  val player: Player,
  private val kinaPokerViewModel: KinaPokerViewModel,
  private val buttons: Array<RadioButton>,
  private val life: LifecycleOwner,
  private val text:TextView
) {
  private val TAG:String = "kpsc"
  init {
    // setup observers on player change
    kinaPokerViewModel.kinaPoker.observe(life) {
      radioGroup.visibility = isVisible(it, player)
      text.visibility = isVisible(it, player)
      when (kinaPokerViewModel.numberOfPlayer.value) {
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
      when (checkedId) {
        buttons[0].id -> {
          Log.d(TAG, "PlaceChooser: Player: $player chose place 1")
        }
        buttons[0].id -> {
          Log.d(TAG, "PlaceChooser: Player: $player chose place 2")
        }
        buttons[0].id -> {
          Log.d(TAG, "PlaceChooser: Player: $player chose place 3")
        }
        buttons[0].id -> {
          Log.d(TAG, "PlaceChooser: Player: $player chose place 4")
        }
      }
    }
  }

  private fun isVisible(kp: KinaPoker, player:Player): Int {
    return if (kp != null && kp.isPlayerPlaying(player)) {
      View.VISIBLE
    } else {
      View.GONE
    }
  }
}
