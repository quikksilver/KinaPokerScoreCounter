package se.axel.bengtsson.kinapokerscorecalculator

class KinaPoker(numberOfPlayers: Int): KinapokerInterface {
  val round:Round
  private val numberOfPlayers:Int
  init {
    this.round = Round(numberOfPlayers)
    this.numberOfPlayers = numberOfPlayers

  }
  // PLAY TYPE
  override fun setPlayersPlayType(player: Player, playType: PlayType) {
    round.playerRound[player.index(this.numberOfPlayers)].playType = playType

    if (isAllPlayersPlayTypeSet()) {
      clearBonus()
      // Set Bonus on play type.
      // Not Play should always add to all.
      round.playerRound.forEach { p ->
        val filterAllExceptSelf:(PlayerRound)->Boolean= {pr -> pr.player != p.player}
        val filterAllExceptSelfAndPlaying:(PlayerRound)->Boolean= {pr -> pr.player != p.player && pr.playType == PlayType.Play }

        // Add Bonus to all others (if NotPlay) and to only playing for all else
        round.playerRound
          .filter(if ( p.playType == PlayType.NotPlay) {filterAllExceptSelf} else {filterAllExceptSelfAndPlaying})
          .forEach { p2 ->
            // Add Bonus to it self
            p.bonus.add((Triple(p.player, p.playType!!.bonusType, Hand.Other)))
            // Add Negative bonus to other player
            p2.bonus.add((Triple(p.player, p.playType!!.bonusType, Hand.Other)))
          }
      }
      calcScore()
    }
  }

  override fun isAllPlayersPlayTypeSet(): Boolean {
    return round.playerRound.map { it.playType != null }.reduce { acc, b -> acc && b }
  }

  override fun setPlayerWin(player: Player, hand: Hand, loser: Player) {
    println("SetPlayerWin")
    val bonusType = BonusType.Win
    if (player != loser) {
      round.playerRound[player.index(numberOfPlayers)].bonus.add(Triple(player, bonusType, hand))
      round.playerRound[loser.index(numberOfPlayers)].bonus.add(Triple(player, bonusType, hand))
    }
    if (isAllPlayersPlaceSet(Hand.Hand1)
      && isAllPlayersPlaceSet(Hand.Hand2)
      && isAllPlayersPlaceSet(Hand.Hand3)) {
      calcScoop()
    } else {
      removeScoop()
    }
    calcScore()
  }

  override fun removePlayerWin(player: Player, hand: Hand, loser: Player) {
    println("removePlayerWin")
    val bonusType = BonusType.Win
    if (player != loser) {
      round.playerRound[player.index(numberOfPlayers)].bonus.remove(Triple(player, bonusType, hand))
      round.playerRound[loser.index(numberOfPlayers)].bonus.remove(Triple(player, bonusType, hand))
    }
    if (isAllPlayersPlaceSet(Hand.Hand1)
      && isAllPlayersPlaceSet(Hand.Hand2)
      && isAllPlayersPlaceSet(Hand.Hand3)) {
      calcScoop()
    } else {
      removeScoop()
    }
    calcScore()
  }
  private fun calcScoop() {
    println("calcScoop")
    round.playerRound.filter { it.playType ==  PlayType.Play }
        .forEach { playerRound ->
      val p = mutableListOf(0, 0, 0, 0)
      playerRound.bonus.filter { it.second == BonusType.Win && it.first != playerRound.player}
        .forEach {
          p[it.first.index(numberOfPlayers)]++
        }
      println(p)
      Player.values().filter { it.isPlaying(numberOfPlayers)}.forEach {
        if (p[it.index(numberOfPlayers)] == 3) { // Three hands
          round.playerRound[it.index(numberOfPlayers)].bonus.add(Triple(it, BonusType.ScoopUp, Hand.Other))
          // Take bonus
          playerRound.bonus.add((Triple(it, BonusType.ScoopUp, Hand.Other)))
        }
      }
    }
  }
  private fun removeScoop() {
    println("removeScoop")
    round.playerRound.filter { it.playType ==  PlayType.Play }.forEach { playerRound ->
      playerRound.bonus.removeAll { it.second == BonusType.ScoopUp }
    }
  }

