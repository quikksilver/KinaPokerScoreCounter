package ae.axel.bengtsson.kinapokerscorecalculator

import org.junit.Test

import org.junit.Assert.*
import se.axel.bengtsson.kinapokerscorecalculator.Hand
import se.axel.bengtsson.kinapokerscorecalculator.KinaPoker
import se.axel.bengtsson.kinapokerscorecalculator.PlayType
import se.axel.bengtsson.kinapokerscorecalculator.Player

/**
 */
class KinaPokerPlayTypeTest {
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
  }

  @Test
  fun happyTest4Players1Auto1NoPlay() {
    val kp = KinaPoker(4)
    kp.setPlayersPlayType(Player.You, PlayType.NotPlay)
    kp.setPlayersPlayType(Player.Right, PlayType.Play)
    kp.setPlayersPlayType(Player.Opposite, PlayType.Automatic)
    kp.setPlayersPlayType(Player.Left, PlayType.Play)
    assertArrayEquals(kp.round.playerRound.map { it.playType }.toTypedArray(), arrayOf(PlayType.NotPlay, PlayType.Play, PlayType.Automatic, PlayType.Play))
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(-9,0,9,0))
    assertTrue(kp.isAllPlayersPlayTypeSet())
  }

  @Test
  fun happyTest4Players1Auto2NoPlay() {
    val kp = KinaPoker(4)
    kp.setPlayersPlayType(Player.You, PlayType.NotPlay)
    kp.setPlayersPlayType(Player.Right, PlayType.NotPlay)
    kp.setPlayersPlayType(Player.Opposite, PlayType.Automatic)
    kp.setPlayersPlayType(Player.Left, PlayType.Play)
    assertArrayEquals(kp.round.playerRound.map { it.playType }.toTypedArray(), arrayOf(PlayType.NotPlay, PlayType.Play, PlayType.Automatic, PlayType.NotPlay ))
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(-6,3,9,-6))
    assertTrue(kp.isAllPlayersPlayTypeSet())
  }

  @Test
  fun happyTest4Players1Dragon1DragonColor() {
    val kp = KinaPoker(4)
    kp.setPlayersPlayType(Player.You, PlayType.Dragon)
    kp.setPlayersPlayType(Player.Right, PlayType.ColorDragon)
    kp.setPlayersPlayType(Player.Opposite, PlayType.Automatic)
    kp.setPlayersPlayType(Player.Left, PlayType.Play)
    assertArrayEquals(kp.round.playerRound.map { it.playType }.toTypedArray(), arrayOf(PlayType.Dragon, PlayType.Play, PlayType.Automatic, PlayType.ColorDragon ))
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(13,-55,3,39))
    assertTrue(kp.isAllPlayersPlayTypeSet())
  }

  @Test
  fun happyTest3Players1Dragon1DragonColor() {
    val kp = KinaPoker(3)
    kp.setPlayersPlayType(Player.You, PlayType.Dragon)
    kp.setPlayersPlayType(Player.Right, PlayType.ColorDragon)
    kp.setPlayersPlayType(Player.Left, PlayType.Play)
    assertArrayEquals(kp.round.playerRound.map { it.playType }.toTypedArray(), arrayOf(PlayType.Dragon, PlayType.Play, PlayType.ColorDragon ))
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(13,-52,39))
    assertTrue(kp.isAllPlayersPlayTypeSet())
  }

  @Test
  fun happyTest2Players1DragonColor() {
    val kp = KinaPoker(2)
    kp.setPlayersPlayType(Player.You, PlayType.Play)
    kp.setPlayersPlayType(Player.Opposite, PlayType.ColorDragon)
    assertArrayEquals(kp.round.playerRound.map { it.playType }.toTypedArray(), arrayOf(PlayType.Play, PlayType.ColorDragon ))
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(-39,39))
    assertTrue(kp.isAllPlayersPlayTypeSet())
  }

  @Test
  fun happyTest1Players1DragonColor() {
    val kp = KinaPoker(1)
    kp.setPlayersPlayType(Player.You, PlayType.ColorDragon)
    assertArrayEquals(kp.round.playerRound.map { it.playType }.toTypedArray(), arrayOf(PlayType.ColorDragon ))
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(0))
    assertTrue(kp.isAllPlayersPlayTypeSet())
  }

  // Place tests

  @Test
  fun happyTest4PlayersPlayPlace() {
    val kp = KinaPoker(4)
    kp.setPlayersPlayType(Player.You, PlayType.Play)
    kp.setPlayersPlayType(Player.Right, PlayType.Play)
    kp.setPlayersPlayType(Player.Opposite, PlayType.Play)
    kp.setPlayersPlayType(Player.Left, PlayType.Play)
    assertTrue(kp.isAllPlayersPlayTypeSet())
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(0,0,0,0))
    // Hand 1
    kp.setPlayerWin(Player.You, Hand.Hand1, Player.Left)
    kp.setPlayerWin(Player.You, Hand.Hand1, Player.Right)
    kp.setPlayerWin(Player.You, Hand.Hand1, Player.Opposite)
    kp.setPlayerWin(Player.Left, Hand.Hand1,Player.Right)
    kp.setPlayerWin(Player.Left, Hand.Hand1,Player.Opposite)
    kp.setPlayerWin(Player.Opposite, Hand.Hand1,Player.Right)

    assertTrue(kp.isAllPlayersPlaceSet(Hand.Hand1))
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(3,1,-1,-3))
    // Hand 2
    kp.setPlayerWin(Player.You, Hand.Hand2, Player.Left)
    kp.setPlayerWin(Player.You, Hand.Hand2, Player.Right)
    kp.setPlayerWin(Player.You, Hand.Hand2, Player.Opposite)
    kp.setPlayerWin(Player.Left, Hand.Hand2,Player.Right)
    kp.setPlayerWin(Player.Left, Hand.Hand2,Player.Opposite)
    kp.setPlayerWin(Player.Opposite, Hand.Hand2,Player.Right)
    assertTrue(kp.isAllPlayersPlaceSet(Hand.Hand2))
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(6,2,-2,-6))
    // Hand 3
    kp.setPlayerWin(Player.You, Hand.Hand3, Player.Left)
    kp.setPlayerWin(Player.You, Hand.Hand3, Player.Right)
    kp.setPlayerWin(Player.You, Hand.Hand3, Player.Opposite)
    kp.setPlayerWin(Player.Left, Hand.Hand3,Player.Right)
    kp.setPlayerWin(Player.Left, Hand.Hand3,Player.Opposite)
    kp.setPlayerWin(Player.Opposite, Hand.Hand3,Player.Right)
    assertTrue(kp.isAllPlayersPlaceSet(Hand.Hand3))
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(9,3,-3,-9))
  }

  @Test
  fun happyTest3PlayersPlayPlace() {
    val kp = KinaPoker(3)
    kp.setPlayersPlayType(Player.You, PlayType.Play)
    kp.setPlayersPlayType(Player.Left, PlayType.Play)
    kp.setPlayersPlayType(Player.Right, PlayType.Play)
    assertTrue(kp.isAllPlayersPlayTypeSet())
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(0,0,0))
    // Hand 1
    kp.setPlayerWin(Player.You, Hand.Hand1, Player.Left)
    kp.setPlayerWin(Player.You, Hand.Hand1, Player.Right)
    kp.setPlayerWin(Player.Left, Hand.Hand1,Player.Right)
    assertTrue(kp.isAllPlayersPlaceSet(Hand.Hand1))
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(2,0,-2))
    // Hand 2
    kp.setPlayerWin(Player.You, Hand.Hand2, Player.Left)
    kp.setPlayerWin(Player.You, Hand.Hand2, Player.Right)
    kp.setPlayerWin(Player.Left, Hand.Hand2,Player.Right)
    assertTrue(kp.isAllPlayersPlaceSet(Hand.Hand2))
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(4,0,-4))
    // Hand 3
    kp.setPlayerWin(Player.You, Hand.Hand3, Player.Left)
    kp.setPlayerWin(Player.You, Hand.Hand3, Player.Right)
    kp.setPlayerWin(Player.Left, Hand.Hand3,Player.Right)
    assertTrue(kp.isAllPlayersPlaceSet(Hand.Hand3))
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(6,0,-6))
  }

  @Test
  fun happyTest2PlayersPlayPlace() {
    val kp = KinaPoker(2)
    kp.setPlayersPlayType(Player.You, PlayType.Play)
    kp.setPlayersPlayType(Player.Opposite, PlayType.Play)
    assertTrue(kp.isAllPlayersPlayTypeSet())
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(0,0))
    // Hand 1
    kp.setPlayerWin(Player.You, Hand.Hand1, Player.Opposite)
    assertTrue(kp.isAllPlayersPlaceSet(Hand.Hand1))
    println(kp.round.playerRound[Player.You.index(2)].toString())
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(1,-1))
    // Hand 2
    kp.setPlayerWin(Player.You, Hand.Hand2, Player.Opposite)
    assertTrue(kp.isAllPlayersPlaceSet(Hand.Hand2))
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(2,-2))
    // Hand 3
    kp.setPlayerWin(Player.You, Hand.Hand3, Player.Opposite)
    assertTrue(kp.isAllPlayersPlaceSet(Hand.Hand3))
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(3,-3))
  }
  @Test
  fun happyTest1PlayersPlayPlace() {
    val kp = KinaPoker(1)
    kp.setPlayersPlayType(Player.You, PlayType.Play)
    assertTrue(kp.isAllPlayersPlayTypeSet())
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(0))
    // Hand 1
    kp.setPlayerWin(Player.You, Hand.Hand1, Player.You)
    assertTrue(kp.isAllPlayersPlaceSet(Hand.Hand1))
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(0))
    // Hand 2
    kp.setPlayerWin(Player.You, Hand.Hand1, Player.You)
    assertTrue(kp.isAllPlayersPlaceSet(Hand.Hand2))
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(0))
    // Hand 3
    kp.setPlayerWin(Player.You, Hand.Hand1, Player.You)
    assertTrue(kp.isAllPlayersPlaceSet(Hand.Hand3))
    assertArrayEquals(kp.round.playerRound.map { it.totalscore}.toTypedArray(), arrayOf(0))
  }

  //
}
