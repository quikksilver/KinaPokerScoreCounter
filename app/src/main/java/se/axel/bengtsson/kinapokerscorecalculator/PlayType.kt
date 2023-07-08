package se.axel.bengtsson.kinapokerscorecalculator

import se.axel.bengtsson.kinapokerscorecalculator.BonusType

/**
 * Each round the player choose the playType
 */
enum class PlayType(val bonusType: BonusType) {
  Play(BonusType.NoBonus),
  NotPlay(BonusType.NotPlay),
  Automatic(BonusType.Automatic),
  Dragon(BonusType.Dragon),
  ColorDragon(BonusType.ColorDragon);
}
