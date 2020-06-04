import dataObjects.Card;
import dataObjects.Move;
import dataObjects.SolitaireState;
import javafx.scene.image.Image;

import java.util.List;

/**
 * @author Erlend
 * See interface for comments.
 */

public class Controller implements I_Controller {
    private final I_Logic logic = new Logic();
    private final StateManager stateManager = new StateManager();
    private I_ComputerVisionController CV_Controller; // Instantiate here

    @Override
    public void getFirstMove(Image img, NextMoveCallBack callBack) {
        try {
            Card[] cardData = CV_Controller.getSolitaireCards(img);
            SolitaireState state = stateManager.initiate(cardData); // Make new history, logfile and state
            List<Move> moves = logic.getMoves(state);
            stateManager.saveState(state, moves); // Saves the suggested moves
            callBack.OnSuccess(stateManager.getMoves(), stateManager.getHistory());
        } catch (Exception e) {
            callBack.OnError(e);
        }
    }

    @Override
    public void performMove(Move move, CompletionCallBack callBack) {
        try {
            if (move == null) {
                callBack.OnFailure("Please supply a move", stateManager.getHistory());
            } else {
                SolitaireState state = stateManager.updateState(move);
                List<Move> moves = logic.getMoves(state);
                stateManager.saveState(state, moves);
                callBack.OnSuccess("OK: Move registered, and suggested moves generated.", stateManager.getHistory());
            }
        } catch (Exception e) {
            callBack.OnError(e);
        }
    }

    @Override
    public void getNextMove(Image img, NextMoveCallBack callBack) {
        try {
            Card[] cardData = CV_Controller.getSolitaireCards(img);
            stateManager.checkState(cardData);
            callBack.OnSuccess(stateManager.getMoves(), stateManager.getHistory());
        } catch (Exception e) {
            callBack.OnError(e);
        }
    }

    @Override
    public void undo(CompletionCallBack callBack) {
        try {
            stateManager.undo();
            callBack.OnSuccess("UNDO registered, try to move your cards back and run 'getNextMove' to control.", stateManager.getHistory());
        } catch (Exception e) {
            callBack.OnError(e);
        }
    }
}
