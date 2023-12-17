package game

import akka.actor.{Actor, ActorRef, Props}
import game.enemyai.AIActor


class AIController(gameActor: ActorRef) extends Actor {

  val numberOfAIPlayers = 3

  var aiPlayers: Map[ActorRef, String] = Map()

  for (i <- 0 until numberOfAIPlayers) {
    val playerId = "AI_" + i.toString
    aiPlayers += context.actorOf(Props(classOf[AIActor], gameActor, playerId)) -> playerId
    gameActor ! AddPlayerAtSpawner(playerId)
  }

  override def receive: Receive = {
    case Update => gameActor ! SendGameState
    case gs: GameStateMessage => aiPlayers.keys.foreach(ai => ai ! gs)
  }

}
