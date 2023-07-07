package se.axelbengtsson.kinapokerscorecalculator;
/**
 * Describe the Bonus and the point that is added per player.
 */
public enum BonusType {
  NoBonus(0),
  Kind(3),
  FullHouse(2),
  FourOfAKind(4),
  StraightFlush(5),
  ScoopUp(3),
  NotPlay(-3),
  Automatic(3),
  Dragon(13),
  ColorDragon(39);

  final int points;
  BonusType(int points) {
    this.points = points;
  }
}
