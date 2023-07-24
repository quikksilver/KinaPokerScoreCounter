package ae.axel.bengtsson.kinapokerscorecalculator

import org.junit.Test

import org.junit.Assert.*
import se.axel.bengtsson.kinapokerscorecalculator.*
import java.nio.file.WatchEvent.Kind

/**
 */
class KinaPokerBonusTest {
  @Test
  fun happyTest4PlayersPlay() {
    val kp = KinaPoker(4)
    assertFalse(kp.isAllPlayersPlayTypeSet())
    kp.setPlayersPlayType(Player.You, PlayType.Play)
    assertFalse(kp.isAllPlayersPlayTypeSet())
    kp.setPlayersPlayType(Player.Right, PlayType.Play)
    assertFalse(kp.isAllPlayersPlayTypeSet())
    kp.setPlayersPlayType(Player.Opposite, PlayType.Play)
    assertFalse(kp.isAllPlayersPlayTypeSet())
    kp.setPlayersPlayType(Player.Left, PlayType.Play)
    assertArrayEquals(kp.round.playerRound.map { it.playType }.toTypedArray(), arrayOf(PlayType.Play, PlayType.Play, PlayType.Play, PlayType.Play))
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(0,0,0,0))
    assertTrue(kp.isAllPlayersPlayTypeSet())
    kp.setPlayersBonus(Player.You, Hand.Hand1, BonusType.Kind);
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(9,-3,-3,-3))
    kp.setPlayersBonus(Player.Left, Hand.Hand1, BonusType.Kind);
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(6,6,-6,-6))
    kp.removePlayersBonus(Player.Left, Hand.Hand1, BonusType.Kind);
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(9,-3,-3,-3))
    kp.removePlayersBonus(Player.You, Hand.Hand1, BonusType.Kind);
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(0,0,0,0))
  }



  //
}
