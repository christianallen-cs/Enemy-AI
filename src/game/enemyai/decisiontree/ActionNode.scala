package game.enemyai.decisiontree

import game.AIAction
import game.enemyai.AIGameState

class ActionNode(f: AIGameState => AIAction) extends DecisionTreeValue {

  override def check(gameState: AIGameState): Int = {
    0
  }

  override def action(gameState: AIGameState): AIAction = {
    f(gameState)
  }

}
