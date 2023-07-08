package se.axel.bengtsson.kinapokerscorecalculator

class PlayerRound(player: Player) {
    var playType: PlayType? = null
    val hands = mutableMapOf(Hand.Hand_1 to 0, Hand.Hand_2 to 0, Hand.Hand_3 to 0);
    var totalscore = 0
    val bonus: MutableList<Pair<Player, BonusType>> = mutableListOf()
    val player = player
}
