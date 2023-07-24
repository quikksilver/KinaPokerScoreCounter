package se.axel.bengtsson.kinapokerscorecalculator.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import se.axel.bengtsson.kinapokerscorecalculator.Hand
import se.axel.bengtsson.kinapokerscorecalculator.KinaPoker

class KinaPokerViewModel : ViewModel() {

  private val _numberOfPlayer = MutableLiveData<Int>()
  val numberOfPlayer: LiveData<Int> = _numberOfPlayer

  private val _kinaPoker = MutableLiveData<KinaPoker>()
  val kinaPoker: LiveData<KinaPoker> = _kinaPoker

  private val _score = MutableLiveData<Array<Int>>()
  val score: LiveData<Array<Int>> = _score

  // Different steps done
  private val _isPlayTypeDone = MutableLiveData<Boolean>()
  val isPlayTypeDone: LiveData<Boolean> = _isPlayTypeDone

  private val _isHand1Done = MutableLiveData<Boolean>()
  val isHand1Done: LiveData<Boolean> = _isHand1Done

  private val _isHand2Done = MutableLiveData<Boolean>()
  val isHand2Done: LiveData<Boolean> = _isHand2Done

  private val _isHand3Done = MutableLiveData<Boolean>()
  val isHand3Done: LiveData<Boolean> = _isHand3Done

  fun setNumberOfPlayer(player:Int) {
    _numberOfPlayer.value = player
    _kinaPoker.value = KinaPoker(player)
    updateModel()
  }

  fun updateModel() {
    Log.d("kpsc", "Before " + _score.value?.get(0)+  " " + _score.value?.get(1) + " "+  _score.value?.get(2) + " " + _score.value?.get(3))
    _score.value = _kinaPoker.value?.getRoundScore();
    Log.d("kpsc", "After " + _score.value?.get(0)+  " " + _score.value?.get(1) + " "+  _score.value?.get(2) + " " + _score.value?.get(3))
    _isPlayTypeDone.value = _kinaPoker.value?.isAllPlayersPlayTypeSet()?.or( false)
    _isHand1Done.value = _kinaPoker.value?.isAllPlayersPlaceSet(Hand.Hand1)?.or(false)
    _isHand2Done.value = _kinaPoker.value?.isAllPlayersPlaceSet(Hand.Hand2)?.or(false)
    _isHand3Done.value = _kinaPoker.value?.isAllPlayersPlaceSet(Hand.Hand3)?.or(false)
  }
}
