package se.axelbengtsson.kinapokerscorecalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerRound {
  PlayType playType;
  Map<Hand, Integer> hands = new HashMap(3);
  int totalscore;
  List<Map.Entry<Player, BonusType>> bonus = new ArrayList<>();
}
