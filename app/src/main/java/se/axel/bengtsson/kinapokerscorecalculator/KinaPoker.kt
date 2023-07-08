package se.axel.bengtsson.kinapokerscorecalculator

class KinaPoker(numberOfPlayers: Int): KinapokerInterface {
  val round:Round;
  private val numberOfPlayers:Int;
  private var playTypeLocked:Boolean;
  init {
    this.round = Round(numberOfPlayers);
    this.numberOfPlayers = numberOfPlayers;
    this.playTypeLocked = false;
  }

  override fun setPlayersPlayType(player: Player, playType: PlayType) {
    if (!playTypeLocked) {
      round.playerRound[player.index(this.numberOfPlayers)].playType = playType;
    } else {
      throw RuntimeException("Play type is locked")
    }
    if (isAllPlayersPlayTypeSet()) {
      playTypeLocked = true;
      // Set Bonus on play type.
      round.playerRound.filter { it.playType == PlayType.NotPlay }.forEach { p ->
        // Add Bonus to it self
        p.bonus.add((p.player to p.playType?.bonusType) as Pair<Player, BonusType>);
        // Add Bonus to all others
        round.playerRound.filter { it.player != p.player }.forEach { p2 ->
          p2.bonus.add((p.player to p.playType?.bonusType) as Pair<Player, BonusType>);
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
      round.playerRound[player.index(numberOfPlayers)].hands[hand] = place;
    } else {
      throw RuntimeException("Wrong hand: $hand");
    }
  }

  override fun setPlayersBonus(player: Player, hand: Hand, bonusType: BonusType) {
    round.playerRound[player.index(numberOfPlayers)].bonus.add(player to bonusType)
  }

  override fun setRound(round: Round): Array<Player> {
    TODO("Not yet implemented")
  }

  override fun calcScore() {
    //Bonus
    round.playerRound.forEach { player ->
      // Own Bonus
      player.totalscore += player.bonus.filter { it -> it.first == player.player }
        .map {
          when (it.second) {
            BonusType.NotPlay -> it.second.points * (numberOfPlayers - 1)
            else -> it.second.points
          }
        }
        .fold(0) {acc , b -> acc + b}
      // Opponents Bonus
      player.totalscore += player.bonus.filter { it -> it.first != player.player }
        .map { it.second.points }
        .fold(0) {acc , b -> acc + b * -1}

    }
    //place
  }
}
