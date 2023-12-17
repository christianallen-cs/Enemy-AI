package game

// Received by GameServer
case object SendGameState
case class GameStateMessage(gameState: String)


// Received by GameActor
abstract class AIAction()

case class MovePlayer(username: String, x: Double, y:Double) extends AIAction
case class StopPlayer(username: String) extends AIAction
case class FireWithDirection(username: String, xDirection: Double, yDirection: Double) extends AIAction
case class Fire(username: String) extends AIAction

case object UpdateGame
case class AddPlayerAtStart(username: String)
case class AddPlayerAtSpawner(username: String)
case class AddPlayerWithLocation(username: String, x: Int, y: Int)
case class RemovePlayer(username: String)


// Received by AISystem
case object Update


