import dataObjects.Card;
import dataObjects.Move;
import dataObjects.SolitaireState;
import javafx.scene.image.Image;
import logger.CardLogger;
import logger.I_CardLogger;

import java.util.List;

/**
 * @author Erlend
 */
public class Controller implements I_Controller {
    private I_Logic logic; // Instantiate
    private final I_CardLogger logger = new CardLogger();
    private I_ComputerVisionController CV_Controller; // Instantiate
    private List<Card> cardData;
    private StateHandler stateHandler;
    private Move lastMove = null;

    public void getNextMove(Image img, NextMoveCallback callback) {
        try {
            //cardData = CV_Controller.getSolitaireCards(img); // Never null
            SolitaireState state = stateHandler.updateState(cardData, lastMove); // lastMove is null at beginning of game

            // TODO Check if cards are as expected after previous move
            boolean correct = true;

            if (correct) {
                // State for test and dev
                lastMove = logic.getMoves(state).get(0);
                callback.OnSuccess(lastMove);
                logger.logCards(state);
            } else {
                callback.OnFailure("Can't recognise last move.", lastMove, state);
            }
        } catch (Exception e) {
            callback.OnError(e);
        }
    }

    public Image getImage() {
        return new Image((java.io.InputStream) null);
    }

    public SolitaireState getCards() throws Exception {
        return stateHandler.getState();
    }
}
