package ae.axel.bengtsson.kinapokerscorecalculator

import org.junit.Test

import org.junit.Assert.*
import se.axel.bengtsson.kinapokerscorecalculator.Player
import se.axel.bengtsson.kinapokerscorecalculator.Round

/**
 */
class RoundTest {
  @Test
  fun happyTest4Players() {
    val round = Round(4)
    assertEquals(round.numberOfPlayers, 4);
    assertArrayEquals(round.players, arrayOf(Player.You, Player.Left, Player.Opposite, Player.Right));
    assertEquals(round.players.size, 4);
    assertEquals(round.playerRound.size, 4);
  }
  @Test
  fun happyTest3Players() {
    val round = Round(3)
    assertEquals(round.numberOfPlayers, 3);
    assertArrayEquals(round.players, arrayOf(Player.You, Player.Left, Player.Right));
    assertEquals(round.players.size, 3);
    assertEquals(round.playerRound.size, 3);
  }
  @Test
  fun happyTest2Players() {
    val round = Round(2)
    assertEquals(round.numberOfPlayers, 2);
    assertArrayEquals(round.players, arrayOf(Player.You, Player.Opposite));
    assertEquals(round.players.size, 2);
    assertEquals(round.playerRound.size, 2);
  }
  @Test
  fun happyTest1Players() {
    val round = Round(1)
    assertEquals(round.numberOfPlayers, 1);
    assertArrayEquals(round.players, arrayOf(Player.You));
    assertEquals(round.players.size, 1);
    assertEquals(round.playerRound.size, 1);
  }
}
