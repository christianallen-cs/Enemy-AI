package game.enemyai

import game.enemyai.decisiontree.DecisionTreeValue
import game.lo4_data_structures.graphs._
import game.lo4_data_structures.linkedlist.{LinkedListNode, QueueClass}
import game.lo4_data_structures.trees.BinaryTreeNode
import game.maps.GridLocation
import game.{AIAction, MovePlayer}

class AIPlayer(val id: String) {

  var data: PlayerLocation = new PlayerLocation(0, 0, "")


  // TODO: Replace this placeholder code with your own
  def locatePlayer(playerId: String, playerLocations: LinkedListNode[PlayerLocation]): PlayerLocation = {
    if (playerId == playerLocations.value.playerId) {
      playerLocations.value
    } else {
      locatePlayer(playerId, playerLocations.next)
    }
  }

  // TODO: Replace this placeholder code with your own
  def closestPlayer(playerLocations: LinkedListNode[PlayerLocation]): PlayerLocation = {
    this.data = locatePlayer(this.id, playerLocations)
    val list: List[PlayerLocation] = listHelper(playerLocations)
    var comparator: PlayerLocation = new PlayerLocation(list.head.x + list.apply(1).x, list.head.y + list.apply(1).y, "comparator")
    for (i <- list) {
      if ((distanceCalc(i) < distanceCalc(comparator)) && (!(distanceCalc(i) == 0))) {
        comparator = i
      }
    }
    comparator
  }

  def listHelper(param: LinkedListNode[PlayerLocation]): List[PlayerLocation] = {
    if (param.next == null) {
      List(param.value)
    } else {
      listHelper(param.next) ++ List(param.value)
    }
  }

  def distanceCalc(input: PlayerLocation): Double = {
    Math.sqrt(Math.pow(input.x - this.data.x, 2) + Math.pow(input.y - this.data.y, 2))
  }


  // TODO: Replace this placeholder code with your own
  def computePath(start: GridLocation, end: GridLocation): LinkedListNode[GridLocation] = {
    val swag: GridLocation = start
    computePathHelper(swag, end)
  }

  def computePathHelper(startingGrid: GridLocation, endingGrid: GridLocation): LinkedListNode[GridLocation] = {
    if (startingGrid.x > endingGrid.x) {
      val first: GridLocation = startingGrid
      computePathHelper(new GridLocation(startingGrid.x - 1, startingGrid.y), endingGrid).prepend(first)
    } else if (startingGrid.x < endingGrid.x) {
      val second: GridLocation = startingGrid
      computePathHelper(new GridLocation(startingGrid.x + 1, startingGrid.y), endingGrid).prepend(second)
    } else if (startingGrid.y > endingGrid.y) {
      val third: GridLocation = startingGrid
      computePathHelper(new GridLocation(startingGrid.x, startingGrid.y - 1), endingGrid).prepend(third)
    } else if (startingGrid.y < endingGrid.y) {
      val fourth: GridLocation = startingGrid
      computePathHelper(new GridLocation(startingGrid.x, startingGrid.y + 1), endingGrid).prepend(fourth)
    } else {
      new LinkedListNode[GridLocation](endingGrid, null)
    }
  }

  // TODO: Replace this placeholder code with your own
  def makeDecision(gameState: AIGameState, decisionTree: BinaryTreeNode[DecisionTreeValue]): AIAction = {
    makeDecisionHelperMethod(gameState, decisionTree)
  }

  def makeDecisionHelperMethod(state: AIGameState, Tree: BinaryTreeNode[DecisionTreeValue]): AIAction = {
    if (Tree.value.check(state) == 0) {
      Tree.value.action(state)
    } else if (Tree.value.check(state) < 0) {
      makeDecisionHelperMethod(state, Tree.left)
    } else {
      makeDecisionHelperMethod(state, Tree.right)
    }
  }


  // TODO: Replace this placeholder code with your own
  def closestPlayerAvoidWalls(gameState: AIGameState): PlayerLocation = {
    closestPlayer(gameState.playerLocations)
  }

  // TODO: Replace this placeholder code with your own
  def getPath(gameState: AIGameState): LinkedListNode[GridLocation] = {
    computePath(locatePlayer(this.id, gameState.playerLocations).asGridLocation(), closestPlayerAvoidWalls(gameState).asGridLocation())
  }

  def distanceAvoidWalls(gameState: AIGameState, location1: GridLocation, location2: GridLocation): Int = {

    val levels: Graph[GridLocation] = gameState.levelAsGraph()
    val graph1code: Int = DawHelperMethod1(location1, gameState)
    val graph2code: Int = DawHelperMethod1(location2, gameState)
    val lines = new QueueClass[Int]
    var together: Map[Int, Int] = Map()

    lines.inQueue(graph1code)

    while (lines.front != null) {
      val next: Int = lines.unQueue()
      val list: List[Int] = levels.adjacencyList(next)
      for (i <- list) {
        if (together.getOrElse(i, 1) == 1) {
          together = together + (i -> next)
          lines.inQueue(i)
        }
      }
    }
    DawHelperMethod2(graph1code, graph2code, together)
  }

  def DawHelperMethod1(location: GridLocation, level: AIGameState): Int = {
    location.y * level.levelWidth + location.x
  }

  def DawHelperMethod2(x: Int, y: Int, maps: Map[Int, Int]): Int = {
    val current: Int = maps(y)
    if (current == x) {
      1
    } else {
      DawHelperMethod2(x, current, maps) + 1
    }
  }
}

