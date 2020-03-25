import java.io.File;

/**
 * @author Erlend
 */

public interface I_Controller {

    // 1. Gets SolitaireCards from ComputerVision
    // 2. Gets move from logic
    // 3. Returns the recommended Move to GUI.
    Move getNextMove(File img);

    // Returns image to GUI if needed
    File getImage();

    // Returns 'cards'-object to gui if needed
    SolitaireState getCards();

}
