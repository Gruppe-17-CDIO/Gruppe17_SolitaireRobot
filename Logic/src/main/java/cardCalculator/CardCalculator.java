package cardCalculator;

import dataObjects.Card;
import dataObjects.Move;
import dataObjects.SolitaireState;
import stateBuilding.StateGenerator;

import java.util.ArrayList;
import java.util.List;

public class CardCalculator {

    public SolitaireState initiateState(Card[] cardData) throws Exception {
        // Check input
        if (cardData == null) {
            throw new Exception("Card data was null.");
        }
        if (cardData.length != 12) {
            throw new Exception("Card data should have 12 slots. Was " + cardData.length);
        }
        for (int i = 0; i < 12; i++) {
            if (i == 0 || i > 4) {
                if (cardData[i] == null) {
                    throw new Exception("Card Data " + i + " was null. Drawn card (0) and piles (5-11) should be " +
                            "valid cards when a new game is set up");
                }
            }
        }
        SolitaireState state = StateGenerator.getState(999); // State template

        state.setDrawnCard(cardData[0]);

        ArrayList<Card> foundations = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            foundations.add(null); // No cards here at first
        }
        state.setFoundations(foundations);

        List<List<Card>> piles = state.getPiles();
        for (int i = 0; i < 7; i++) {
            piles.get(i).add(cardData[i + 5]); // Add the 7 visible cards
        }
        state.setPiles(piles);
        // Do not save state here
        return state;
    }

    public SolitaireState updateState(SolitaireState prevState, Move move) {
        SolitaireState state = prevState;
        // State = state + move

        /*
        //Moves are from drawn or piles only.
        if (mvoetype == DRAW) update drawn, update stock
        if (movetype == TURN) // TODO: REMOVE THIS DATA FROM MOVE
        if (movetype == MOVE) maybe focus on this first:
                //if pile which pile?
                // which card
                // move card at position + all card up to this pile.length - 1 to destination.
       */


        // Add move to state as previous move
        // Do not save state!
        return state;
    }

    public void checkState(Card[] cardData, SolitaireState state) throws Exception {
        // CONSIDER DEEP COPY WHEN UPDATNG STATE
        List<Card> foundations = state.getFoundations();
        List<List<Card>> piles = state.getPiles();
        Card[] stateData = new Card[12];

        stateData[0] = state.getDrawnCard();
        for (int i = 0; i < 5; i++) {
            stateData[i + 1] = foundations.get(i); // 1 to 5 = foundations
        }
        for (int i = 0; i < 7; i++) {
            List<Card> pile = piles.get(i);
            stateData[i + 5] = pile.get(pile.size() - 1); // top card from all piles
        }

        for (int i = 0; i < 12; i++) {
            if (!cardData[i].toString().equals(stateData[i].toString())) { // tostring should match
                throw new Exception("Data from Computer Vision does not match calculated array:\n" +
                        "Computervision:\n" + cardData + "\n" +
                        "State as array:\n" + stateData + "\n" +
                        "Retry with 'getNextMove', or 'undo', or restart with 'getFirstMove'!");
            }
        }
    }
}
