package se.axelbengtsson.kinapokerscorecalculator;
import static org.junit.Assert.*;

import org.junit.Test;

import se.axelbengtsson.kinapokerscorecalculator.KinaPokerScoreCalculator.Bonus;
import se.axelbengtsson.kinapokerscorecalculator.KinaPokerScoreCalculator.GameType;
import se.axelbengtsson.kinapokerscorecalculator.KinaPokerScoreCalculator.Hand;



public class TestKinaPokerScoreCalculator {

  public TestKinaPokerScoreCalculator() {
    // TODO Auto-generated constructor stub
  }

  @Test
  public void testGameType() {
    KinaPokerScoreCalculator sc = KinaPokerScoreCalculator.create(4);
    sc.setGamePlay(0, GameType.Play);
    sc.setGamePlay(1, GameType.Automatic);
    sc.setGamePlay(2, GameType.NotPlay);
    sc.setGamePlay(3, GameType.Play);
    assertEquals(0, sc.getSum());
  }

  @Test
  public void testGameType2() {
    KinaPokerScoreCalculator sc = KinaPokerScoreCalculator.create(4);
    sc.setGamePlay(0, GameType.NotPlay);
    sc.setGamePlay(1, GameType.Automatic);
    sc.setGamePlay(2, GameType.NotPlay);
    sc.setGamePlay(3, GameType.Play);
    assertEquals(-6, sc.getSum());
  }

  @Test
  public void testGameType3() {
    KinaPokerScoreCalculator sc = KinaPokerScoreCalculator.create(4);
    sc.setGamePlay(0, GameType.Automatic);
    sc.setGamePlay(1, GameType.Automatic);
    sc.setGamePlay(2, GameType.NotPlay);
    sc.setGamePlay(3, GameType.Play);
    assertEquals(6, sc.getSum());
  }

  @Test
  public void testGameType5() {
    KinaPokerScoreCalculator sc = KinaPokerScoreCalculator.create(3);
    sc.setGamePlay(0, GameType.Automatic);
    sc.setGamePlay(1, GameType.NotPlay);
    sc.setGamePlay(2, GameType.Play);
    assertEquals(6, sc.getSum());
  }

  @Test
  public void testGameType4() {
    KinaPokerScoreCalculator sc = KinaPokerScoreCalculator.create(4);
    sc.setGamePlay(0, GameType.Dragon);
    sc.setGamePlay(1, GameType.Automatic);
    sc.setGamePlay(2, GameType.NotPlay);
    sc.setGamePlay(3, GameType.Play);
    assertEquals(16, sc.getSum());
  }

  @Test
  public void testBonus() {
    KinaPokerScoreCalculator sc = KinaPokerScoreCalculator.create(4);
    sc.setGamePlay(0, GameType.Play);
    sc.setGamePlay(1, GameType.Play);
    sc.setGamePlay(2, GameType.NotPlay);
    sc.setGamePlay(3, GameType.Play);
    sc.setBonusYou(Hand.Hand1, Bonus.Kind);
    sc.setBonusOpponent(Hand.Hand1, Bonus.Kind);
    assertEquals(6, sc.getSum());
  }

  @Test
  public void testBonus2() {
    KinaPokerScoreCalculator sc = KinaPokerScoreCalculator.create(4);
    sc.setGamePlay(0, GameType.Play);
    sc.setGamePlay(1, GameType.Play);
    sc.setGamePlay(2, GameType.Play);
    sc.setGamePlay(3, GameType.Play);
    sc.setBonusYou(Hand.Hand1, Bonus.Kind);
    sc.setBonusYou(Hand.Hand2, Bonus.FullHouse);
    sc.setBonusYou(Hand.Hand3, Bonus.StraightFlush);
    sc.setBonusOpponent(Hand.Hand1, Bonus.Kind);
    sc.setBonusOpponent(Hand.Hand3, Bonus.StraightFlush);
    assertEquals(22, sc.getSum());
  }

}
