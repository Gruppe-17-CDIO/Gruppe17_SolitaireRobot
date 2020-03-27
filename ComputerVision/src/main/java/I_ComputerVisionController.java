import dataObjects.SolitaireState;

import java.io.File;

/**
 * @author Erlend
 */

public interface I_ComputerVisionController {

    // Return the whole table of cards as a SolitaireCards object to the controller.
    // (Image format is not decided, file is just an example.)
    SolitaireState getSolitaireCards(File img);
}
