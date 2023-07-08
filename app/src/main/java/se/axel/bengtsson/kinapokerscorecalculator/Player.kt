package se.axel.bengtsson.kinapokerscorecalculator

/**
 *
 */
enum class Player(a: Int, b: Int, c: Int, d: Int) {
  You(0, 0, 0, 0),
  Left(-1, -1, 1, 1),
  Opposite(-1, 1, -1, 2),
  Right(-1, -1, 2, 3);

    val indexArray: IntArray

    init {
        indexArray = IntArray(4)
        indexArray[0] = a
        indexArray[1] = b
        indexArray[2] = c
        indexArray[3] = d
    }

    fun index(numberOfPlayers: Int): Int {
        return indexArray[numberOfPlayers - 1]
    }

    fun isPlaying(numberOfPlayers: Int): Boolean {
        return index(numberOfPlayers) >= 0
    }
}
