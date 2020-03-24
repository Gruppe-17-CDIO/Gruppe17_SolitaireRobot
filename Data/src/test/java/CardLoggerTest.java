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
        System.out.println("logCards: Writing to file, then reading...");
        CardLogger logger = new CardLogger();
        logger.deleteAllData();
        List<SolitaireCards> cardList = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            SolitaireCards cards = new SolitaireCards();
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
        logger.deleteAllData();
        assert (logger.getHistory().isEmpty());
        List<SolitaireCards> cardList = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            SolitaireCards cards = new SolitaireCards();
            cardList.add(cards);
            logger.logCards(cards);
        }

        List<SolitaireCards> reply = logger.getHistory();

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
        logger.deleteAllData();
        assert (logger.getHistory().isEmpty());
    }
}