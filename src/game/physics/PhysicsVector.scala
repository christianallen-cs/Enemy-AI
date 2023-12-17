package game.physics

/**
  * A 3-Dimension vector in the physical space. The x/y plane is parallel to the ground and the z-axis is perpendicular
  * to the ground. Gravity is applied in the negative z direction (ie. positive z is up)
  *
  * @param x magnitude of the vector in the x direction
  * @param y magnitude of the vector in the y direction
  * @param z magnitude of the vector in the z direction
  */
class PhysicsVector(var x: Double = 0.0, var y: Double = 0.0, var z: Double = 0.0) {


  def distance2d(other: PhysicsVector): Double = {
    Math.sqrt(Math.pow(x - other.x, 2.0) + Math.pow(y - other.y, 2.0))
  }

  def normal2d(): PhysicsVector = {
    if (x.abs < 0.001 && y.abs < 0.001) {
      new PhysicsVector(0.0, 0.0)
    } else {
      val magnitude: Double = Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0))
      new PhysicsVector(x / magnitude, y / magnitude)
    }
  }

  override def toString: String = {
    "(" + x + ", " + y + ")"
  }

}
