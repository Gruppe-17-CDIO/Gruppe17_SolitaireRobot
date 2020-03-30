import dataObjects.Move;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import dataObjects.SolitaireState;
import CV_simulation.StateGenerator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LogicTest {
    @Test
    void getMovesTest() {

        // Arrange
        SolitaireState state = null;
        I_Logic logic = new Logic();
        List<Move> moves;

        try {
            state = StateGenerator.getState(1);
            System.out.println(state.getPrintFormat());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        // Act
        moves = logic.getMoves(state);

        // Assert: Possible moves - ordered by algorithm, but not weighted yet:

        // piles[2,2] -> piles[1]
        int[] pos = {2,2};
        assertEquals(Move.MoveType.MOVE, moves.get(0).getMoveType());
        assertArrayEquals(pos, moves.get(0).getPosition());
        assertEquals(Move.DestinationType.PILE, moves.get(0).getDestinationType());
        assertEquals(1, moves.get(0).getDestPosition());

        // piles[3,3] -> piles[0]
        pos[0] = 3; pos[1] = 3;
        assertEquals(Move.MoveType.MOVE, moves.get(1).getMoveType());
        assertArrayEquals(pos, moves.get(1).getPosition());
        assertEquals(Move.DestinationType.PILE, moves.get(1).getDestinationType());
        assertEquals(0, moves.get(1).getDestPosition());

        // piles[5,5] -> piles[6]
        pos[0] = 5; pos[1] = 5;
        assertEquals(Move.MoveType.MOVE, moves.get(2).getMoveType());
        assertArrayEquals(pos, moves.get(2).getPosition());
        assertEquals(Move.DestinationType.PILE, moves.get(2).getDestinationType());
        assertEquals(6, moves.get(2).getDestPosition());
    }
}