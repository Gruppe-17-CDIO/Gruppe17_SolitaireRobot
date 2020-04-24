import dataObjects.Move;
import dataObjects.SolitaireState;

public interface NextMoveCallback {
        void OnSuccess(Move move);

        void OnFailure(String message, Move move, SolitaireState cards);

        void OnError(Exception e);
}
