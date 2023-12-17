package game.gameobjects

import game.physics.PhysicsVector
import game.physics.objects.{DynamicObject, StaticObject}

class Wall(val x:Int, val y:Int) extends StaticObject(new PhysicsVector(x,y), new PhysicsVector(1.0, 1.0)){

  override def collideWithDynamicObject(otherObject: DynamicObject): Unit = {
    otherObject.location.x = otherObject.previousLocation.x
    otherObject.location.y = otherObject.previousLocation.y
  }

}
