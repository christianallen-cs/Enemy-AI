package game.enemyai

import akka.actor.{Actor, ActorRef}
import game.enemyai.decisiontree.{ActionNode, DecisionNode, DecisionTreeValue}
import game.lo4_data_structures.linkedlist.LinkedListNode
import game.lo4_data_structures.trees.BinaryTreeNode
import game.maps.GridLocation
import game.physics.PhysicsVector
import game.{AIAction, FireWithDirection, GameStateMessage, MovePlayer, StopPlayer}

class AIActor(gameActor: ActorRef, id: String) extends Actor {

  val aiPlayer = new AIPlayer(id)

  override def receive: Receive = {
    case gs: GameStateMessage =>

      val gameState: AIGameState = new AIGameState
      gameState.parseJsonState(gs.gameState)

      val action: AIAction = aiPlayer.makeDecision(gameState, decisionTree(aiPlayer))

      gameActor ! action
  }



  // assumes the path is not empty/null
  def pathToDirection(referencePlayer: AIPlayer, playerLocations: LinkedListNode[PlayerLocation], path: LinkedListNode[GridLocation]): PhysicsVector = {
    val myLocation: PlayerLocation = referencePlayer.locatePlayer(referencePlayer.id, playerLocations)

    val targetGridLocation: GridLocation = if (path.next == null) path.value else path.next.value
    val targetVector: PhysicsVector = targetGridLocation.centerAsVector()

    new PhysicsVector(targetVector.x - myLocation.x, targetVector.y - myLocation.y)
  }


  def decisionTree(referencePlayer: AIPlayer): BinaryTreeNode[DecisionTreeValue] = {

    val moveTowardsClosest: ActionNode = new ActionNode((gameState: AIGameState) => {
      val path = referencePlayer.getPath(gameState)
      val direction: PhysicsVector = pathToDirection(referencePlayer, gameState.playerLocations, path)

      MovePlayer(referencePlayer.id, direction.x, direction.y)
    })

    val fireTowardsClosest: ActionNode = new ActionNode((gameState: AIGameState) => {

      val myLocation: PlayerLocation = referencePlayer.locatePlayer(referencePlayer.id, gameState.playerLocations)
      val closestPlayer: PlayerLocation = referencePlayer.closestPlayerAvoidWalls(gameState)
      val direction: PhysicsVector = new PhysicsVector(closestPlayer.x - myLocation.x, closestPlayer.y - myLocation.y)

      FireWithDirection(referencePlayer.id, direction.x, direction.y)
    })

    val stop: ActionNode = new ActionNode((_: AIGameState) => {
      StopPlayer(referencePlayer.id)
    })

    val moveTowardsClosestNode = new BinaryTreeNode[DecisionTreeValue](moveTowardsClosest, null, null)
    val fireTowardsClosestNode = new BinaryTreeNode[DecisionTreeValue](fireTowardsClosest, null, null)
    val stopNode = new BinaryTreeNode[DecisionTreeValue](stop, null, null)


    val rootDecider = new DecisionNode(
      (gameState: AIGameState) => {
        val path = referencePlayer.getPath(gameState)
        if ((path.size() - 1) >= 10) {
          1
        } else {
          -1
        }
      }
    )

    val leftDecider = new DecisionNode((gameState: AIGameState) => {
      val path = referencePlayer.getPath(gameState)
      if ((path.size() - 1) >= 5) {
        1
      } else {
        -1
      }
    }
    )


    val decisionTree = new BinaryTreeNode[DecisionTreeValue](
      rootDecider, new BinaryTreeNode[DecisionTreeValue](leftDecider, fireTowardsClosestNode, moveTowardsClosestNode), stopNode
    )

    decisionTree
  }
}
