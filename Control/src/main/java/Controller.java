import java.io.File;

/**
 * @author Erlend
 */
public class Controller implements I_Controller {
    private I_Logic logic; // Instantiate
    private I_CardLogger logger = new CardLogger();
    private I_ComputerVisionController CV_Controller; // Instantiate
    private SolitaireState cards;

    public Move getNextMove(File img) {
        cards = CV_Controller.getSolitaireCards();
        logger.logCards(cards);
        return logic.getMove(cards);
    }

    public File getImage() {
        return null;
    }

    public SolitaireState getCards() {
        return cards;
    }
}
