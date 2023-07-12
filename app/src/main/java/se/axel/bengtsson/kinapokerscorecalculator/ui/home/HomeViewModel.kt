package se.axel.bengtsson.kinapokerscorecalculator.ui.home

import android.R
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.DEFAULT_CONCURRENCY
import se.axel.bengtsson.kinapokerscorecalculator.KinaPoker

class HomeViewModel : ViewModel() {

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
    Log.d("kpsc", "Before " + _score.value.toString())
    _score.value = _kinaPoker.value?.getRoundScore();
    Log.d("kpsc", "After " + _score.value.toString())
  }
}
