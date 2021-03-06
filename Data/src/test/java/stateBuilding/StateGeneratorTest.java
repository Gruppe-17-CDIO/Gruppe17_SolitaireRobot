package stateBuilding;

import dataObjects.SolitaireState;
import org.junit.jupiter.api.Test;

class StateGeneratorTest {

    @Test
    void getTestState() {
        int iterations = 2;
        for (int i = 0; i < iterations; i++) {
            SolitaireState state = null;
            try {
                state = new StateGenerator().getState(i);
                System.out.println(state.getPrintFormat());
            } catch (Exception e) {
                e.printStackTrace();
            }
            assert (state != null);
        }
    }
}