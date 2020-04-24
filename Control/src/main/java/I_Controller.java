import dataObjects.SolitaireState;
import javafx.scene.image.Image;

/**
 * @author Erlend
 */

public interface I_Controller {

    // 1. Gets SolitaireCards from ComputerVision
    // 2. Gets move from logic
    // 3. Returns the recommended Objects.Move to GUI.
    void getNextMove(Image img, NextMoveCallback callback) throws Exception;

    // Returns image to GUI if needed
    Image getImage();

    // Returns 'cards'-object to gui if needed
    SolitaireState getCards() throws Exception;

}


