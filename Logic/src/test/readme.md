# logic.Logic unittest

## Summary

logic.Logic implementation can be tested with a SolitaireState object using the StateGenerator class and builder-files.

### How-to

1. Copy/create a builder-file and put it in this module (logic.Logic) *.
2. Add junit test in ``src/test/LogicTest```

*see "Computer Vision Simulation" readme in Data-module.

#### Example
![](/mixedTest.png)

Expected valid moves for ```build-a-state_0``` prioritized order from 1-6.

Arrange and act:
```
// Arrange
SolitaireState state = null;
logic.I_Logic logic = new logic.Logic();
List<Move> moves;

try {
    state = StateGenerator.getState(0);
    System.out.println(state.getPrintFormat());
} catch (Exception ex) {
    System.out.println(ex.toString());
}

// Act
moves = logic.getMoves(state);
```

To check that the first move (index 0) in the list is from draw pile to foundation (index 2):
```
assertEquals(Move.MoveType.DRAW, moves.get(0).getMoveType());
assertEquals(Move.DestinationType.FOUNDATION, moves.get(0).getDestinationType());
assertEquals(2, moves.get(0).getDestPosition());
```

>Author: Anders