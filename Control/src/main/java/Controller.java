import CV_simulation.StateGenerator;
import dataObjects.Move;
import dataObjects.SolitaireState;
import javafx.scene.image.Image;
import logger.CardLogger;
import logger.I_CardLogger;

/**
 * @author Erlend
 */
public class Controller implements I_Controller {
    private I_Logic logic; // Instantiate
    private final I_CardLogger logger = new CardLogger();
    private I_ComputerVisionController CV_Controller; // Instantiate
    private SolitaireState cards;
    private Move lastMove;

    public void getNextMove(Image img, NextMoveCallback callback) {
        try {
            //cards = CV_Controller.getSolitaireCards(img);
            cards = StateGenerator.getState(0);

            // Check if cards are as expected after previous move
            boolean correct = true;

            if (correct) {
                lastMove = logic.getMoves(cards).get(0);
                callback.OnSuccess(lastMove);
                logger.logCards(cards);
            } else {
                callback.OnFailure("Failed", lastMove, cards);
            }
        } catch (Exception e) {
            callback.OnError(e);
        }
    }

    public Image getImage() {
        return new Image((java.io.InputStream) null);
    }

    public SolitaireState getCards() {
        return cards;
    }

    //TODO: Update state

    // TODO: CAllback listener

}
