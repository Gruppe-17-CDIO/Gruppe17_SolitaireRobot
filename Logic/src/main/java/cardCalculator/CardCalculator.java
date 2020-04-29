package cardCalculator;

import dataObjects.Card;
import dataObjects.Move;
import dataObjects.SolitaireState;
import stateBuilding.StateGenerator;

public class CardCalculator {

    public SolitaireState initiateState(Card[] imgstate) throws Exception {
        SolitaireState state = StateGenerator.getState(999);
        // TODO: Add cards based on visible cards from imgstate
        return state;
    }

    public SolitaireState updateState(Card[] imgstate, SolitaireState prevState, Move move) {
        SolitaireState state = prevState;
        // TODO: Add/move cards based on visible cards from imgstate
        //
        return state;
    }
}
