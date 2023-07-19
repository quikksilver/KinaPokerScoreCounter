package ae.axel.bengtsson.kinapokerscorecalculator

import org.junit.Test

import org.junit.Assert.*
import se.axel.bengtsson.kinapokerscorecalculator.*
import java.nio.file.WatchEvent.Kind

/**
 */
class KinaPokerPlaceTest {
  @Test
  fun happyTest3PlayersPlay1Not() {
    val kp = KinaPoker(4)
    assertFalse(kp.isAllPlayersPlayTypeSet())
    kp.setPlayersPlayType(Player.You, PlayType.NotPlay)
    assertFalse(kp.isAllPlayersPlayTypeSet())
    kp.setPlayersPlayType(Player.Right, PlayType.Play)
    assertFalse(kp.isAllPlayersPlayTypeSet())
    kp.setPlayersPlayType(Player.Opposite, PlayType.Play)
    assertFalse(kp.isAllPlayersPlayTypeSet())
    kp.setPlayersPlayType(Player.Left, PlayType.Play)
    assertArrayEquals(kp.round.playerRound.map { it.playType }.toTypedArray(), arrayOf(PlayType.NotPlay, PlayType.Play, PlayType.Play, PlayType.Play))
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(-9,3,3,3))
    assertTrue(kp.isAllPlayersPlayTypeSet())
    kp.setPlayerWin(Player.Left, Hand.Hand1, Player.Opposite)
    kp.setPlayerWin(Player.Left, Hand.Hand1, Player.Right)
    kp.setPlayerWin(Player.Opposite, Hand.Hand1, Player.Right)
    assertTrue(kp.isAllPlayersPlaceSet(Hand.Hand1))
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(-9,5,3,1))
  }

}