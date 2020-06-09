package cardCalculator;

import com.google.gson.Gson;
import dataObjects.Card;
import dataObjects.Move;
import dataObjects.SolitaireState;
import dataObjects.TopCards;
import stateBuilding.StateGenerator;
import stateBuilding.TopCardsSimulator;

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
            throw new Exception("initiateState(): Missing a drawn card in the setup. " +
                    "Draw exactly one card before calling initiate.");
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

        return state;
    }

    /**
     * Makes new state based on previous state and move.
     *
     * @param prevState
     * @param move
     * @param topCards
     * @return state
     */
    public SolitaireState updateState(SolitaireState prevState, Move move, TopCards topCards, TopCardsSimulator topCardsSimulator, boolean test) throws Exception {
        // Deep copy
        Gson gson = new Gson();
        SolitaireState state = gson.fromJson(gson.toJson(prevState), SolitaireState.class);

        // Clear suggestedmoves
        state.setSuggestedMoves(new ArrayList<>());

        List<Card> drawnCards = state.getDrawnCards();
        List<Card> foundations = state.getFoundations();
        List<List<Card>> piles = state.getPiles();

        // If move is draw, add the newly turned card from CV
        if (move.getMoveType() == Move.MoveType.DRAW) {
            int stock = state.getStock();
            state.setStock(stock - 1);
            if (test) {
                drawnCards.add(topCardsSimulator.getCard());
            } else {
                drawnCards.add(topCards.getDrawnCard());
            }
            state.setDrawnCards(drawnCards);
        }

        // If a face down card is uncovered in a pile, replace with card from CV
        // (Implicitly this is the FACEUP move type.)
        for (int i = 0; i < 7; i++) {
            List<Card> pile = piles.get(i);
            if (pile.size() > 0 && pile.get(pile.size() - 1).getStatus() == Card.Status.FACEDOWN) {
                if (test) {
                    piles.get(i).set(piles.get(i).size() - 1, topCardsSimulator.getCard());
                } else {
                    piles.get(i).set(piles.get(i).size() - 1, topCards.getPiles()[i]);
                }
                state.setPiles(piles);
            }
        }

        if (move.getMoveType() == Move.MoveType.USEDRAWN) {
            if (drawnCards.size() < 1) {
                throw new Exception("Can't perform 'use drawn card'. No cards in the DrawnCards pile.");
            }
            Card card = drawnCards.get(drawnCards.size() - 1);
            drawnCards.remove(drawnCards.size() - 1);
            state.setDrawnCards(drawnCards);

            if (move.getDestinationType() == Move.DestinationType.FOUNDATION) {
                foundations.set(move.getDestPosition(), card);
                state.setFoundations(foundations);
            } else if (move.getDestinationType() == Move.DestinationType.PILE) {
                List<Card> cards = new ArrayList<>();
                cards.add(card);
                piles.get(move.getDestPosition()).addAll(cards);
                state.setPiles(piles);
            }
        }

        if (move.getMoveType() == Move.MoveType.MOVE) {
            int pileIndex = move.getPosition()[0];
            int cardIndex = move.getPosition()[1];

            // Pick up the cards and all cards on top of it.
            List<Card> cards = piles.get(pileIndex).subList(cardIndex, piles.get(pileIndex).size());
            piles.set(pileIndex, piles.get(pileIndex).subList(0, cardIndex));
            state.setPiles(piles);

            if (move.getDestinationType() == Move.DestinationType.FOUNDATION) {
                if (cards.size() != 1) {
                    throw new Exception("Move exactly one card to foundation at a time. Was "
                            + cards.size() + ".");
                }
                Card card = cards.get(0);
                foundations.set(move.getDestPosition(), card);
                state.setFoundations(foundations);
            } else if (move.getDestinationType() == Move.DestinationType.PILE) {
                piles.get(move.getDestPosition()).addAll(cards);
                state.setPiles(piles);
            }
        }
        return state;
    }


    /**
     * Method to check the integrity of state against real cards. New cards not previously seen are copied from
     * topCards to state.
     *
     * @param topCards
     * @param state
     * @throws Exception
     */
    public SolitaireState checkState(TopCards topCards, SolitaireState state) throws Exception {
        // TODO: This is not tested!

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

        return state;
    }
}