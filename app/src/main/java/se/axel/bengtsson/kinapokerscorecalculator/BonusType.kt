package se.axel.bengtsson.kinapokerscorecalculator

/**
 * Describe the Bonus and the point that is added per player.
 */
enum class BonusType(val points: Int, val short:String) {
  NoBonus(0, "-"),
  Kind(3, "3K"),
  FullHouse(2, "FH"),
  FourOfAKind(4, "4K"),
  StraightFlush(5, "SF"),
  ScoopUp(3, "S"),
  Win(1, "W"),
  NotPlay(-3, "NP"),
  Automatic(3, "A"),
  Dragon(13, "D"),
  ColorDragon(39, "CD");
}
