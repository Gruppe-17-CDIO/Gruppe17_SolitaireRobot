package controller;

import Converter.Convertion;
import computerVision.I_ComputerVisionController;
import dataObjects.Move;
import dataObjects.SolitaireState;
import dataObjects.TopCards;
import javafx.scene.image.Image;
import logic.I_Logic;
import logic.Logic;
import stateBuilding.TopCardsSimulator;

import java.util.List;
import java.util.Stack;

/**
 * @author Erlend
 * See interface for comments.
 */

public class Controller implements I_Controller {
    private I_ComputerVisionController CV_Controller;
    private I_Logic logic;
    private StateManager stateManager;
    private boolean testmode = false;
    private TopCardsSimulator topCardsSimulator;
    private boolean gameStarted = false;

    @Override
    // Note that this method returns the first move suggestion and saves state
    public void startNewGame(Image img, NextMoveCallBack callBack) {
        try {
            logic = new Logic();
            stateManager = new StateManager();
            CV_Controller = new Convertion();
            gameStarted = true;
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

            Move currentMove = stateManager.getBestMove();
            //System.out.println("\nStartNewGame: " + currentMove + "\n");
            //System.out.println(state.getPrintFormat());
            stateManager.updateGameProcess(moves);
            callBack.OnSuccess(currentMove, stateManager.getHistory().peek(), state.getGameProgress());
        } catch (Exception e) {
            gameStarted = false;
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
        // Make sure game is started!
        if (!gameStarted) {
            startNewGame(img, callBack);
        } else {
            try {
                // Get move from state before calculating new
                Move prevMove = stateManager.getBestMove();

                TopCards topCards;
                SolitaireState state;
                if (!testmode) {
                    topCards = CV_Controller.getSolitaireCards(img);
                    state = stateManager.updateState(prevMove, topCards, null, false); // Needs topCards
                    //stateManager.checkStateAgainstImage(topCards, state);

                } else {
                    state = stateManager.updateState(prevMove, null, topCardsSimulator, true); // Test mode needs simulator
                }
                List<Move> moves = logic.getMoves(state);

                // Save the new state and it's move list
                stateManager.saveState(state);
                stateManager.addMovesToState(moves);

                stateManager.updateGameProcess(moves);
                Move move = stateManager.getBestMove();
                //System.out.println("NextMove: " + move);
                callBack.OnSuccess(move, stateManager.getHistory().peek(), state.getGameProgress());

            } catch (Exception e) {
                callBack.OnError(e);
            }
        }
    }

    // Remove last move and try again! Use if a card is misread.
    // Don't use if card is simply not found, only if the value is wrong!
    @Override
    public void redo(Image img, NextMoveCallBack callBack) {
        try {
            stateManager.undo();
        } catch (Exception e) {
            callBack.OnError(e);
        }
        getNextMove(img, callBack);
    }

    // This method is for testing, not part of interface
    public Stack<SolitaireState> getHistory() throws Exception {
        return stateManager.getHistory();
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
