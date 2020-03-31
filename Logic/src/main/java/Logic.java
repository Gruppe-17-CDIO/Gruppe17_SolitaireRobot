import dataObjects.Card;
import dataObjects.Move;
import dataObjects.SolitaireState;
import java.util.ArrayList;
import java.util.List;

public class Logic implements I_Logic {
    @Override
    public List<Move> getMoves(SolitaireState state) {
        List<Move> moves = new ArrayList<>();

        /*
         * Outer loop iterates through pile 1 to 7
         * Inner loop iterates each pile-stack from top to bottom
         */
        for (int i = 0; i < 7; i++) {
            for (int j = state.getPiles().get(i).size() - 1; j >= 0; j--) {
                Card card = state.getPiles().get(i).get(j);
                if (card == null)
                    continue;

                if (card.getStatus() == Card.Status.FACEDOWN) {
                    if (j == state.getPiles().get(i).size() - 1)
                        moves.add(0, new Move(Move.MoveType.FACEUP, new int[]{i,j}, Move.DestinationType.SELF, 0));
                    break;
                }

                // Check out possible move to foundation stack
                for (int f = 0; f < state.getFoundations().size(); f++) {
                    // If foundation pile is empty and card rank is 1 add move
                    // Else if foundation pile is same suit and rank fits add move
                    if (state.getFoundations().get(f) == null) {
                        if (card.getRank() == 1) {
                            moves.add(0, new Move(Move.MoveType.MOVE, new int[]{i,j}, Move.DestinationType.FOUNDATION, f));
                            break;
                        }
                    } else if (state.getFoundations().get(f).getSuit() == card.getSuit()) {
                        if (card.getRank() - 1 == state.getFoundations().get(f).getRank()) {
                            moves.add(0, new Move(Move.MoveType.MOVE, new int[]{i,j}, Move.DestinationType.FOUNDATION, f));
                            break;
                        }
                    }
                }

                // Check out possible moves to other rows/piles
                for (int p = 0; p < 7; p++) {
                    // Continue loop if (checked) pile == (outer loop) pile or pile is empty
                    if (p == i || state.getPiles().get(p).size() == 0)
                        continue;

                    Card destCard = state.getPiles().get(p).get(state.getPiles().get(p).size() - 1);

                    if (destCard == null) {
                        if (card.getRank() == 13 && j != 0) {
                            moves.add(new Move(Move.MoveType.MOVE, new int[]{i,j}, Move.DestinationType.PILE, p));
                        }
                        continue;
                    }

                    if (destCard.getStatus() == Card.Status.FACEDOWN)
                        continue;

                    if (card.getRank() == destCard.getRank() - 1 && card.getColor() != destCard.getColor()) {
                        moves.add(
                                new Move(Move.MoveType.MOVE, new int[]{i,j}, Move.DestinationType.PILE, p)
                        );
                    }
                }

                // Drawn cards
                //TODO
            }
        }
        return moves;
    }
}
