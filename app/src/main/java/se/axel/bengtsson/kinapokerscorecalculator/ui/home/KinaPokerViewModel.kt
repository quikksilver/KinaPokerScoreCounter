package se.axel.bengtsson.kinapokerscorecalculator.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import se.axel.bengtsson.kinapokerscorecalculator.KinaPoker

class KinaPokerViewModel : ViewModel() {

  private val _numberOfPlayer = MutableLiveData<Int>()
  val numberOfPlayer: LiveData<Int> = _numberOfPlayer

  private val _kinaPoker = MutableLiveData<KinaPoker>()
  val kinaPoker: LiveData<KinaPoker> = _kinaPoker

  private val _score = MutableLiveData<Array<Int>>()
  val score: LiveData<Array<Int>> = _score

  fun setNumberOfPlayer(player:Int) {
    _numberOfPlayer.value = player
    _kinaPoker.value = KinaPoker(player)
    updateScore()
  }

  fun updateScore() {
    Log.d("kpsc", "Before " + _score.value?.get(0)+  " " + _score.value?.get(1) + " "+  _score.value?.get(2) + " " + _score.value?.get(3))
    _score.value = _kinaPoker.value?.getRoundScore();
    Log.d("kpsc", "After " + _score.value?.get(0)+  " " + _score.value?.get(1) + " "+  _score.value?.get(2) + " " + _score.value?.get(3))
  }
}
