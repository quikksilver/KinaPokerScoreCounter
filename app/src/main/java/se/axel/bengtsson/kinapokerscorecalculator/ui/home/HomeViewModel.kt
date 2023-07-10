package se.axel.bengtsson.kinapokerscorecalculator.ui.home

import android.R
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import se.axel.bengtsson.kinapokerscorecalculator.KinaPoker

class HomeViewModel : ViewModel() {

  private val _numberOfPlayer = MutableLiveData<Int>()
  val numberOfPlayer: LiveData<Int> = _numberOfPlayer

  private val _kinaPoker = MutableLiveData<KinaPoker>()
  val kinaPoker: LiveData<KinaPoker> = _kinaPoker

  fun setNumberOfPlayer(player:Int) {
    _numberOfPlayer.value = player
    _kinaPoker.value = KinaPoker(player)
  }
}
