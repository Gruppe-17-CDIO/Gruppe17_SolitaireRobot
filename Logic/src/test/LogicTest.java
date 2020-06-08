import dataObjects.Move;
import dataObjects.SolitaireState;
import org.junit.jupiter.api.Test;
import stateBuilding.StateGenerator;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Anders Frandsen
 */

class LogicTest {
    @Test
    void pileMovesTest() {

        /*
         * Possible moves between the seven piles
         * Uses build-a-state_202 with four possible moves
         */

        // Arrange
        SolitaireState state = null;
        I_Logic logic = new Logic();
        List<Move> moves;

        try {
            state = new StateGenerator().getState(202);
            System.out.println(state.getPrintFormat());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        // Act
        moves = logic.getMoves(state);

        // Assert

        // Drawn card -> foundations[1]
        assertEquals(Move.MoveType.DRAW, moves.get(0).getMoveType());
        assertEquals(Move.DestinationType.FOUNDATION, moves.get(0).getDestinationType());
        assertEquals(1, moves.get(0).getDestPosition());

        // piles[2,2] -> piles[1]
        assertEquals(Move.MoveType.MOVE, moves.get(1).getMoveType());
        assertArrayEquals(new int[]{2,2}, moves.get(1).getPosition());
        assertEquals(Move.DestinationType.PILE, moves.get(1).getDestinationType());
        assertEquals(1, moves.get(1).getDestPosition());

        // piles[3,3] -> piles[0]
        assertEquals(Move.MoveType.MOVE, moves.get(2).getMoveType());
        assertArrayEquals(new int[]{3,3}, moves.get(2).getPosition());
        assertEquals(Move.DestinationType.PILE, moves.get(2).getDestinationType());
        assertEquals(0, moves.get(2).getDestPosition());

        // piles[5,5] -> piles[6]
        assertEquals(Move.MoveType.MOVE, moves.get(3).getMoveType());
        assertArrayEquals(new int[]{5,5}, moves.get(3).getPosition());
        assertEquals(Move.DestinationType.PILE, moves.get(3).getDestinationType());
        assertEquals(6, moves.get(3).getDestPosition());
    }

    @Test
    void mixedTest() {

        /*
         * Uses build-a-state_201 with six possible moves
         */

        // Arrange
        SolitaireState state = null;
        I_Logic logic = new Logic();
        List<Move> moves;

        try {
            state = new StateGenerator().getState(201);
            System.out.println(state.getPrintFormat());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        // Act
        moves = logic.getMoves(state);

        // Assert

        // Drawn card -> foundation[2]
        assertEquals(Move.MoveType.DRAW, moves.get(0).getMoveType());
        assertEquals(Move.DestinationType.FOUNDATION, moves.get(0).getDestinationType());
        assertEquals(2, moves.get(0).getDestPosition());

        // piles[4,7] -> foundation[0]
        assertEquals(Move.MoveType.MOVE, moves.get(1).getMoveType());
        assertArrayEquals(new int[]{4,7}, moves.get(1).getPosition());
        assertEquals(Move.DestinationType.FOUNDATION, moves.get(1).getDestinationType());
        assertEquals(0, moves.get(1).getDestPosition());

        // piles[0,1] -> piles[5]
        assertEquals(Move.MoveType.MOVE, moves.get(2).getMoveType());
        assertArrayEquals(new int[]{0,1}, moves.get(2).getPosition());
        assertEquals(Move.DestinationType.PILE, moves.get(2).getDestinationType());
        assertEquals(5, moves.get(2).getDestPosition());

        // piles[2,2] -> piles[6]
        assertEquals(Move.MoveType.MOVE, moves.get(3).getMoveType());
        assertArrayEquals(new int[]{2,2}, moves.get(3).getPosition());
        assertEquals(Move.DestinationType.PILE, moves.get(3).getDestinationType());
        assertEquals(6, moves.get(3).getDestPosition());

        // piles[3,3] -> piles[0]
        assertEquals(Move.MoveType.MOVE, moves.get(4).getMoveType());
        assertArrayEquals(new int[]{3,3}, moves.get(4).getPosition());
        assertEquals(Move.DestinationType.PILE, moves.get(4).getDestinationType());
        assertEquals(0, moves.get(4).getDestPosition());

        // Drawn card -> piles[4]
        assertEquals(Move.MoveType.DRAW, moves.get(5).getMoveType());
        assertEquals(Move.DestinationType.PILE, moves.get(5).getDestinationType());
        assertEquals(4, moves.get(5).getDestPosition());
    }
}