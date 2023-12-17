package game

import akka.actor.Actor
import game.physics.PhysicsVector


class GameActor extends Actor {

  val game: Game = new Game()

  override def receive: Receive = {
    case message: AddPlayerAtStart => game.addPlayerAtStart(message.username)
    case message: AddPlayerAtSpawner => game.addPlayerAtSpawner(message.username)
    case message: AddPlayerWithLocation => game.addPlayerWithLocation(message.username, message.x, message.y)
    case message: RemovePlayer => game.removePlayer(message.username)
    case message: MovePlayer => game.gameState.players(message.username).move(new PhysicsVector(message.x, message.y))
    case message: StopPlayer => game.gameState.players(message.username).stop()
    case message: Fire => game.fire(message.username)
    case message: FireWithDirection => game.fireWithDirection(message.username, message.xDirection, message.yDirection)
    case UpdateGame => game.update()
    case SendGameState => sender() ! GameStateMessage(game.gameStateJson())
  }
}
