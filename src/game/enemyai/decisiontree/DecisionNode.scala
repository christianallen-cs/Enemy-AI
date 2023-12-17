package game.enemyai.decisiontree

import game.enemyai.AIGameState
import game.{AIAction, StopPlayer}

class DecisionNode(f: AIGameState => Int) extends DecisionTreeValue {

  override def check(gameState: AIGameState): Int = {
    f(gameState)
  }

  override def action(gameState: AIGameState): AIAction = {
    // This should never be called
    StopPlayer("AI_0")
  }

}
