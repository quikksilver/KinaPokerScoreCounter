package se.axelbengtsson.kinapokerscorecalculator;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for calculate score
 * @author Axel Bengtsson
 *
 */
public class KinaPokerScoreCalculator {
  public final static int YOU = 0;

  private int numberOfPlayer;
  private int sum;
  private GameType[] gameType;
  private int hand1PlacePoints = 0;
  private int hand2PlacePoints = 0;
  private int hand3PlacePoints = 0;
  private List<Bonus> positiveBonus;
  private List<Bonus> negativeBonus;
  
  public static enum GameType {
    Play(0),
    NotPlay(-3),
    Automatic(3),
    Dragon(13),
    ColorDragon(39);
    
    final int points;

    private GameType(final int points) {
      this.points = points;
    }
  }
  public static enum Hand {
    Hand1,
    Hand2,
    Hand3,
    NoHand,
  }
  public static enum Bonus {
    Kind(3),
    FullHouse(2),
    FourOfAKind(4),
    StraightFlush(5),
    ScoopUp(3);
    final int points;
    private Bonus(final int points) {
      this.points = points;
    }
  }
  private final static int[][] placePoints = {
    {0, 0, 0, 0},
    {1,-1, 0, 0},
    {2, 0,-2, 0},
    {3, 1,-1,-3}};

  /** Private constructor*/
  private KinaPokerScoreCalculator(int numberOfPlayers) {
    this.numberOfPlayer = numberOfPlayers;
    gameType = new GameType[numberOfPlayer];
    // Add default value
    for (int i = 0; i < numberOfPlayer; i++) {
      gameType[i] = GameType.Play;
    }
    this.hand1PlacePoints = 0;
    this.hand2PlacePoints = 0;
    this.hand3PlacePoints = 0;
    this.positiveBonus = new LinkedList<KinaPokerScoreCalculator.Bonus>();
    this.negativeBonus = new LinkedList<KinaPokerScoreCalculator.Bonus>();
  }

  /**
   * Singleton method to create to create a new instance
   * @param numberOfPlayers
   * @return
   */
  public static KinaPokerScoreCalculator create(int numberOfPlayers) {
    return new KinaPokerScoreCalculator(numberOfPlayers);
  }

  /**
   * Set the score of different hands by setting the place.
   * @param hand - hand (just for log)
   * @param place - The place
   */
  public void setPlaceOfHand(final Hand hand, final int place) {
    if (place > 0 && place <= numberOfPlayer) {
      switch(hand) {
      case Hand1:
        hand1PlacePoints = placePoints[getNumberOfPlayerThatPlay() - 1][place - 1]; break;
      case Hand2:
        hand2PlacePoints = placePoints[getNumberOfPlayerThatPlay() - 1][place - 1]; break;
      case Hand3:
        hand3PlacePoints = placePoints[getNumberOfPlayerThatPlay() - 1][place - 1]; break;
      }
      calculateSum();
    }
    
  }
  /** Calculate the sum, should be called every time something changes*/
  private void calculateSum() {
    int s = 0;
    // GameType bonus
    // YOU will give points to all players when you not playing
    if (gameType[YOU] == GameType.NotPlay) {
      s = gameType[YOU].points * (getNumberOfPlayer() - 1);
    } else { // YOU will only take points from opponents that Play
      s = gameType[YOU].points * (getNumberOfPlayerThatPlay(1));
    }
    for (int i = 1; i < numberOfPlayer; i++) {
      // YOU will only lose points if you play or NotPlay from opponents
      if (gameType[YOU] == GameType.Play || gameType[i] == GameType.NotPlay) {
        s -= gameType[i].points;
      }
    }
    if (gameType[YOU] == GameType.Play) {
      s += hand1PlacePoints;
      s += hand2PlacePoints;
      s += hand3PlacePoints;
      for (Bonus bonus : positiveBonus) {
        s += bonus.points * ((bonus == Bonus.ScoopUp) ? 1 : getNumberOfPlayerThatPlay(1));
      }
      for (Bonus bonus : negativeBonus) {
        s -= bonus.points;
      }
    }
    sum = s;
  }

  /**
   * @return - the number of players that play - YOU
   */
  public int getNumberOfPlayerThatPlay() {
    return getNumberOfPlayerThatPlay(0);
  }

  /**
   * Number of player that play
   * @param startAt (if 0 then including you)
   * @return
   */
  public int getNumberOfPlayerThatPlay(final int startAt) {
    int sum = 0;
    for (int i = startAt; i < gameType.length; i++) {
      if (gameType[i] == GameType.Play) {
        sum++;
      }
    }
    return sum;
  }

  /**
   * Set game type for player
   * @param player
   * @param type
   */
  public void setGamePlay(int player, GameType type) {
    if (player < numberOfPlayer) {
      gameType[player] = type;
      calculateSum();
    }
  }
//TODO refactoring this to use index of players intead of two methods YOU/Opponents
  public void setBonusYou(Hand hand, Bonus bonus) {
    positiveBonus.add(bonus);
    calculateSum();
  }
  public void setBonusOpponent(Hand hand, Bonus bonus) {
    negativeBonus.add(bonus);
    calculateSum();
  }
  public void removeBonusYou(Hand hand, Bonus bonus) {
    positiveBonus.remove(bonus);
    calculateSum();
  }
  public void removeBonusOpponent(Hand hand, Bonus bonus) {
    negativeBonus.remove(bonus);
    calculateSum();
  }
//Getter
  /**
   * @return number of players
   */
  public int getNumberOfPlayer() {
    return this.numberOfPlayer;
  }

  /**
   * @return - current score sum
   */
  public int getSum() {
    return this.sum;
  }

  /**
   * @return - true if YOU game type is play
   */
  public boolean areYouPlaying() {
    return gameType[YOU] == GameType.Play;
  }
}