  override fun isAllPlayersPlaceSet(hand: Hand): Boolean {
    val playing = numberOfPlayType(PlayType.Play)
    return round.playerRound
      .filter { playerRound ->
        println("$playing " + playerRound.bonus.filter { it.second == BonusType.Win && it.third == hand
        }.size)
        playerRound.playType == PlayType.Play
          && playerRound.bonus.filter { it.second == BonusType.Win && it.third == hand
        }.size  == playing - 1}.size == playing
  }

  // BONUS
  override fun setPlayersBonus(player: Player, hand: Hand, bonusType: BonusType) {
    val filterAllExceptSelfAndPlaying:(PlayerRound)->Boolean = {pr -> pr.player != player && pr.playType == PlayType.Play }

    round.playerRound
      .filter(filterAllExceptSelfAndPlaying)
      .forEach { p2 ->
        // Add bonus
        round.playerRound[player.index(numberOfPlayers)].bonus.add(Triple(player, bonusType, hand))
        // Take bonus
        p2.bonus.add((Triple(player, bonusType, hand)))
      }
    calcScore()
  }

  override fun removePlayersBonus(player: Player, hand: Hand, bonusType: BonusType) {
    val filterAllExceptSelfAndPlaying:(PlayerRound)->Boolean = {pr -> pr.player != player && pr.playType == PlayType.Play }
    round.playerRound
      .filter(filterAllExceptSelfAndPlaying)
      .forEach { p2 ->
        round.playerRound[player.index(numberOfPlayers)].bonus.remove(Triple(player, bonusType, hand))
        p2.bonus.remove((Triple(player, bonusType, hand)))
      }
    calcScore()
  }

  // UTILS
  fun isPlayerInTheRound(player:Player):Boolean {
    return player.isPlaying(numberOfPlayers)
  }

 private fun numberOfPlayType(playType: PlayType): Int {
   val filterType:(PlayerRound)->Boolean = { pr -> pr.playType == playType }
   return round.playerRound.filter(filterType).size
 }

  override fun getRoundScore(): Array<Int> {
    return arrayOf(
      if (isPlayerInTheRound(Player.You)) { round.playerRound[Player.You.index(numberOfPlayers)].totalscore } else {0},
      if (isPlayerInTheRound(Player.Left)) { round.playerRound[Player.Left.index(numberOfPlayers)].totalscore } else {0},
      if (isPlayerInTheRound(Player.Opposite)) { round.playerRound[Player.Opposite.index(numberOfPlayers)].totalscore } else {0},
      if (isPlayerInTheRound(Player.Right)) { round.playerRound[Player.Right.index(numberOfPlayers)].totalscore } else {0}
    )
  }

  override fun getPlayerPlayType(player: Player): PlayType {
    if (isAllPlayersPlayTypeSet()) {
      return round.playerRound[player.index(numberOfPlayers)].playType!!
    } else {
      throw RuntimeException("Should not be here")
    }
  }

  override fun getPlayerIndex(player: Player): Int {
    return player.index(numberOfPlayers)
  }

  override fun getPlayerBonus(player: Player): Array<Triple<Player, BonusType, Hand>> {
    return if (player.isPlaying(numberOfPlayers)) {
      round.playerRound[player.index(numberOfPlayers)].bonus.toTypedArray()
    } else {
      arrayOf()
    }
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
            BonusType.NotPlay -> bonus.second.points //* (numberOfPlayers - 1)
            else -> bonus.second.points //* round.playerRound.filter { it.player != player.player && it.playType == PlayType.Play}.size
          }
        }
        .fold(0) {acc , b -> acc + b}
      // Opponents Bonus
      player.totalscore += player.bonus
        .filter { it.first != player.player }
        .map { it.second.points }
        .fold(0) {acc , b -> acc + b * -1}
    }
  }
}
