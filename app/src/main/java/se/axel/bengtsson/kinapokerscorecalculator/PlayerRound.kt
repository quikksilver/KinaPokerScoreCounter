package se.axel.bengtsson.kinapokerscorecalculator

class PlayerRound(val player: Player) {
  var playType: PlayType? = null
  //val hands: MutableMap<Hand, Int?> = mutableMapOf(Hand.Hand1 to null, Hand.Hand2 to null, Hand.Hand3 to null)
  var totalscore = 0
  val bonus: MutableList<Triple<Player, BonusType, Hand>> = mutableListOf()

  override fun toString(): String {
    return "PlayerRound(playType=$playType, totalscore=$totalscore, bonus=$bonus, player=$player)"
  }

  fun getBonus():Array<Triple<Player, BonusType, Hand>> {
    return bonus.filter { it.second != BonusType.NoBonus }.toTypedArray()
  }
}
