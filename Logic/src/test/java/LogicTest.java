import dataObjects.Move;
import org.junit.jupiter.api.Test;
import dataObjects.SolitaireState;
import CV_simulation.StateGenerator;
import sun.jvm.hotspot.utilities.Assert;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LogicTest {
    @Test
    void getMovesTest() {
        //Arrange
        SolitaireState state = null;
        I_Logic logic = new Logic();
        Move move = null;

        try {
            state = StateGenerator.getState(1);
            System.out.println(state.getPrintFormat());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        //Act
        move = logic.getMove(state);

        //Assert
        /*
         * Possible moves - ordered, but not weighted:
         * piles[2][0] -> piles[1]
         * piles[5][0] -> piles[6]
         * piles[3][3] -> piles[0]
         * Drawn[0] -> Foundation[1]
         * Drawn[1] -> piles[3]
         */
        assertEquals(move.getAvailableType(), Move.Type.DRAW);
    }
}