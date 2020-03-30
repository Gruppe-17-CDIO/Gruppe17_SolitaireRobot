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
         * Outer loops iterates from pile 1 to 7 and every card in each pile.
         */
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < state.getPiles().get(i).size(); j++) {
                Card card = state.getPiles().get(i).get(j);

                if (card.getStatus() == Card.Status.FACEDOWN) {
                    // turn up
                    continue;
                }

                // Inner loop: piles
                for (int p = 0; p < 7; p++) {
                    if (p == i)
                        continue;

                    Card destCard = state.getPiles().get(p).get(state.getPiles().get(p).size() - 1);
                    if (destCard.getStatus() == Card.Status.FACEDOWN)
                        continue;

                    int[] position = {i, j};

                    if (card.getRank() == destCard.getRank() - 1 && card.getColor() != destCard.getColor()) {
                        moves.add(
                                new Move(Move.MoveType.MOVE, position, Move.DestinationType.PILE, p)
                        );
                    }
                }

                // Draw card
                Card draw = state.getDrawnCards().get(0);
                int[] position = {};

                if (draw.getRank() == card.getRank() - 1 && draw.getColor() != card.getColor()) {
                    moves.add(
                            new Move(Move.MoveType.DRAW, position, Move.DestinationType.PILE, i)
                    );
                }
            }
        }

        if (moves.isEmpty())
            return null;
        else
            return moves;
    }
}
