package se.axel.bengtsson.kinapokerscorecalculator.ui.home

import android.R
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import se.axel.bengtsson.kinapokerscorecalculator.PlayType
import se.axel.bengtsson.kinapokerscorecalculator.Player

/**
 <item>Play</item>
<item>Not Play</item>
<item>Automatic</item>
<item>Dragon</item>
<item>Color Dragon</item>
 */
class PlayTypeSpinner(val player: Player,
                      private val homeViewModel:HomeViewModel,
                      val context: Context,
                      val spinner: Spinner)
  : Activity(), AdapterView.OnItemSelectedListener {
  /**
   * Callback object for game type spinner
   * @author axel
   */
  private val playTypeArray: Array<PlayType> = arrayOf(
    PlayType.Play,
    PlayType.NotPlay,
    PlayType.Automatic,
    PlayType.Dragon,
    PlayType.ColorDragon)

  init {
    //val gameType = resources.getStringArray(se.axel.bengtsson.kinapokerscorecalculator.R.array.game_type)
    val adapter = ArrayAdapter.createFromResource(
      context,
      se.axel.bengtsson.kinapokerscorecalculator.R.array.game_type, R.layout.simple_spinner_item
    )
    // Specify the layout to use when the list of choices appears
    adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
    // Apply the adapter to the spinner
    spinner.adapter = adapter
    spinner.onItemSelectedListener = this
  }

  override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
      Log.d(
        "kpsc",
        "PlayType selectedplayer=$player id=$id name: " + parent.getItemAtPosition(
          pos
        )
      )
    if (homeViewModel.kinaPoker.value != null) {
      if (homeViewModel.kinaPoker.value?.isPlayerPlaying(player) == true) {
        homeViewModel.kinaPoker.value?.setPlayersPlayType(player, playTypeArray[id.toInt()])
        homeViewModel.updateScore();
      }
    }
  }

  fun reset() {
    spinner.setSelection(0)
    if (homeViewModel.kinaPoker.value != null) {
      if (homeViewModel.kinaPoker.value?.isPlayerPlaying(player) == true) {
        homeViewModel.kinaPoker.value?.setPlayersPlayType(player, playTypeArray[0])
        homeViewModel.updateScore();
      }
    }
  }
  override fun onNothingSelected(parent: AdapterView<*>?) {
    // Another interface callback
  }
}

