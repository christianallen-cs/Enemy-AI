package game

import game.gameobjects._
import game.maps.{GridLocation, Level}
import game.physics.{PhysicsEngine, PhysicsVector, World}


class Game {

  val gameState: GameState = new GameState()
  val world: World = new World(10)
  val playerSize: Double = 0.5
  var lastUpdateTime: Long = System.nanoTime()
  val projectileSpeed: Double = 5.0
  val projectileSize: Double = 0.1

  loadLevel(Level(0))


  def loadLevel(newLevel: Level): Unit = {
    world.staticObjects = List()
    this.gameState.level = newLevel

    this.gameState.projectiles.foreach(po => po.destroy())
    this.gameState.projectiles = List()

    this.gameState.walls = List()
    this.gameState.level.wallLocations.foreach(wall => placeWall(wall.x, wall.y))
  }


  def addPlayerAtStart(id: String): Unit = {
    addPlayerWithLocation(id, gameState.level.startingLocation.x, gameState.level.startingLocation.y)
  }

  def addPlayerAtSpawner(id: String): Unit = {
    val spawner: GridLocation = this.gameState.level.enemySpawners((Math.random()*this.gameState.level.enemySpawners.length).toInt)
    val newPlayer = addPlayerWithLocation(id, spawner.x, spawner.y)
    newPlayer.respawnLocations = this.gameState.level.enemySpawners.map((location: GridLocation) => new PhysicsVector(location.x+0.5, location.y+0.5))
  }

  def addPlayerWithLocation(id: String, x: Int, y:Int): Player = {
    val player = new Player(new PhysicsVector(x + 0.5, y + 0.5), new PhysicsVector(this.playerSize, this.playerSize), id)
    this.gameState.players += (id -> player)
    world.dynamicObjects = player :: world.dynamicObjects
    player
  }


  def removePlayer(id: String): Unit = {
    this.gameState.players(id).destroyed = true
    this.gameState.players -= id
  }


  def placeWall(x: Int, y: Int): Unit = {
    val wall: Wall = new Wall(x, y)
    this.gameState.walls = wall :: this.gameState.walls
    this.world.staticObjects ::= wall
  }


  def fire(playerId: String): Unit ={
    if(this.gameState.players.contains(playerId)){
      val orientation = this.gameState.players(playerId).orientation
      fireWithDirection(playerId, orientation.x, orientation.y)
    }
  }

  def fireWithDirection(playerId: String, xDirection: Double, yDirection: Double): Unit = {
    if(this.gameState.players.contains(playerId)){
      val location = this.gameState.players(playerId).location
      this.addProjectile(new Projectile(new PhysicsVector(location.x + playerSize/2.0, location.y + playerSize/2.0),
        new PhysicsVector(xDirection, yDirection),
        new PhysicsVector(this.projectileSize, this.projectileSize),
        playerId
      ))
    }
  }


  def addProjectile(projectile: Projectile): Unit = {
    val normalizeVelocity: PhysicsVector = projectile.velocity.normal2d()
    projectile.velocity.x = normalizeVelocity.x * projectileSpeed
    projectile.velocity.y = normalizeVelocity.y * projectileSpeed

    this.gameState.projectiles = projectile :: this.gameState.projectiles
    world.dynamicObjects = projectile :: world.dynamicObjects
  }


  def update(): Unit = {
    val time: Long = System.nanoTime()
    val dt = (time - this.lastUpdateTime) / 1000000000.0
    PhysicsEngine.updateWorld(this.world, dt)

    // remove destroyed objects
    this.gameState.projectiles = this.gameState.projectiles.filter(!_.destroyed)
    this.world.dynamicObjects = this.world.dynamicObjects.filter(!_.destroyed)

    this.lastUpdateTime = time
  }

  def gameStateJson(): String = {
    this.gameState.asJsonString()
  }

}
