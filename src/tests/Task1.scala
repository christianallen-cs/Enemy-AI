package tests

import game.enemyai.{AIPlayer, PlayerLocation}
import game.lo4_data_structures.linkedlist.LinkedListNode
import game.maps.GridLocation
import org.scalatest._

class Task1 extends FunSuite {

  val EPSILON: Double = 0.001

  def compareDoubles(d1: Double, d2: Double): Boolean = {
    Math.abs(d1 - d2) < EPSILON
  }

  def apply[A](i: Int, list: LinkedListNode[A]): LinkedListNode[A] = {
    if (i == 0) {
      list
    } else {
      apply(i - 1, list.next)
    }
  }

  test("test locate player") {

    val player: AIPlayer = new AIPlayer("player1")

    var list: LinkedListNode[PlayerLocation] = new LinkedListNode(
      new PlayerLocation(3.0, 4.0, "player2"), null)

    list = new LinkedListNode(new PlayerLocation(1.0, 8.0, "player1"), list)

    val actual: PlayerLocation = player.locatePlayer("player2", list)

    assert(actual.playerId == "player2")
    assert(actual.x == 3.0)
    assert(actual.y == 4.0)
  }

  test("test compute path") {

    val player: AIPlayer = new AIPlayer("player1")
    var path: LinkedListNode[GridLocation] = player.computePath(
      new GridLocation(2, 2),
      new GridLocation(4, 4))

    //TODO: Check if path starts with (2, 2)
    //TODO: Check if path ends with (4, 4)
    //TODO: Check that the length of the path is minimal
    //TODO: Check if all moves are valid (up, down, left, right)
  }

  test("xd") {

    val constantPlayer: PlayerLocation = new PlayerLocation(0, 0, "AI1")
    val player1: PlayerLocation = new PlayerLocation(1, 0, "1")
    val player2: PlayerLocation = new PlayerLocation(2, 0, "2")
    val player3: PlayerLocation = new PlayerLocation(3, 0, "3")

    val list: LinkedListNode[PlayerLocation] =
      new LinkedListNode[PlayerLocation](constantPlayer,
       new LinkedListNode[PlayerLocation](player1,
        new LinkedListNode[PlayerLocation](player2,
         new LinkedListNode[PlayerLocation](player3, null))))

    val ai: AIPlayer = new AIPlayer("AI1")
    val close: PlayerLocation = ai.closestPlayer(list)

    assert(close.asGridLocation().equals(player1.asGridLocation()))
  }

  test("swag") {

    val playa: AIPlayer = new AIPlayer("playa")
    val start: GridLocation = new GridLocation(0, 0)
    val end: GridLocation = new GridLocation(1, 1)
    val path: LinkedListNode[GridLocation] = playa.computePath(start, end)
    val actual: GridLocation = apply(path.size() - 1, path).value

    assert(path.value.equals(start))
    assert(path.next.value.equals(new GridLocation(1, 0)))
    assert(path.next.next.value.equals(actual))
  }

  test("negatives") {

    val playuh: AIPlayer = new AIPlayer("playuh")
    val start: GridLocation = new GridLocation(5, 5)
    val end: GridLocation = new GridLocation(-5, -5)
    val computed: LinkedListNode[GridLocation] = playuh.computePath(start, end)
    val actual: GridLocation = apply(computed.size() - 1, computed).value

    assert(end.equals(actual))
    assert(computed.size() == 21)
    assert(computed.value.equals(start))
  }

}
