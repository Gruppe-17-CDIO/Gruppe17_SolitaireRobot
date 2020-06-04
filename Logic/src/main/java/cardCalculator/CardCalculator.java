package cardCalculator;

import dataObjects.Card;
import dataObjects.Move;
import dataObjects.SolitaireState;
import dataObjects.TopCards;
import stateBuilding.StateGenerator;

import java.util.ArrayList;
import java.util.List;

public class CardCalculator {
    // This class is responsible for
    // 1. Making a state based on initial TopCard object from CV.
    // 2. Updating state based on move and previous state.
    // 3. Checking state against the new TopCard object from CV.

    /**
     * @param topCards, the cards as seen by computer vision on initial setup
     * @return state, a Full SoliaireState Object
     * @throws Exception if CardData cannot be used
     */
    public SolitaireState initiateState(TopCards topCards) throws Exception {
        // Check input
        if (topCards == null) {
            throw new Exception("initiateState(): Card data was null.");
        }
        if (topCards.getDrawnCard() == null) {
            throw new Exception("initiateState(): Missing a drawn card in the setup. Draw exactly one card before calling initiate.");
        }
        if (topCards.getFoundations() != null) {
            throw new Exception("initiateState(): Can't start out with foundations already present.");
        }
        if (topCards.getPiles().size() != 7) {
            throw new Exception("initiateState(): Excpected seven top cards, one for each pile (column).");
        }


        SolitaireState state = StateGenerator.getState(999); // State template

        ArrayList<Card> drawnCards = new ArrayList<>();
        drawnCards.add(topCards.getDrawnCard());
        state.setDrawnCards(drawnCards);

        ArrayList<Card> foundations = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            foundations.add(null); // No cards here at first
        }
        state.setFoundations(foundations);

        List<List<Card>> piles = state.getPiles();
        for (int i = 0; i < 7; i++) {
            piles.get(i).add(topCards.getPiles().get(i)); // Add the 7 visible cards
        }
        state.setPiles(piles);
        // Do not save state here
        return state;
    }

    /**
     * Updates the state based on previous state and previous move.
     *
     * @param prevState
     * @param move
     * @return state
     */
    public SolitaireState updateState(SolitaireState prevState, Move move) {
        SolitaireState state = prevState;
        // TODO: Fucking implement!
        // State = state + move
        // CONSIDER DEEP COPY WHEN UPDATNG STATE

        /*
        //Moves are from drawn or piles only.
        if (mvoetype == DRAW) update drawn, update stock
        if (movetype == TURN) // TODO: REMOVE THIS DATA FROM MOVE
        if (movetype == MOVE) maybe focus on this first:
                //if pile which pile? Remember drawn cards is apile too.
                // which card
                // move card at position + all card up to this pile.length - 1 to destination.
       */


        // Add move to state as previous move
        // Do not save state!
        return state;
    }

    /**
     * @param topCards
     * @param state
     * @throws Exception
     */
    public void checkState(TopCards topCards, SolitaireState state) throws Exception {

        // TODO: Update to the new data object
        List<Card> foundations = state.getFoundations();
        List<List<Card>> piles = state.getPiles();
        Card[] stateData = new Card[12];

        stateData[0] = state.getDrawnCards().get(0);
        for (int i = 0; i < 5; i++) {
            stateData[i + 1] = foundations.get(i); // 1 to 5 = foundations
        }
        for (int i = 0; i < 7; i++) {
            List<Card> pile = piles.get(i);
            stateData[i + 5] = pile.get(pile.size() - 1); // top card from all piles
        }

        for (int i = 0; i < 12; i++) {
            if (!topCards[i].toString().equals(stateData[i].toString())) { // tostring should match
                throw new Exception("Data from Computer Vision does not match calculated array:\n" +
                        "Computervision:\n" + topCards + "\n" +
                        "State as array:\n" + stateData + "\n" +
                        "Retry with 'getNextMove', or 'undo', or restart with 'getFirstMove'!");
            }
        }
    }
}
