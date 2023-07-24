package se.axel.bengtsson.kinapokerscorecalculator

import java.lang.RuntimeException

class Round(val numberOfPlayers: Int) {

  val players: Array<Player>
  val playerRound: Array<PlayerRound>

  init {
    // Validation
    if (numberOfPlayers < 0 || numberOfPlayers > 4) {
      throw RuntimeException("Number of player can only be 0 - 4")
    }
    // Initiate
    players = Player.values().filter { player -> player.isPlaying(numberOfPlayers) }.toTypedArray()
    playerRound = players.map { PlayerRound(it) }.toTypedArray()
  }
}
