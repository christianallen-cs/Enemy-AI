package game.maps

import game.physics.PhysicsVector

class GridLocation(val x: Int, val y: Int) {

  def centerAsVector(): PhysicsVector = {
    new PhysicsVector(this.x + 0.5, this.y + 0.5)
  }

  override def toString = s"($x, $y)"

  override def equals(that: Any): Boolean = {
    that match {
      case other: GridLocation =>
        this.x == other.x && this.y == other.y
      case _ => false
    }
  }
}
