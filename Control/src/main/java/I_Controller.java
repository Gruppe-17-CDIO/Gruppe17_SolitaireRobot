import java.io.File;

/**
 * @author Erlend
 */

public interface I_Controller {

    // 1. Polls Computer vision for a new SolitaireCards
    // 2. Polls Logic for a new Move
    // 3. Returns the recommended Move to GUI.
    Move getNextMove();

    // Returns image to GUI if needed
    File getImage();

    // Returns 'cards'-object to gui if needed
    SolitaireCards getCards();

}
