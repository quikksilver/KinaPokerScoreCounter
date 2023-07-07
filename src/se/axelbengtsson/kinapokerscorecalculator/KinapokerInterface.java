
import se.axelbengtsson.kinapokerscorecalculator.BonusType;
import se.axelbengtsson.kinapokerscorecalculator.Hand;
import se.axelbengtsson.kinapokerscorecalculator.PlayType;
import se.axelbengtsson.kinapokerscorecalculator.Player;
public interface KinapokerInterface {

  public Player[] createRound(int numberOfPlayers);
  public void setPlayersPlayType(Player player, PlayType playType);
  public void setPlayersPlace(Player player, Hand hand, int place);
  public void setPlayersBonus(Player player, Hand hand, BonusType bonusType);

}
