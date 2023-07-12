package se.axel.bengtsson.kinapokerscorecalculator

class KinaPoker(numberOfPlayers: Int): KinapokerInterface {
  val round:Round
  private val numberOfPlayers:Int
  init {
    this.round = Round(numberOfPlayers)
    this.numberOfPlayers = numberOfPlayers

  }

  override fun setPlayersPlayType(player: Player, playType: PlayType) {

    round.playerRound[player.index(this.numberOfPlayers)].playType = playType

    if (isAllPlayersPlayTypeSet()) {
      clearBonus()
      // Set Bonus on play type.
      // Not Play should always add to all.
      round.playerRound.forEach { p ->
        // Add Bonus to it self
        p.bonus.add((Triple(p.player, p.playType!!.bonusType, Hand.PlayType)))

        val filterAllExceptSelf:(PlayerRound)->Boolean= {pr -> pr.player != p.player}
        val filterAllExceptSelfAndPlaying:(PlayerRound)->Boolean= {pr -> pr.player != p.player && pr.playType == PlayType.Play }

        // Add Bonus to all others (if NotPlay) and to only playing for all else
        round.playerRound
          .filter(if ( p.playType == PlayType.NotPlay) {filterAllExceptSelf} else {filterAllExceptSelfAndPlaying})
          .forEach { p2 ->
            p2.bonus.add((Triple(p.player, p.playType!!.bonusType, Hand.PlayType)))
          }
      }
      calcScore()
    }
  }

  override fun isAllPlayersPlayTypeSet(): Boolean {
    return round.playerRound.map { it.playType != null }.reduce { acc, b -> acc && b }
  }

  override fun setPlayersPlace(player: Player, hand: Hand, place: Int) {
    if (round.playerRound[player.index(numberOfPlayers)].hands.containsKey(hand)) {
      round.playerRound[player.index(numberOfPlayers)].hands[hand] = place
      if (isAllPlayersPlaceSet(hand)) {
        calcScore()
      }
    } else {
      throw RuntimeException("Wrong hand: $hand")
    }
  }

  override fun isAllPlayersPlaceSet(hand: Hand): Boolean {
    return round.playerRound.none { it.hands[hand] == null }
  }

  override fun isRoundComplete():Boolean {
    return isAllPlayersPlaceSet(Hand.Hand3) && isAllPlayersPlaceSet(Hand.Hand2) && isAllPlayersPlaceSet(Hand.Hand1)
  }

  override fun setRoundComplete() {
    if (isRoundComplete() && isAllPlayersPlayTypeSet()) {
      // Handle scope...
      TODO("Not yet implemented")
    } else {
      throw RuntimeException("Missing information to complete the round")
    }
  }
  fun isPlayerPlaying(player:Player):Boolean {
    return round.playerRound.any { it.player == player }
  }

  override fun setPlayersBonus(player: Player, hand: Hand, bonusType: BonusType) {
    round.playerRound[player.index(numberOfPlayers)].bonus.add(Triple(player, bonusType, hand))
  }

  override fun setRound(round: Round): Array<Player> {
    TODO("Not yet implemented")
  }

  override fun getRoundScore(): Array<Int> {
    return arrayOf(
      if (isPlayerPlaying(Player.You)) { round.playerRound[Player.You.index(numberOfPlayers)].totalscore } else {0},
      if (isPlayerPlaying(Player.Left)) { round.playerRound[Player.Left.index(numberOfPlayers)].totalscore } else {0},
      if (isPlayerPlaying(Player.Opposite)) { round.playerRound[Player.Opposite.index(numberOfPlayers)].totalscore } else {0},
      if (isPlayerPlaying(Player.Right)) { round.playerRound[Player.Right.index(numberOfPlayers)].totalscore } else {0}
    )
  }

  private fun clearBonus() {
    round.playerRound.forEach { player ->
      player.bonus.clear()
    }
  }

  private fun calcScore() {
    //Bonus
    round.playerRound.forEach { player ->
      // clear all scores
      player.totalscore = 0
      // Own Bonus
      player.totalscore += player.bonus
        .filter { it.first == player.player }
        .map { bonus ->
          when (bonus.second) {
            BonusType.NotPlay -> bonus.second.points * (numberOfPlayers - 1)
            else -> bonus.second.points * round.playerRound.filter { it.player != player.player && it.playType == PlayType.Play}.size
          }
        }
        .fold(0) {acc , b -> acc + b}
      // Opponents Bonus
      player.totalscore += player.bonus
        .filter { it.first != player.player }
        .map { it.second.points }
        .fold(0) {acc , b -> acc + b * -1}
    }
    //Place
    val numberThatPlay = round.playerRound
      .filter { it.playType == PlayType.Play }.size
    arrayOf(Hand.Hand1, Hand.Hand2, Hand.Hand3)
      .filter { isAllPlayersPlaceSet(it) }
      .forEach { hand ->
        round.playerRound
          .filter { it.playType == PlayType.Play }
          .forEach { player ->
            player.totalscore += PlaceScore.placePoints[numberThatPlay - 1][player.hands[hand]!! - 1]
          }
      }
  }
}
