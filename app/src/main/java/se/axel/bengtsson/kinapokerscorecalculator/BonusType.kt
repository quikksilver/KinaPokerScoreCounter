package se.axel.bengtsson.kinapokerscorecalculator

/**
 * Describe the Bonus and the point that is added per player.
 */
enum class BonusType(val points: Int) {
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
}
