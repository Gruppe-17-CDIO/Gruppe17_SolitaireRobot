package cardCalculator;

import dataObjects.Card;
import dataObjects.Move;
import dataObjects.SolitaireState;
import stateBuilding.StateGenerator;

public class CardCalculator {

    public SolitaireState initiateState(Card[] imgstate) throws Exception {
        SolitaireState state = StateGenerator.getState(999);
        // Add cards based on visible cards from imgstate
        // Do not save state
        return state;
    }

    public SolitaireState updateState(SolitaireState prevState, Move move) {
        SolitaireState state = prevState;
        // State = state + move
        // Add move to state as previous move
        // Do not save state!
        return state;
    }

    public void checkState(Card[] cardData, SolitaireState state) throws Exception {

        if (false) {
            throw new Exception("Data from Computer Vision does not match calculated array:\n" +
                    "Computervision:\n" +
                    "State as array:\n" +
                    "Try an undo, or if this is a new game, restart with 'getFirstMove'!");
        }
    }
}
