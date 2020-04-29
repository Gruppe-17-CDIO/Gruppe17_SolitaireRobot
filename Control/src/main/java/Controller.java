import dataObjects.Card;
import dataObjects.Move;
import dataObjects.SolitaireState;
import javafx.scene.image.Image;

import java.util.List;

/**
 * @author Erlend
 */
public class Controller implements I_Controller {
    private final I_Logic logic = new Logic();
    private final StateManager stateManager = new StateManager();
    private I_ComputerVisionController CV_Controller; // Instantiate
    private Move prevMove;

    @Override
    public void initiate(Image img, NextMoveCallback callback) {
        try {
            // 1. Get new image
            Card[] cardData = CV_Controller.getSolitaireCards(img);
            // 2. reset history and make new logfile
            // 3. Calculate State
            SolitaireState state = stateManager.initiate(cardData);
            // 4. Get moves
            List<Move> moves = logic.getMoves(state);
            // 5. Store state and moves
            stateManager.saveState(state, moves);
            // 6. Keep the previous move. (Not list)
            prevMove = moves.get(0);
            // 7. Return move and history
            callback.OnSuccess(moves, stateManager.getHistory());
        } catch (Exception e) {
            callback.OnError(e);
        }

    }

    @Override
    public void getNextMove(Image img, NextMoveCallback callback) {
        try {
            // 1. Get new image
            Card[] cardData = CV_Controller.getSolitaireCards(img);
            // 2. Calculate current state based on previous state and previous move
            // 3. Check this against image
            SolitaireState state = stateManager.updateState(cardData, prevMove);
            // 4.  Get move
            List<Move> moves = logic.getMoves(state);
            // 5. Save state and moves
            stateManager.saveState(state, moves);
            // 6. Keep the previous move. (Not list)
            prevMove = moves.get(0);
            // 7. Return move and history
            callback.OnSuccess(moves, stateManager.getHistory());
        } catch (Exception e) {
            callback.OnError(e);
        }
    }
}
