import javafx.scene.image.Image;

/**
 * @author Erlend
 */

public interface I_Controller {

    // Start or restart the game. Generates new log and history.
    // and suggests move.
    void initiate(Image img, NextMoveCallback callback);

    // Returns the recommended moves and history to GUI.
    void getNextMove(Image img, NextMoveCallback callback);

}


