import CV_simulation.TestStateGenerator;
import dataObjects.SolitaireState;
import logger.CardLogger;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

// **********************************
// IMPORTANT: These tests delete all
// data in current session's log!
// **********************************

class CardLoggerTest {
    int iterations = 100;

    @Test
    void logCards() {
        TestStateGenerator generator = new TestStateGenerator();
        System.out.println("logCards: Writing to file, then reading...");
        CardLogger logger = new CardLogger();
        logger.deleteCurrentSessionData();
        List<SolitaireState> cardList = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            SolitaireState cards = null;
            try {
                cards = generator.getTestState(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cardList.add(cards);
            //System.out.println("Logging " + (i + 1));
            logger.logCards(cards);
        }

        assert (logger.getHistory().size() == iterations);
        System.out.println("Done");
    }

    @Test
    void getHistory() {
        System.out.println("getHistory: Writing to file, then reading...");
        CardLogger logger = new CardLogger();
        logger.deleteCurrentSessionData();
        assert (logger.getHistory().isEmpty());
        List<SolitaireState> cardList = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            SolitaireState cards = new SolitaireState();
            cardList.add(cards);
            logger.logCards(cards);
        }

        List<SolitaireState> reply = logger.getHistory();

        for (int i = 0; i < iterations; i++) {
            String t1 = reply.get(i).time;
            String t2 = cardList.get(i).time;
            assert (t1.equals(t2));
        }
        System.out.println("Done");
    }

    @Test
    void deleteData() {
        CardLogger logger = new CardLogger();
        logger.deleteCurrentSessionData();
        assert (logger.getHistory().isEmpty());
    }
}