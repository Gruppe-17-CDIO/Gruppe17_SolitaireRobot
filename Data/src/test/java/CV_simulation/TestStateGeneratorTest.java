package CV_simulation;

import dataObjects.SolitaireState;
import org.junit.jupiter.api.Test;

class TestStateGeneratorTest {

    @Test
    void getTestState() {
        int iterations = 1;
        for (int i = 0; i < iterations; i++) {
            SolitaireState state = null;
            try {
                state = TestStateGenerator.getTestState(0);
                state.printState();
            } catch (Exception e) {
                e.printStackTrace();
            }
            assert (state != null);
        }
    }
}