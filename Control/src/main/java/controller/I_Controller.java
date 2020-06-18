package controller;

import javafx.scene.image.Image;

/**
 * @author Erlend
 */

public interface I_Controller {

    /**
     * This method calculates the first list of moves.
     * Can be used to start and restart game.
     *
     * @param img      Image input from view
     * @param callBack return suggested moves and history
     */
    void startNewGame(Image img, NextMoveCallBack callBack);

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
    void getNextMove(Image img, NextMoveCallBack callBack);

    /**
     * Undo the last move, giving the player a
     * chance to save a broken game.
     * (The 'bad' state is still in the log file.)
     *
     * @param callBack A status message and history
     */
    void undo(CompletionCallBack callBack);

    /**
     * Enter test mode, which maintains state without computervision
     *
     * @param test,    boolean to indicate test mode on or off
     * @param callBack
     */
    void setTestModeOn(boolean test, CompletionCallBack callBack);
}


