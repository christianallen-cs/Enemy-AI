package game.enemyai.decisiontree

import game.AIAction
import game.enemyai.AIGameState

abstract class DecisionTreeValue{

  // returns a negative number if you should go left
  // returns a positive number if you should go right
  // returns 0 if this node contains the action
  def check(gameState: AIGameState): Int


  def action(gameState: AIGameState): AIAction

}
