package cardTracker;

import org.junit.jupiter.api.Test;

class CardTrackerTest {

    @Test
    void generateState() {
    }

    @Test
    void testGenerateState() {
        CardTracker tracker = new CardTracker();
        try {
            System.out.println(tracker.generateState(null).getPrintFormat());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}