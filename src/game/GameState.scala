package game

import game.gameobjects.{Player, Projectile, Wall}
import game.maps.Level
import play.api.libs.json.{JsValue, Json}

class GameState {

  var walls: List[Wall] = List()
  var projectiles: List[Projectile] = List()
  var level: Level = new Level()
  var players: Map[String, Player] = Map()


  def asJsonString(): String = {
    val gameState: Map[String, JsValue] = Map(
      "gridSize" -> Json.toJson(Map("x" -> this.level.gridWidth, "y" -> this.level.gridHeight)),
      "start" -> Json.toJson(Map("x" -> this.level.startingLocation.x, "y" -> this.level.startingLocation.y)),
      "spawners" -> Json.toJson(this.level.enemySpawners.map({ w => Json.toJson(Map("x" -> w.x, "y" -> w.y)) })),
      "walls" -> Json.toJson(this.walls.map({ w => Json.toJson(Map("x" -> w.x, "y" -> w.y)) })),
      "players" -> Json.toJson(this.players.map({ case (k, v) => Json.toJson(Map(
        "x" -> Json.toJson(v.location.x),
        "y" -> Json.toJson(v.location.y),
        "v_x" -> Json.toJson(v.velocity.x),
        "v_y" -> Json.toJson(v.velocity.y),
        "id" -> Json.toJson(k))) })),
      "projectiles" -> Json.toJson(this.projectiles.map({ po => Json.toJson(Map("x" -> po.location.x, "y" -> po.location.y)) }))
    )

    Json.stringify(Json.toJson(gameState))
  }
}
