package ae.axel.bengtsson.kinapokerscorecalculator

import org.junit.Test

import org.junit.Assert.*
import se.axel.bengtsson.kinapokerscorecalculator.KinaPoker
import se.axel.bengtsson.kinapokerscorecalculator.PlayType
import se.axel.bengtsson.kinapokerscorecalculator.Player
import se.axel.bengtsson.kinapokerscorecalculator.Round

/**
 */
class KinaPokerTest {
  @Test
  fun happyTest4PlayersPlay() {
    val kp = KinaPoker(4)
    assertFalse(kp.isAllPlayersPlayTypeSet())
    kp.setPlayersPlayType(Player.You, PlayType.Play);
    assertFalse(kp.isAllPlayersPlayTypeSet())
    kp.setPlayersPlayType(Player.Right, PlayType.Play);
    assertFalse(kp.isAllPlayersPlayTypeSet())
    kp.setPlayersPlayType(Player.Opposite, PlayType.Play);
    assertFalse(kp.isAllPlayersPlayTypeSet())
    kp.setPlayersPlayType(Player.Left, PlayType.Play);
    assertArrayEquals(kp.round.playerRound.map { it.playType }.toTypedArray(), arrayOf(PlayType.Play, PlayType.Play, PlayType.Play, PlayType.Play));
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(0,0,0,0));
    assertTrue(kp.isAllPlayersPlayTypeSet())
  }

  @Test
  fun happyTest4Players1Auto1NoPlay() {
    val kp = KinaPoker(4)
    kp.setPlayersPlayType(Player.You, PlayType.NotPlay);
    kp.setPlayersPlayType(Player.Right, PlayType.Play);
    kp.setPlayersPlayType(Player.Opposite, PlayType.Automatic);
    kp.setPlayersPlayType(Player.Left, PlayType.Play);
    assertArrayEquals(kp.round.playerRound.map { it.playType }.toTypedArray(), arrayOf(PlayType.NotPlay, PlayType.Play, PlayType.Automatic, PlayType.Play));
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(-9,3,3,3));
    assertTrue(kp.isAllPlayersPlayTypeSet())
  }
}
