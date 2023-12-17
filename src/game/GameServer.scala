package game

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.corundumstudio.socketio.listener.{ConnectListener, DataListener, DisconnectListener}
import com.corundumstudio.socketio.{AckRequest, Configuration, SocketIOClient, SocketIOServer}
import play.api.libs.json.Json

import scala.concurrent.duration

class GameServer(val gameActor: ActorRef) extends Actor {

  var socketToActor: Map[SocketIOClient, ActorRef] = Map()
  var actorToSocket: Map[ActorRef, SocketIOClient] = Map()

  val config: Configuration = new Configuration {
    setHostname("0.0.0.0")
    setPort(8080)
  }

  val server: SocketIOServer = new SocketIOServer(config)

  server.addConnectListener(new ConnectionListener(this))
  server.addDisconnectListener(new DisconnectionListener(this))
  server.addEventListener("keyStates", classOf[String], new KeyStatesListener(this))
  server.addEventListener("fire", classOf[String], new FireListener(this))

  server.start()


  override def receive: Receive = {
    case SendGameState =>
      gameActor ! SendGameState

    case gs: GameStateMessage =>
      server.getBroadcastOperations.sendEvent("gameState", gs.gameState)
  }

}


class ConnectionListener(server: GameServer) extends ConnectListener {
  override def onConnect(socket: SocketIOClient): Unit = {
    server.gameActor ! AddPlayerAtStart(socket.getSessionId.toString)
  }
}

class DisconnectionListener(server: GameServer) extends DisconnectListener {
  override def onDisconnect(socket: SocketIOClient): Unit = {
    server.gameActor ! RemovePlayer(socket.getSessionId.toString)
  }
}


class KeyStatesListener(server: GameServer) extends DataListener[String] {
  override def onData(socket: SocketIOClient, keyStatesJson: String, ackRequest: AckRequest): Unit = {

    val keyStates = Json.parse(keyStatesJson)

    val wDown = (keyStates \ "w").as[Boolean]
    val aDown = (keyStates \ "a").as[Boolean]
    val sDown = (keyStates \ "s").as[Boolean]
    val dDown = (keyStates \ "d").as[Boolean]

    var x = 0.0
    if (aDown && !dDown) {
      x = -1.0
    } else if (!aDown && dDown) {
      x = 1.0
    }

    var y = 0.0
    if (wDown && !sDown) {
      y = -1.0
    } else if (!wDown && sDown) {
      y = 1.0
    }

    if (x.abs > 0.001 || y.abs > 0.001) {
      server.gameActor ! MovePlayer(socket.getSessionId.toString, x, y)
    } else {
      server.gameActor ! StopPlayer(socket.getSessionId.toString)
    }

  }
}

class FireListener(server: GameServer) extends DataListener[String] {
  override def onData(socket: SocketIOClient, keyStatesJson: String, ackRequest: AckRequest): Unit = {
    server.gameActor ! Fire(socket.getSessionId.toString)
  }
}

object GameServer {

  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem()

    import actorSystem.dispatcher

    import scala.concurrent.duration._

    val gameActor = actorSystem.actorOf(Props(classOf[GameActor]))
    val server = actorSystem.actorOf(Props(classOf[GameServer], gameActor))
    val ai = actorSystem.actorOf(Props(classOf[AIController], gameActor))

    actorSystem.scheduler.scheduleWithFixedDelay(FiniteDuration(16, duration.MILLISECONDS), FiniteDuration(16, duration.MILLISECONDS), gameActor, UpdateGame)
    actorSystem.scheduler.scheduleWithFixedDelay(FiniteDuration(8, duration.MILLISECONDS), FiniteDuration(32, duration.MILLISECONDS), server, SendGameState)
    actorSystem.scheduler.scheduleWithFixedDelay(FiniteDuration(1000, duration.MILLISECONDS), FiniteDuration(1000, duration.MILLISECONDS), ai, Update)
  }

}
