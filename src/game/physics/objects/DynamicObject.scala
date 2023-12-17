package game.physics.objects

import game.physics.PhysicsVector

/**
  * This class represents a game object that does not have a fixed position. As such, these objects have a velocity
  * to determine their movement, and mass to determine their momentum. The physics engine will move these objects
  * during each update.
  *
  * @param location Initial location vector of the object
  * @param dimensions The size of the object
  */
abstract class DynamicObject(location: PhysicsVector, dimensions: PhysicsVector, val team: String = "") extends GameObject(location, dimensions) {

  var velocity: PhysicsVector = new PhysicsVector()


  /**
    * previousLocation is used by the physics engine and other objects to determine the movement vector of the
    * dynamic object during a particular update. This value is computed at the beginning of each update and
    * represents the location of the object before the object location was updated.
    *
    * Example Use: If an object collides with a wall its location can be set back to its previous location to "undo"
    * the movement
    */
  var previousLocation = new PhysicsVector()


  /**
    * Called by the physics engine whenever the object collides with a static object. By default, an object does not
    * react to such a collision. Extending classes will add functionality to this method as needed.
    *
    * @param staticObject The static object with which the object collided
    */
  def collideWithStaticObject(staticObject: StaticObject): Unit

}
