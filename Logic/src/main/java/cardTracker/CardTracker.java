package cardTracker;

import CV_simulation.StateGenerator;
import dataObjects.Card;
import dataObjects.Move;
import dataObjects.SolitaireState;

public class CardTracker {

    public SolitaireState generateState(Card[] imgstate, SolitaireState prevState, Move move) {
        SolitaireState state = prevState;
        // TODO: Add/move cards based on visible cards from imgstate
        return state;
    }

    public SolitaireState generateState(Card[] imgstate) throws Exception {
        SolitaireState state = StateGenerator.getState(999);
        // TODO: Add cards based on visible cards from imgstate
        return state;
    }

}
