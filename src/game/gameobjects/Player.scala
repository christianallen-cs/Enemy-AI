package game.gameobjects

import game.physics.PhysicsVector
import game.physics.objects.{DynamicObject, StaticObject}

class Player(inputLocation: PhysicsVector,
             dimensions: PhysicsVector,
             id: String) extends DynamicObject(inputLocation, dimensions, id) {

  var respawnLocations: List[PhysicsVector] = List(new PhysicsVector(inputLocation.x, inputLocation.y))
  val speed: Double = 4.0
  val orientation: PhysicsVector = new PhysicsVector(1.0, 0.0)

  def move(direction: PhysicsVector): Unit = {
    val normalDirection = direction.normal2d()
    this.velocity = new PhysicsVector(normalDirection.x * speed, normalDirection.y * speed)
    this.orientation.x = this.velocity.x
    this.orientation.y = this.velocity.y
  }

  def stop(): Unit = {
    this.velocity = new PhysicsVector(0, 0)
  }

  override def destroy(): Unit = {
    val respawnLocation: PhysicsVector = this.respawnLocations((Math.random()*this.respawnLocations.length).toInt)
    this.location.x = respawnLocation.x
    this.location.y = respawnLocation.y
  }

  override def collideWithStaticObject(otherObject: StaticObject): Unit = {}

  override def collideWithDynamicObject(otherObject: DynamicObject): Unit = {
    if(otherObject.team != this.team) {
      otherObject.destroy()
      this.destroy()
    }
  }


}
