package se.axelbengtsson.kinapokerscorecalculator;

import se.axelbengtsson.kinapokerscorecalculator.BonusType;
/**
 * Each round the player choose the playType
 */
public enum PlayType {
  Play(BonusType.NoBonus),
  NotPlay(BonusType.NotPlay),
  Automatic(BonusType.Automatic),
  Dragon(BonusType.Dragon),
  ColorDragon(BonusType.ColorDragon);

  final BonusType bonusType;
  PlayType(BonusType bonusType) {
    this.bonusType = bonusType;
  }
}
