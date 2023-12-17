package game.physics

import game.physics.objects.{DynamicObject, GameObject, StaticObject}

/**
 * Controls and computes the simulated physics for a game. Manages dynamic object movement, gravity, and
 * object collisions
 */
object PhysicsEngine {


  def segmentsOverlap(lower1: Double, upper1: Double, lower2: Double, upper2: Double): Boolean = {
    lower2 < upper1 && upper2 > lower1
  }


  def isCollision(object1: GameObject, object2: GameObject): Boolean = {
    val p1 = object1.location
    val p2 = object2.location

    val d1 = object1.dimensions
    val d2 = object2.dimensions

    segmentsOverlap(p1.x, p1.x + d1.x, p2.x, p2.x + d2.x) &&
      segmentsOverlap(p1.y, p1.y + d1.y, p2.y, p2.y + d2.y)
  }


  def updateObject(dynamicObject: DynamicObject, deltaTime: Double): Unit = {

    dynamicObject.previousLocation.x = dynamicObject.location.x
    dynamicObject.previousLocation.y = dynamicObject.location.y

    dynamicObject.location.x += deltaTime * dynamicObject.velocity.x
    dynamicObject.location.y += deltaTime * dynamicObject.velocity.y
  }


  // Primary Objective: Update world
  def updateWorld(world: World, deltaTime: Double): Unit = {

    for (obj: DynamicObject <- world.dynamicObjects) {
      this.updateObject(obj, deltaTime)
    }

    for (obj: DynamicObject <- world.dynamicObjects) {
      for (sObj: StaticObject <- world.staticObjects) {
        if (isCollision(obj, sObj)) {
          obj.collideWithStaticObject(sObj)
          sObj.collideWithDynamicObject(obj)
        }
      }

      var foundThis: Boolean = false
      for (obj2: DynamicObject <- world.dynamicObjects) {
        if (foundThis) {
          if (isCollision(obj, obj2)) {
            obj.collideWithDynamicObject(obj2)
            obj2.collideWithDynamicObject(obj)
          }
        }
        // "foundThis" is used to avoid colliding objects twice on a single update
        if (obj2 == obj) {
          foundThis = true
        }
      }
    }

  }


}
