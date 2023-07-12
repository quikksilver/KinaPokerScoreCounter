package se.axel.bengtsson.kinapokerscorecalculator.ui.common

import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import se.axel.bengtsson.kinapokerscorecalculator.ui.home.KinaPokerViewModel

class ScoreShower(scoreText: TextView, life: LifecycleOwner, private val kinaPokerViewModel: KinaPokerViewModel) {
  init {
    kinaPokerViewModel.score.observe(life) {
      if (it != null) {
        scoreText.text = "score\n You: ${it[0]} Left: ${it[1]} Opposite: ${it[2]} Right: ${it[3]}"
      }
    }
  }
}
