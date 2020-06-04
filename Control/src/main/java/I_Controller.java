import dataObjects.Move;
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
    void getFirstMove(Image img, NextMoveCallBack callBack);

    /**
     * Stores move and calculates new moves
     *
     * @param move The chosen move
     */
    void performMove(Move move, CompletionCallBack callBack);

    /**
     * This method controls state against the
     * data from the image, and returns a move list.
     * Can be run several times in a row, as it doesn't
     * change state.
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
}


