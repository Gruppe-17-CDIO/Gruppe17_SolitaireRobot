import CV_simulation.StateGenerator;
import dataObjects.Card;
import dataObjects.Move;
import dataObjects.SolitaireState;

import java.util.List;

public class StateHandler {
    private List<SolitaireState> history;
    private SolitaireState state;

    public SolitaireState updateState(List<Card> cardData, Move move) throws Exception {
        if (cardData == null) {
            throw new Exception("Card data was null. This input is needed.");
        } else if (move == null || state == null) {
            // initial setup, a blank SolitaireState is created based on CardData
            state = StateGenerator.getState(0);
        } else {
            // Update state based on move and log it.
        }
        return state;
    }

    public SolitaireState getState() throws Exception {
        if (state == null) {
            throw new Exception("State was null. Too soon to call this method.");
        }
        return state;
    }
}
