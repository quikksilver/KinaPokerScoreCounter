package se.axelbengtsson.kinapokerscorecalculator;

/**
 *
 */
public enum Player {
  You(0,0,0,0),
  Left(-1,-1,1,1),
  Opposite(-1,1,-1,2),
  Right(-1,-1,2,3);

  final int[] indexArray;
  Player(int a, int b, int c, int d) {
    indexArray = new int[4];
    indexArray[0] = a;
    indexArray[1] = b;
    indexArray[2] = c;
    indexArray[3] = d;
  }
  public int index(int numberOfPlayers) {
    return indexArray[numberOfPlayers - 1];
  }
  public boolean isPlaying(int numberOfPlayers) {
    return this.index(numberOfPlayers) >= 0;
  }
}
