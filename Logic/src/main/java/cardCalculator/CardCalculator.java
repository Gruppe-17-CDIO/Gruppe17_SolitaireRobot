package cardCalculator;

import dataObjects.Card;
import dataObjects.Move;
import dataObjects.SolitaireState;
import dataObjects.TopCards;
import stateBuilding.StateGenerator;

import java.util.ArrayList;
import java.util.List;

public class CardCalculator {
    /**
     * This class performs these tasks:
     * 1. Making a state based on initial TopCard object from CV.
     * 2. Updating state based on move and previous state.
     * 3. Checking state against the new TopCard object from CV.
     *
     * @param topCards, the cards as seen by computer vision on initial setup
     * @return state, a Full SoliaireState Object
     * @throws Exception if CardData cannot be used
     * @author erlend
     * /
     * <p>
     * /**
     */
    public SolitaireState initiateState(TopCards topCards) throws Exception {
        // Check input
        if (topCards == null) {
            throw new Exception("initiateState(): topCards was null.");
        }
        if (topCards.getDrawnCard() == null) {
            throw new Exception("initiateState(): Missing a drawn card in the setup. Draw exactly one card before calling initiate.");
        }
        for (int i = 0; i < 4; i++) {
            if (topCards.getFoundations()[i] != null) {
                throw new Exception("initiateState(): Can't start game with foundations already present.");
            }
        }
        for (int i = 0; i < 7; i++) {
            if (topCards.getPiles()[i] == null) {
                throw new Exception("initiateState(): Missing card from pile: " + i + ". All piles must have one card at start of new game.");
            }
        }


        SolitaireState state = new StateGenerator().getState(999); // State template

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
            piles.get(i).add(topCards.getPiles()[i]); // Add the 7 visible cards
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
        // TODO: Implement!
        // State = state + move
        // CONSIDER DEEP COPY WHEN UPDATNG STATE

        /*
        //Moves are from drawn or piles.
        if (mvoetype == DRAW) update drawn, update stock
        if (movetype == MOVE) maybe focus on this first:
                //if pile which pile? Remember drawn cards is a pile too.
                // which card
                // move card at position + all card up to this pile.length - 1 to destination.
       */

        // Add move to state as previous move
        // Do not save state!
        return state;
    }

    /**
     * Method to check the integrity of state against real cards
     *
     * @param topCards
     * @param state
     * @throws Exception
     */
    public void checkState(TopCards topCards, SolitaireState state) throws Exception {

        List<Card> drawnCards = state.getDrawnCards();
        List<Card> foundations = state.getFoundations();
        List<List<Card>> piles = state.getPiles();

        // Check drawn card pile
        Card drawnCard = null;
        if (drawnCards.size() > 0) {
            drawnCard = drawnCards.get(drawnCards.size() - 1);
        }
        if (drawnCard == null) {
            if (topCards.getDrawnCard() != null) {
                throw new Exception("checkState: State drawn card was null, but image drawn card was NOT null.");
            }
        } else if (topCards.getDrawnCard() == null) {
            throw new Exception("checkState: Image drawn card was null, state drawn card NOT null.");
        } else {
            if (!(topCards.getDrawnCard().toString().equals(drawnCard.toString()))) {
                throw new Exception("checkState: The drawn cards don't match." +
                        "\n\tState: " + drawnCard.toString() +
                        "\n\tImage: " + topCards.getDrawnCard().toString());
            }
        }

        // Check the foundations
        for (int i = 0; i < 4; i++) {
            if (foundations.get(i) == null) {
                if (topCards.getFoundations()[i] != null) {
                    throw new Exception("checkState: State's foundation " + i + " was null, but corresponding card from image was NOT null.");
                }
            } else if (topCards.getFoundations()[i] == null) {
                throw new Exception("checkState: Image foundations " + i + " was null, corresponding card in state was NOT null.");
            } else {
                if (!(topCards.getFoundations()[i].toString().equals(foundations.get(i).toString()))) {
                    throw new Exception("checkState: The foundation card " + i + " doesn't match." +
                            "\n\tState: " + foundations.get(i).toString() +
                            "\n\tImage: " + topCards.getFoundations()[i].toString());
                }
            }
        }

        // Check the piles
        for (int i = 0; i < 7; i++) {
            if (piles.get(i) == null) {
                if (topCards.getPiles()[i] != null) {
                    throw new Exception("checkState: State's pile " + i + " was null, but corresponding card from image was NOT null.");
                }
            } else if (topCards.getPiles()[i] == null) {
                throw new Exception("checkState: Image pile " + i + " was null, corresponding card in state was NOT null.");
            } else {
                if (!(topCards.getPiles()[i].toString().equals(piles.get(i).toString()))) {
                    throw new Exception("checkState: The pile card " + i + " doesn't match." +
                            "\n\tState: " + piles.get(i).toString() +
                            "\n\tImage: " + topCards.getPiles()[i].toString());
                }
            }
        }
    }
}

