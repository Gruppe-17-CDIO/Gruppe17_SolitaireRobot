import dataObjects.Move;
import org.junit.jupiter.api.Test;
import dataObjects.SolitaireState;
import CV_simulation.StateGenerator;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LogicTest {
    @Test
    void pileMovesTest() {

        /*
         * Possible moves between the seven piles
         * Uses build-a-state_1 with three possible moves
         */

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

        // Assert

        // piles[2,2] -> piles[1]
        assertEquals(Move.MoveType.MOVE, moves.get(0).getMoveType());
        assertArrayEquals(new int[]{2,2}, moves.get(0).getPosition());
        assertEquals(Move.DestinationType.PILE, moves.get(0).getDestinationType());
        assertEquals(1, moves.get(0).getDestPosition());

        // piles[3,3] -> piles[0]
        assertEquals(Move.MoveType.MOVE, moves.get(1).getMoveType());
        assertArrayEquals(new int[]{3,3}, moves.get(1).getPosition());
        assertEquals(Move.DestinationType.PILE, moves.get(1).getDestinationType());
        assertEquals(0, moves.get(1).getDestPosition());

        // piles[5,5] -> piles[6]
        assertEquals(Move.MoveType.MOVE, moves.get(2).getMoveType());
        assertArrayEquals(new int[]{5,5}, moves.get(2).getPosition());
        assertEquals(Move.DestinationType.PILE, moves.get(2).getDestinationType());
        assertEquals(6, moves.get(2).getDestPosition());
    }

    @Test
    void mixedTest() {

        /*
         * Uses build-a-state_0 with two possible moves
         */

        // Arrange
        SolitaireState state = null;
        I_Logic logic = new Logic();
        List<Move> moves;

        try {
            state = StateGenerator.getState(0);
            System.out.println(state.getPrintFormat());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        // Act
        moves = logic.getMoves(state);

        // Assert

        // piles[4,7] -> foundation[0]
        assertEquals(Move.MoveType.MOVE, moves.get(0).getMoveType());
        assertArrayEquals(new int[]{4,7}, moves.get(0).getPosition());
        assertEquals(Move.DestinationType.FOUNDATION, moves.get(0).getDestinationType());
        assertEquals(0, moves.get(0).getDestPosition());

        // piles[0,1] -> piles[5]
        assertEquals(Move.MoveType.MOVE, moves.get(1).getMoveType());
        assertArrayEquals(new int[]{0,1}, moves.get(1).getPosition());
        assertEquals(Move.DestinationType.PILE, moves.get(1).getDestinationType());
        assertEquals(5, moves.get(1).getDestPosition());

        // piles[2,2] -> piles[6]
        assertEquals(Move.MoveType.MOVE, moves.get(2).getMoveType());
        assertArrayEquals(new int[]{2,2}, moves.get(2).getPosition());
        assertEquals(Move.DestinationType.PILE, moves.get(2).getDestinationType());
        assertEquals(6, moves.get(2).getDestPosition());
    }
}