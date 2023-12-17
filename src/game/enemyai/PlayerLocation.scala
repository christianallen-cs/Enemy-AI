package game.enemyai

import game.maps.GridLocation

class PlayerLocation(val x: Double, val y: Double, val playerId: String) {

  def asGridLocation(): GridLocation = {
    new GridLocation(x.toInt, y.toInt)
  }

}
