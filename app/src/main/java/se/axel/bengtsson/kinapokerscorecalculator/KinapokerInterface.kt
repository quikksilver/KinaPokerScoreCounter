package se.axel.bengtsson.kinapokerscorecalculator

import se.axel.bengtsson.kinapokerscorecalculator.BonusType

interface KinapokerInterface {
    fun setPlayersPlayType(player: Player, playType: PlayType)
    fun isAllPlayersPlayTypeSet():Boolean
    fun setPlayersPlace(player: Player, hand: Hand, place: Int)
    fun setPlayersBonus(player: Player, hand: Hand, bonusType: BonusType)
    fun setRound(round: Round): Array<Player>
    fun calcScore()
}
