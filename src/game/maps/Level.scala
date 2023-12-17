package game.maps

object Level {

  def apply(number: Int): Level = {
    if (number == 0) {
      new Level {
        gridWidth = 19
        gridHeight = 19

        startingLocation = new GridLocation(1, 17)

        enemySpawners = List(
          new GridLocation(17, 17),
          new GridLocation(1, 1),
          new GridLocation(9, 1),
          new GridLocation(17, 9),
          new GridLocation(17, 1)
        )

        wallLocations = List(
          new GridLocation(2, 5),
          new GridLocation(2, 6),
          new GridLocation(2, 7),
          new GridLocation(2, 8),
          new GridLocation(2, 9),
          new GridLocation(2, 10),
          new GridLocation(2, 11),
          new GridLocation(2, 12),
          new GridLocation(2, 13),

          new GridLocation(5, 2),
          new GridLocation(6, 2),
          new GridLocation(7, 2),
          new GridLocation(8, 2),
          new GridLocation(9, 2),
          new GridLocation(10, 2),
          new GridLocation(11, 2),
          new GridLocation(12, 2),
          new GridLocation(13, 2),

          new GridLocation(15, 2),
          new GridLocation(15, 3),
          new GridLocation(16, 3),

          new GridLocation(5, 16),
          new GridLocation(6, 16),
          new GridLocation(7, 16),
          new GridLocation(8, 16),
          new GridLocation(9, 16),
          new GridLocation(10, 16),
          new GridLocation(11, 16),
          new GridLocation(12, 16),
          new GridLocation(13, 16),


          new GridLocation(16, 5),
          new GridLocation(16, 6),
          new GridLocation(16, 7),
          new GridLocation(16, 8),
          new GridLocation(16, 9),
          new GridLocation(16, 10),
          new GridLocation(16, 11),
          new GridLocation(16, 12),
          new GridLocation(16, 13),


          new GridLocation(3, 5),
          new GridLocation(3, 13),

          new GridLocation(4, 6),
          new GridLocation(4, 12),

          new GridLocation(5, 3),
          new GridLocation(5, 7),
          new GridLocation(5, 11),
          new GridLocation(5, 15),

          new GridLocation(6, 4),
          new GridLocation(6, 8),
          new GridLocation(6, 10),
          new GridLocation(6, 14),

          new GridLocation(7, 5),
          new GridLocation(7, 9),
          new GridLocation(7, 13),

          new GridLocation(8, 6),
          new GridLocation(8, 12),

          new GridLocation(9, 7),
          new GridLocation(9, 11),

          new GridLocation(10, 6),
          new GridLocation(10, 12),

          new GridLocation(11, 5),
          new GridLocation(11, 9),
          new GridLocation(11, 13),

          new GridLocation(12, 4),
          new GridLocation(12, 8),
          new GridLocation(12, 10),
          new GridLocation(12, 14),

          new GridLocation(13, 3),
          new GridLocation(13, 7),
          new GridLocation(13, 11),
          new GridLocation(13, 15),

          new GridLocation(14, 6),
          new GridLocation(14, 12),

          new GridLocation(15, 5),
          new GridLocation(15, 13)
        )

        wallLocations :::= generateBoundaryWalls(gridWidth, gridHeight)
      }
    } else {
      new Level
    }
  }

  def generateBoundaryWalls(gridWidth: Int, gridHeight: Int): List[GridLocation] = {
    (for (i <- 0 until gridWidth) yield {
      List(new GridLocation(i, 0), new GridLocation(i, gridHeight - 1))
    }).toList.flatten :::
      (for (j <- 0 until gridHeight) yield {
        List(new GridLocation(0, j), new GridLocation(gridWidth - 1, j))
      }).toList.flatten
  }

}

class Level {

  var enemySpawners: List[GridLocation] = List()
  var wallLocations: List[GridLocation] = List()

  var gridWidth: Int = 4
  var gridHeight: Int = 4

  var startingLocation = new GridLocation(1, 1)


}
