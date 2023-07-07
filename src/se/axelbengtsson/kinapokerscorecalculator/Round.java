package se.axelbengtsson.kinapokerscorecalculator;

public class Round {
  final int numberOfPlayers;
  final Player[] players;
  final PlayerRound[] playerRound;

  public Round(int numberOfPlayers) {
    this.numberOfPlayers = numberOfPlayers;
    this.players = new Player[numberOfPlayers];
    this.playerRound = new PlayerRound[numberOfPlayers];
    if (numberOfPlayers < 0 || numberOfPlayers > 4) {
      throw new RuntimeException("Number of player can only be 0 - 4");
    }
    for( Player p : Player.values()) {
      if (p.isPlaying(numberOfPlayers)) {
        this.players[p.index(numberOfPlayers)] = p;
      }
    }
  }
}
