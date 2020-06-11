import dataObjects.Card;
import dataObjects.Move;
import dataObjects.SolitaireState;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anders Frandsen
 */

// TODO: Ikke tillad træk fra bunken, hvis 0 kort i "stock + drawncards" tilbage
//  ELLER state.getStockTurned() > 2.

public class Logic implements I_Logic {
    private List<Move> moves;
    private SolitaireState state;
    private Card card;
    private final List<Move> pastMoves = new ArrayList<>();

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
            if (pile.size() > 0 && pile.get(pile.size() - 1).getStatus() == Card.Status.FACEDOWN) {
                moves.add(0, new Move(Move.MoveType.FACEUP, new int[]{i, pile.size() - 1}, Move.DestinationType.SELF, 0));
                break; // No other move possible to this pile
            }

            // Loop through the cards in this pile
            for (int j = pile.size() - 1; j >= 0; j--) {
                card = state.getPiles().get(i).get(j);

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

            if (drawnCard.getStatus() == Card.Status.FACEDOWN) {
                moves.add(0, new Move(Move.MoveType.USEDRAWN, null, Move.DestinationType.SELF, 0));
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
                        moves.add(0, new Move(Move.MoveType.USEDRAWN, null, Move.DestinationType.FOUNDATION, f));
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
                            new Move(Move.MoveType.USEDRAWN, null, Move.DestinationType.PILE, i)
                    );
                }
            }
        }

        // "Draw new card from stock" is added at the end of each list if one of the conditions are true:
        // 1 There are more cards in the stock
        // 2 here are cards in drawncards AND you are allowed to turn the pile again
        if (state.getStock() > 0 ||
                state.getDrawnCards().size() > 0 && state.getStockTurned() < 3) {
            // Values are null: there is only one place to put the card: drawn cards
            moves.add(new Move(Move.MoveType.DRAW, null, null, 0));
        }

        // Same move can only be made five times in total (or game never ends)
        moves = removeRepeatMoves(moves);
        if (moves.size() > 0) {
            pastMoves.add(moves.get(0));
        }

        return moves;
    }

    private void foundationMove(int pileNumber, int cardNumber) {
        for (int f = 0; f < state.getFoundations().size(); f++) {
            // If foundation pile is empty and card rank is 1 add move
            // Else if foundation pile is same suit and rank fits add move
            if (state.getFoundations().get(f) == null) {
                if (card.getRank() == 1) {
                    moves.add(0, new Move(Move.MoveType.MOVE, new int[]{pileNumber, cardNumber}, Move.DestinationType.FOUNDATION, f));
                    break;
                }
            } else if (state.getFoundations().get(f).getSuit() == card.getSuit()) {
                if (card.getRank() - 1 == state.getFoundations().get(f).getRank()) {
                    moves.add(0, new Move(Move.MoveType.MOVE, new int[]{pileNumber, cardNumber}, Move.DestinationType.FOUNDATION, f));
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
                    moves.add(new Move(Move.MoveType.MOVE, new int[]{pileNumber, cardNumber}, Move.DestinationType.PILE, p));
                }
                continue; // Ignore rest of iteration if empty pile
            }

            Card destCard = destPile.get(destPile.size() - 1);

            // Ignore unseen top cards as destinations (in this method)
            if (destCard.getStatus() == Card.Status.FACEDOWN)
                continue;

            if (card != null && card.getStatus() != Card.Status.FACEDOWN) {
                // Priority: (cardNumber == 0 || fromPile.get(cardNumber - 1).getStatus() == Card.Status.FACEDOWN)
                // Add card to series if opposite color and rank one lower
                if (card.getRank() == destCard.getRank() - 1 && card.getColor() != destCard.getColor()) {
                    moves.add(
                            new Move(Move.MoveType.MOVE, new int[]{pileNumber, cardNumber}, Move.DestinationType.PILE, p)
                    );
                }
            }
        }
    }

    private List<Move> removeRepeatMoves(List<Move> moves) {
        // Don't allow same move to be executed six times
        if (moves.size() < 1) {
            return moves;
        }
        List<Move> filteredMoves = new ArrayList<>();
        for (int i = 0; i < moves.size(); i++) {
            int repeat = 0;
            Move move = moves.get(i);
            // Compare 7 last past moves
            for (int j = 0; j < pastMoves.size(); j++) {
                if (move.toString().equals(pastMoves.get(j).toString())) {
                    repeat++;
                    if (repeat > 5) {
                        break;
                    }
                }
            }
            if (repeat < 6 || move.getMoveType() != Move.MoveType.MOVE) {

                filteredMoves.add(move);
            }
        }
        return filteredMoves;
    }
}