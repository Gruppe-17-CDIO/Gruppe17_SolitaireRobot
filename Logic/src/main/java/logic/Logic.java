package logic;

import dataObjects.Card;
import dataObjects.Move;
import dataObjects.SolitaireState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static dataObjects.Card.Status.FACEDOWN;
import static dataObjects.GlobalEnums.GameProgress.LOST;
import static dataObjects.Move.DestinationType.*;
import static dataObjects.Move.MoveBenefit.*;
import static dataObjects.Move.MoveType.*;

/**
 * @author Anders Frandsen
 */

public class Logic implements I_Logic {
    //private List<Move> pastMoves = new ArrayList<>();
    private List<Move> moves;
    private SolitaireState state;
    private Card card;
    private int drawsInRow = 0;

    // Priority
    // 1: Turn discovered cards face up
    // 2: Move to foundation (pile and drawn)
    // 3: Move from pile to pile
    // 4: Move from drawn to pile
    // 5: Draw new card

    @Override
    public List<Move> getMoves(SolitaireState state) {
        this.state = state;
        moves = new ArrayList<>();

        /*
         * Outer loop iterates through pile 1 to 7
         * Inner loop iterates each pile-stack from top to bottom
         */
        List<List<Card>> piles = state.getPiles();
        for (int i = 0; i < 7; i++) {
            List<Card> pile = piles.get(i);

            // Make sure pile is list and not null
            if (piles.get(i) == null) {
                piles.set(i, new ArrayList<>());
            }

            // Turn top card face up
            if (pile.size() > 0 && pile.get(pile.size() - 1).getStatus() == FACEDOWN) {
                moves.add(new Move(FACE_UP_IN_PILE, pile.get(pile.size() - 1), new int[]{i, pile.size() - 1}, SELF, 0, REVEAL_CARD));
                break; // No other move possible to this pile
            }

            // Loop through the cards in this pile
            for (int j = pile.size() - 1; j >= 0; j--) {
                card = state.getPiles().get(i).get(j);

                if (card.getStatus() == FACEDOWN) {
                    break; // No moves possible for cards under this one.
                }

                // Check out possible move to foundation (Must be last card in list)
                if (j == state.getPiles().get(i).size() - 1) {
                    foundationMove(i, j);
                }

                // Check out possible moves to other rows/piles
                pileMoves(i, j);
            }
        }

        // Drawn card move to foundation
        List<Card> drawnCards = state.getDrawnCards();
        if (drawnCards.size() > 0) {
            Card drawnCard = drawnCards.get(drawnCards.size() - 1);

            if (drawnCard.getStatus() == FACEDOWN) {
                moves.add(new Move(Move.MoveType.USE_DRAWN, card, null, SELF, 0, REVEAL_CARD));
            } else {
                for (int f = 0; f < state.getFoundations().size(); f++) {
                    // If foundation pile is empty and card rank is 1 add move
                    // Or if foundation pile is same suit and rank fits add move
                    if ((
                            state.getFoundations().get(f) == null && drawnCard.getRank() == 1) ||
                            state.getFoundations().get(f) != null &&
                                    state.getFoundations().get(f).getSuit() == drawnCard.getSuit() &&
                                    drawnCard.getRank() - 1 == state.getFoundations().get(f).getRank()
                    ) {
                        moves.add(new Move(Move.MoveType.USE_DRAWN, drawnCard, null, FOUNDATION, f, NO_BENEFIT));
                        break;
                    }
                }
            }
        }

        // Drawn card move to a pile
        for (int i = 0; i < 7; i++) {
            List<Card> pile = piles.get(i);
            if (drawnCards.size() > 0) {
                Card drawnCard = drawnCards.get(drawnCards.size() - 1);
                // If empty pile, move king there
                // or if top card rank one lower and opposite color move card there
                if (
                        (drawnCard.getRank() == 13 && pile.size() < 1) ||
                                (
                                        pile.size() > 0 &&
                                                drawnCard.getRank() == pile.get(pile.size() - 1).getRank() - 1
                                                && drawnCard.getColor() != pile.get(pile.size() - 1).getColor()
                                )) {
                    moves.add(
                            // From position not needed.
                            new Move(Move.MoveType.USE_DRAWN, drawnCard, null, PILE, i, NO_BENEFIT)
                    );
                }
            }
        }

        // "Draw new card from stock" is added at the end of each list if one of the conditions are true:
        // 1 There are more cards in the stock
        // 2 here are cards in drawncards AND you are allowed to turn the pile again
        if (state.getStock() > 0) {
            moves.add(new Move(DRAW, null, null, null, 0, NO_BENEFIT));
        } else if (state.getDrawnCards().size() > 0 && state.getStockTurned() < 3) {
            moves.add(new Move(DRAW, null, null, null, 0, TURN_STOCK));
        }

        // Same move can only be suggested five times in total (or game never ends)
        //moves = removeRepeatMoves(moves);

        // End game if all cards viewed since last meaningful move
        if (drawsInRow > state.getStock() + state.getDrawnCards().size() + 1) {
            state.setGameProgress(LOST);
            moves = new ArrayList<>();
        }
        Collections.sort(moves, new MoveComparator());

        // Add to draws in a row
        if (moves.size() > 0 && moves.get(0).getMoveType() == DRAW) {
            drawsInRow++;
        } else {
            drawsInRow = 0;
        }
        return moves;
    }

