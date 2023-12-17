package game.gameobjects

import game.physics.PhysicsVector
import game.physics.objects.{DynamicObject, StaticObject}

class Projectile(location: PhysicsVector,
                 _velocity: PhysicsVector,
                 dimensions: PhysicsVector,
                 owner: String)
  extends DynamicObject(location, dimensions, owner) {

  this.velocity = _velocity

  override def collideWithStaticObject(staticObject: StaticObject): Unit = {
    this.destroy()
  }

  override def collideWithDynamicObject(otherObject: DynamicObject): Unit = {
    if(otherObject.team != this.owner) {
      otherObject.destroy()
      this.destroy()
    }
  }
}
