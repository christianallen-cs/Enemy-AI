# UNIVERSITY AT BUFFALO

## Enemy AI

## Overview

This project involves programming the AI for a 2D top-down game where enemies need to exhibit intelligent behaviors to compete with the player and other AI players. The project is based on a skeleton code and involves various tasks to enhance the AI capabilities.

### Lecture Task 1: Closest Player

**Functionality**:
1. **`locatePlayer`**:
   - **Parameters**: `String playerId`, `LinkedListNode<PlayerLocation> playerLocations`
   - **Returns**: `PlayerLocation` of the player with the specified `playerId`.

2. **`closestPlayer`**:
   - **Parameters**: `LinkedListNode<PlayerLocation> playerLocations`
   - **Returns**: `PlayerLocation` of the closest player (excluding itself), using Euclidean distance.

**Testing**:
- Implement tests in the `tests` package under `LectureTask1`.

### Lecture Task 2: Path to Direction

**Functionality**:
1. **`pathToDirection`**:
   - **Parameters**: `LinkedListNode<PlayerLocation> playerLocations`, `LinkedListNode<GridLocation> path`
   - **Returns**: `PhysicsVector` specifying the direction to move towards the center of the target tile.

**Testing**:
- Implement tests in the `tests` package under `LectureTask2`.

### Lecture Task 3: Compute Path

**Functionality**:
1. **`computePath`**:
   - **Parameters**: `GridLocation start`, `GridLocation end`
   - **Returns**: `LinkedListNode<GridLocation>` representing the shortest path between `start` and `end` using valid moves (up, down, left, right).

**Testing**:
- Implement tests in the `tests` package under `LectureTask3`.

### Lecture Task 4: Making Decisions

**Functionality**:
1. **`makeDecision`**:
   - **Parameters**: `AIGameState gameState`, `BinaryTreeNode<DecisionTreeValue> decisionTree`
   - **Returns**: Action determined by traversing the decision tree.

**Testing**:
- No required testing for this task.

### Lecture Task 5: Distance - Avoid Walls

**Functionality**:
1. **`distanceAvoidWalls`**:
   - **Parameters**: `AIGameState gameState`, `GridLocation start`, `GridLocation end`
   - **Returns**: Shortest distance between `start` and `end` avoiding walls.

**Testing**:
- Implement tests in the `tests` package under `LectureTask5`.

### Lecture Task 6: Closest Player - Avoid Walls

**Functionality**:
1. **`closestPlayerAvoidWalls`**:
   - **Parameters**: `AIGameState gameState`
   - **Returns**: Closest player that can be reached while avoiding walls.

**Testing**:
- Implement tests in the `tests` package under `LectureTask6`.

## Additional Notes

- **Level as Graph**: Use the `levelAsGraph` method in the `AIGameState` class for graph-based tasks.
- **Testing**: Ensure comprehensive testing for each implemented functionality to validate AI behavior.
