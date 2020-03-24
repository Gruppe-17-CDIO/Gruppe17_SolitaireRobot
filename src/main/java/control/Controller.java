package control;

import computerVision.I_ComputerVisionController;
import logic.I_Logic;
import repositories.CardLogger;
import repositories.I_CardLogger;
import repositories.Move;
import repositories.SolitaireCards;

import java.io.File;

/**
 * @author Erlend
 */
public class Controller implements I_Controller {
    private I_Logic logic; // Instantiate
    private I_CardLogger logger = new CardLogger();
    private I_ComputerVisionController CV_Controller; // Instantiate
    private SolitaireCards cards;

    public Move getNextMove() {
        cards = CV_Controller.getSolitaireCards();
        logger.logCards(cards);
        return logic.getMove(cards);
    }

    public File getImage() {
        return null;
    }

    public SolitaireCards getCards() {
        return cards;
    }
}
