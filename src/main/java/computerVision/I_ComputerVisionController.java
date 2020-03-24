package computerVision;

import repositories.SolitaireCards;

/**
 * @author Erlend
 */

public interface I_ComputerVisionController {

    // Return the whole table of cards as a SolitaireCards object to the controller.
    SolitaireCards getSolitaireCards();

}
