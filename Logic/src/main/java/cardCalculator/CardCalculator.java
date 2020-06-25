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
            throw new CardReadException("initiateState(): topCards was null.");
        }
        if (topCards.getDrawnCard() == null) {
            throw new CardReadException("initiateState(): Missing a drawn card in the setup. " +
                    "Draw exactly one card from stock before calling initiate.");
        }
        for (int i = 0; i < 4; i++) {
            if (topCards.getFoundations()[i] != null) {
                throw new CardReadException("initiateState(): Can't start game with foundations already present.");
            }
        }
        for (int i = 0; i < 7; i++) {
            if (topCards.getPiles()[i] == null) {
                throw new CardReadException("initiateState(): Missing card from pile: " + (i + 1) + ". " +
                        "All piles must have one card at start of new game.");
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
     * @param prevMove
     * @param topCards
     * @return state
     */
    public SolitaireState updateState(SolitaireState prevState, Move prevMove, TopCards topCards,
                                      TopCardsSimulator topCardsSimulator, boolean test) throws Exception {
        // Deep copy
        Gson gson = new Gson();
        SolitaireState state = gson.fromJson(gson.toJson(prevState), SolitaireState.class);
        state.createTimeStamp(); // Setting a new timestamp id

        // Clear suggestedmoves
        state.setSuggestedMoves(new ArrayList<>());

        List<Card> drawnCards = state.getDrawnCards();
        List<Card> foundations = state.getFoundations();
        List<List<Card>> piles = state.getPiles();

        // If move is draw, add the newly turned card from CV
        if (prevMove.getMoveType() == Move.MoveType.DRAW) {
            int stock = state.getStock();

            if (stock < 0) {
                throw new Exception("Stock is below zero. Should not be possible.");

            } else if (stock == 0 && drawnCards.size() > 0) {
                // Drawn cards become new stock ("turn draw pile").
                stock = drawnCards.size();
                if (test) {
                    // Keep track of cards in deck to avoid duplicates, test only
                    topCardsSimulator.setUsedCards(drawnCards);
                }
                drawnCards = new ArrayList<>();
                int flipped = state.getStockTurned();
                state.setStockTurned(flipped + 1); // 3 means you can't draw anymore

            } else if (stock == 0) { // No cards left to draw
                throw new Exception("Draw was suggested, but there are no cards left in stock or drawn cards.");
            }

            if (test) {
                List<Card> cards = topCardsSimulator.getUsedCards();
                if (state.getStockTurned() > 0 && cards.size() > 0) {
                    drawnCards.add(cards.get(0));
                    topCardsSimulator.getUsedCards().remove(0);
                } else {
                    drawnCards.add(topCardsSimulator.getCard());
                }
            } else { // if not test
                if (topCards.getDrawnCard() == null) {
                    // Throw special exception for drawn cards
                    throw new CardReadException("Expected a new drawn card but found null.");
                } else {
                    drawnCards.add(topCards.getDrawnCard());
                }
            }

            // Unless exception is raised, decrement stock.
            state.setStock(stock - 1);

            state.setDrawnCards(drawnCards);
        }

        if (prevMove.getMoveType() == Move.MoveType.USE_DRAWN) {
            if (drawnCards.size() < 1) {
                throw new Exception("Can't perform 'use drawn card'. No cards in the DrawnCards pile.");
            }

            Card card = drawnCards.get(drawnCards.size() - 1);
            drawnCards.remove(drawnCards.size() - 1);
            state.setDrawnCards(drawnCards);

            if (prevMove.getDestinationType() == Move.DestinationType.FOUNDATION) {
                foundations.set(prevMove.getDestPosition(), card);
                state.setFoundations(foundations);
            } else if (prevMove.getDestinationType() == Move.DestinationType.PILE) {
                List<Card> cards = new ArrayList<>();
                cards.add(card);
                piles.get(prevMove.getDestPosition()).addAll(cards);
                state.setPiles(piles);
            }
        }

        if (prevMove.getMoveType() == Move.MoveType.MOVE_FROM_PILE) {
            int pileIndex = prevMove.getPosition()[0];
            int cardIndex = prevMove.getPosition()[1];

            // Pick up the cards and all cards on top of it.
            List<Card> cards = piles.get(pileIndex).subList(cardIndex, piles.get(pileIndex).size());
            piles.set(pileIndex, piles.get(pileIndex).subList(0, cardIndex));
            state.setPiles(piles);

            if (prevMove.getDestinationType() == Move.DestinationType.FOUNDATION) {
                if (cards.size() != 1) {
                    throw new Exception("Move exactly one card to foundation at a time. Was "
                            + cards.size() + ".");
                }
                Card card = cards.get(0);
                foundations.set(prevMove.getDestPosition(), card);
                state.setFoundations(foundations);
            } else if (prevMove.getDestinationType() == Move.DestinationType.PILE) {
                piles.get(prevMove.getDestPosition()).addAll(cards);
                state.setPiles(piles);
            }
        }

        // Last: If a face down card is uncovered on top of a pile, replace with card from CV
        // (Implicitly this is the FACEUP move type.)
        for (int i = 0; i < 7; i++) {
            List<Card> pile = piles.get(i);
            if (pile.size() > 0 && pile.get(pile.size() - 1).getStatus() == Card.Status.FACEDOWN) {
                if (test) {
                    piles.get(i).set(piles.get(i).size() - 1, topCardsSimulator.getCard());
                } else {
                    // Replace if there is a visible topcard (This assumes that face down cards are null in the array)
                    if (topCards.getPiles()[i] == null) {
                        // Exception here is removed: This means that
                        // if there is a card, and CV fails to read it, the program will still ask user to turn
                        // the card Face up!
                        //throw new Exception("Expected to see a new card on top of pile " + (i + 1) + ".");
                        piles.get(i).set(piles.get(i).size() - 1, new Card(Card.Status.FACEDOWN));
                    } else {
                        if (topCards.getPiles()[i] == null) {
                            throw new CardReadException("Could not read discovered card in pile " + (i + 1) + ".");
                        }
                        piles.get(i).set(piles.get(i).size() - 1, topCards.getPiles()[i]);
                    }
                }
                state.setPiles(piles);
            }
        }
        return state;
    }

    public boolean checkWin(SolitaireState state) {
        boolean won = true;
        for (int i = 0; i < 4; i++) {
            Card foundation = state.getFoundations().get(i);
            if (foundation == null || foundation.getRank() != 13) {
                won = false;
            }
        }
        return won;
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

        // Check the piles
        for (int i = 0; i < 7; i++) {
            if (piles.get(i) == null) {
                if (topCards.getPiles()[i] != null) {
                    throw new Exception("checkState: State's pile " + (i + 1) + " was null, " +
                            "but corresponding card from image was NOT null.");
                }
            } else if (topCards.getPiles()[i] == null) {
                throw new Exception("checkState: Image pile " + (i + 1) + " was null, " +
                        "corresponding card in state was NOT null.");
            } else {
                String pileCard = topCards.getPiles()[i].toString().replace("[", "").replace("]", "");
                throw new Exception("checkState: The pile card " + (i + 1) + " doesn't match." +
                        "\n\tState: " + piles.get(i).toString() +
                        "\n\tImage: " + topCards.getPiles()[i].toString());
            }
        }
        return state;
    }
}