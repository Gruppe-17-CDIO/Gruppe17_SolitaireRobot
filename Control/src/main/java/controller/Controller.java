package controller;

import computerVision.I_ComputerVisionController;
import dataObjects.Move;
import dataObjects.SolitaireState;
import dataObjects.TopCards;
import javafx.scene.image.Image;
import logic.I_Logic;
import logic.Logic;
import stateBuilding.TopCardsSimulator;

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
    private Move currentMove;
    private TopCardsSimulator topCardsSimulator;

    @Override
    // Note that this method returns the first move suggestion and saves state
    public void startNewGame(Image img, NextMoveCallBack callBack) {
        try {
            TopCards topCards;
            SolitaireState state;
            if (!testmode) {
                topCards = CV_Controller.getSolitaireCards(img);
            } else {
                topCardsSimulator = new TopCardsSimulator();
                topCards = topCardsSimulator.getSimTopCards();
            }
            state = stateManager.initiate(topCards); // Make new history, logfile and state
            List<Move> moves = logic.getMoves(state);
            stateManager.saveState(state);
            stateManager.addMovesToState(moves);
            callBack.OnSuccess(stateManager.getMoves(), stateManager.getHistory(), state.isWon());
        } catch (Exception e) {
            callBack.OnError(e);
        }
    }

    /**
     * precondition: A state exists in history & a list of moves have been generated. This method simply saves
     * the selected move, to allow the player to set the cards before calculating next move.
     *
     * @param move     The chosen move
     * @param callBack
     */
    @Override
    public void performMove(Move move, CompletionCallBack callBack) {
        try {
            if (move == null) {
                callBack.OnFailure("Move was null. Please supply a move.");
            } else {
                currentMove = move;
                callBack.OnSuccess("OK: Move registered.");
            }
        } catch (Exception e) {
            callBack.OnError(e);
        }
    }

    /**
     * Precondition: A move has been selected, saved as currentMove. The cards are moved in the same way as the
     * selected move. A State exists in history.
     * <p>
     * This method starts image analysis, creates a new state based on move and image data, checks for inconsistencies,
     * calculates moves and saves new state + move list.
     *
     * @param img      Image to control the state
     * @param callBack List of suggested moves and history
     */
    @Override
    public void getNextMove(Image img, NextMoveCallBack callBack) {
        try {
            TopCards topCards;
            SolitaireState state;
            if (!testmode) {
                topCards = CV_Controller.getSolitaireCards(img);
                state = stateManager.updateState(currentMove, topCards, null, false); // Needs topCards
                stateManager.checkStateAgainstImage(topCards, state);

            } else {
                state = stateManager.updateState(currentMove, null, topCardsSimulator, true); // Test mode needs simulator
            }
            List<Move> moves = logic.getMoves(state);

            // Save the new state and it's move list
            stateManager.saveState(state);
            stateManager.addMovesToState(moves);

            // Reset move
            currentMove = null;

            callBack.OnSuccess(stateManager.getMoves(), stateManager.getHistory(), state.isWon());
        } catch (Exception e) {
            callBack.OnError(e);
        }
    }

    @Override
    public void undo(CompletionCallBack callBack) {
        try {
            stateManager.undo();
            callBack.OnSuccess("UNDO registered. Last move and state logged, but deleted from current history. " +
                    "\nPerform move again to continue");
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