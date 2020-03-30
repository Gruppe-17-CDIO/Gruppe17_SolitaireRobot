import CV_simulation.StateGenerator;
import dataObjects.Move;
import dataObjects.SolitaireState;
import logger.CardLogger;
import logger.I_CardLogger;

import java.io.File;

/**
 * @author Erlend
 */
public class Controller implements I_Controller {
    private I_Logic logic; // Instantiate
    private I_CardLogger logger = new CardLogger();
    private I_ComputerVisionController CV_Controller; // Instantiate
    private SolitaireState cards;

    public Move getNextMove(File img) throws Exception {
        //cards = CV_Controller.getSolitaireCards(img);
        cards = StateGenerator.getState(0);
        logger.logCards(cards);
        return logic.getMoves(cards).get(0);
    }

    public File getImage() {
        return null;
    }

    public SolitaireState getCards() {
        return cards;
    }
}
