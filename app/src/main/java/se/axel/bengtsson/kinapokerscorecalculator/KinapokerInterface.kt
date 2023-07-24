package se.axel.bengtsson.kinapokerscorecalculator

interface KinapokerInterface {
    fun setPlayersPlayType(player: Player, playType: PlayType)
    fun isAllPlayersPlayTypeSet():Boolean
    // Score
    fun setPlayerWin(player:Player, hand:Hand, loser:Player)
    fun removePlayerWin(player:Player, hand:Hand, loser:Player)
    fun isAllPlayersPlaceSet(hand: Hand):Boolean
    // Bonus
    fun setPlayersBonus(player: Player, hand: Hand, bonusType: BonusType)
    fun removePlayersBonus(player: Player, hand: Hand, bonusType: BonusType)



    // Needed ?
    fun isRoundComplete():Boolean
    fun setRoundComplete()
    fun setRound(round: Round): Array<Player>

    // Getter
    fun getRoundScore():Array<Int>
    fun getPlayerPlayType(player: Player): PlayType
    fun getPlayerIndex(player: Player): Int
    fun getPlayerBonus(player: Player): Array<Triple<Player, BonusType, Hand>>
}
