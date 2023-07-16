package se.axel.bengtsson.kinapokerscorecalculator

interface KinapokerInterface {
    fun setPlayersPlayType(player: Player, playType: PlayType)
    fun isAllPlayersPlayTypeSet():Boolean
    fun setPlayersPlace(player: Player, hand: Hand, place: Int)
    fun isAllPlayersPlaceSet(hand: Hand):Boolean
    fun setPlayersBonus(player: Player, hand: Hand, bonusType: BonusType)
    fun removePlayersBonus(player: Player, hand: Hand, bonusType: BonusType)
    fun isRoundComplete():Boolean
    fun setRoundComplete()
    fun setRound(round: Round): Array<Player>

    // Getter
    fun getRoundScore():Array<Int>
    fun getPlayerPlayType(player: Player): PlayType
}
