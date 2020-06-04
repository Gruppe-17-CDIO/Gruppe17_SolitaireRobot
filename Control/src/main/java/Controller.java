import dataObjects.Move;
import dataObjects.SolitaireState;
import dataObjects.TopCards;
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
    private boolean testmode = false;

    @Override
    // Note that this method returns the first move suggestion and saves state
    public void startNewGame(Image img, NextMoveCallBack callBack) {
        //System.out.println("STARTING!");
        try {
            SolitaireState state;
            if (!testmode) {
                TopCards topCards = CV_Controller.getSolitaireCards(img);
                state = stateManager.initiate(topCards); // Make new history, logfile and state
            } else {
                state = stateManager.initiate(); // No args means just a test.
            }
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
                callBack.OnFailure("Please supply a move");
            } else {
                SolitaireState state = stateManager.updateState(move);
                List<Move> moves = logic.getMoves(state);
                stateManager.saveState(state, moves);
                callBack.OnSuccess("OK: Move registered, and suggested moves generated.");
            }
        } catch (Exception e) {
            callBack.OnError(e);
        }
    }

    @Override
    public void getNextMove(Image img, NextMoveCallBack callBack) {
        try {
            if (!testmode) {
                TopCards topCards = CV_Controller.getSolitaireCards(img);
                stateManager.checkState(topCards);
            }
            callBack.OnSuccess(stateManager.getMoves(), stateManager.getHistory());
        } catch (Exception e) {
            callBack.OnError(e);
        }
    }

    @Override
    public void undo(CompletionCallBack callBack) {
        try {
            stateManager.undo();
            callBack.OnSuccess("UNDO registered. Last move and state logged, but deleted from current history. \n\tPlease try to move your cards back and run thi method,'getNextMove' again. \n\tRestart if this doesn't work.");
        } catch (Exception e) {
            callBack.OnError(e);
        }
    }

    @Override
    public void setTestModeOn(boolean test, CompletionCallBack callBack) {
        try {
            this.testmode = test;
            String status = "Test mode ON.";
            if (!testmode) {
                status = "Test mode OFF.";
            }
            callBack.OnSuccess(status);
        } catch (Exception e) {
            callBack.OnError(e);
        }
    }
}