    private void foundationMove(int pileNumber, int cardNumber) {
        for (int f = 0; f < state.getFoundations().size(); f++) {
            // If foundation pile is empty and card rank is 1 add move
            // Else if foundation pile is same suit and rank fits add move
            if (state.getFoundations().get(f) == null) {
                if (card.getRank() == 1) {
                    moves.add(0, new Move(MOVE_FROM_PILE, card, new int[]{pileNumber, cardNumber}, FOUNDATION, f, NO_BENEFIT));
                    break;
                }
            } else if (state.getFoundations().get(f).getSuit() == card.getSuit()) {
                if (card.getRank() - 1 == state.getFoundations().get(f).getRank()) {
                    moves.add(0, new Move(MOVE_FROM_PILE, card, new int[]{pileNumber, cardNumber}, FOUNDATION, f, NO_BENEFIT));
                    break;
                }
            }
        }
    }

    private void pileMoves(int pileNumber, int cardNumber) {
        for (int p = 0; p < 7; p++) {
            List<Card> fromPile = state.getPiles().get(pileNumber);

            // Continue loop if pile is itself
            if (p == pileNumber) {
                continue;
            }

            // Check if possible "destination" on top of this "destPile"
            List<Card> destPile = state.getPiles().get(p);

            // Add kings to empty piles
            if (destPile.size() < 1) {
                if (card.getRank() == 13 && cardNumber != 0) {
                    moves.add(
                            new Move(MOVE_FROM_PILE, card, new int[]{pileNumber, cardNumber}, PILE, p, PLACE_KING)
                    );
                }
                continue; // Ignore rest of iteration if empty pile
            }

            Card destCard = destPile.get(destPile.size() - 1);

            // Ignore unseen top cards as destinations (in this method)
            if (destCard.getStatus() == FACEDOWN)
                continue;

            if (card != null && card.getStatus() != FACEDOWN) {
                if (card.getRank() == destCard.getRank() - 1 && card.getColor() != destCard.getColor()) {
                    if (cardNumber == 0) {
                        moves.add(
                                new Move(MOVE_FROM_PILE, card, new int[]{pileNumber, cardNumber}, PILE, p, CLEAN_PILE)
                        );
                    } else if (fromPile.get(cardNumber - 1).getStatus() == Card.Status.FACEDOWN) {
                        moves.add(
                                new Move(MOVE_FROM_PILE, card, new int[]{pileNumber, cardNumber}, PILE, p, REVEAL_CARD));
                    } else {
                        // These might be wasted moves
                        //moves.add(
                        //        new Move(MOVE, card, new int[]{pileNumber, cardNumber}, PILE, p, NO_BENEFIT)
                        //);
                    }
                }
            }
        }
    }

    /*
    // Don't allow same move to be executed six times in end game
    private List<Move> removeRepeatMoves(List<Move> moves) {

        if (moves.size() < 1) {
            return moves;
        }
        if (pastMoves.size() > 60) {
            pastMoves = pastMoves.subList(pastMoves.size() - 50, pastMoves.size() - 1);
        }

        List<Move> filteredMoves = new ArrayList<>();
        for (int i = 0; i < moves.size(); i++) {
            int repeat = 0;
            Move move = moves.get(i);
            if (move.getMoveType() != MOVE_FROM_PILE) {
                filteredMoves.add(move);
            } else {
                for (int j = 0; j < pastMoves.size(); j++) {
                    if (move.toString().equals(pastMoves.get(j).toString())) {
                        repeat++;
                        if (repeat > 3) {
                            break;
                        }
                    }
                }
                if (repeat < 4) {
                    filteredMoves.add(move);
                }
            }
        }
        // Add best move to pastMoves (Remove after test)
        if (filteredMoves.size() > 0) {
            pastMoves.add(filteredMoves.get(0));
        }
        return filteredMoves;
    }
     */
}