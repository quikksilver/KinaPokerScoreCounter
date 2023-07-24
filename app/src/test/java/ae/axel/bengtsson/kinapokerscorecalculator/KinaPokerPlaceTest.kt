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
    // Hand 1
    kp.setPlayerWin(Player.Left, Hand.Hand1, Player.Opposite)
    assertFalse(kp.isAllPlayersPlaceSet(Hand.Hand1))
    kp.setPlayerWin(Player.Left, Hand.Hand1, Player.Right)
    assertFalse(kp.isAllPlayersPlaceSet(Hand.Hand1))
    kp.setPlayerWin(Player.Opposite, Hand.Hand1, Player.Right)
    assertTrue(kp.isAllPlayersPlaceSet(Hand.Hand1))
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(-9,5,3,1))
    // Hand 2
    kp.setPlayerWin(Player.Left, Hand.Hand2, Player.Opposite)
    assertFalse(kp.isAllPlayersPlaceSet(Hand.Hand2))
    kp.setPlayerWin(Player.Left, Hand.Hand2, Player.Right)
    assertFalse(kp.isAllPlayersPlaceSet(Hand.Hand2))
    kp.setPlayerWin(Player.Opposite, Hand.Hand2, Player.Right)
    assertTrue(kp.isAllPlayersPlaceSet(Hand.Hand2))
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(-9,7,3,-1))
    // Hand 3
    kp.setPlayerWin(Player.Left, Hand.Hand3, Player.Opposite)
    assertFalse(kp.isAllPlayersPlaceSet(Hand.Hand3))
    kp.setPlayerWin(Player.Left, Hand.Hand3, Player.Right)
    assertFalse(kp.isAllPlayersPlaceSet(Hand.Hand3))
    kp.setPlayerWin(Player.Opposite, Hand.Hand3, Player.Right)
    assertTrue(kp.isAllPlayersPlaceSet(Hand.Hand3))
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(-9,15,3,-9))
    // Check the scoop
    //Left scooped O and R
    assertTrue(kp.round.playerRound[Player.Left.index(4)].bonus.filter { it.second == BonusType.ScoopUp && it.first == Player.Left }.size == 2)
    // O scooped R
    assertTrue(kp.round.playerRound[Player.Opposite.index(4)].bonus.filter { it.second == BonusType.ScoopUp && it.first == Player.Opposite }.size == 1)
  }

}
