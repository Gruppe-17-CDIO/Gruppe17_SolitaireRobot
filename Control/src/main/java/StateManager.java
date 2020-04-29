import cardCalculator.CardCalculator;
import dataObjects.Card;
import dataObjects.Move;
import dataObjects.SolitaireState;
import logger.StateLogger;

import java.util.List;
import java.util.Stack;

/* Responsibility:
    Communicate with cardTracker,
    save state,
    keep track of history,
    return current history to controller */

public class StateManager {
    private Stack<SolitaireState> history;
    private StateLogger logger;

    public SolitaireState initiate(Card[] cardData) throws Exception {
        SolitaireState state;
        if (cardData == null) {
            throw new Exception("Card data was null. Can't create state without data from Computer Vision.");
        } else {
            System.out.println("New session started. Creating new state, history and logfile.");
            history = new Stack<>();
            logger = new StateLogger();
            state = new CardCalculator().initiateState(cardData);
            history.push(state);
            logger.logState(state);
        }
        return state;
    }

    public SolitaireState updateState(Card[] cardData, Move prevMove) throws Exception {
        if (logger == null) {
            throw new Exception("The StateLogger was null, but a move has been made. Log may be corrupted.");
        }
        if (prevMove == null) {
            throw new Exception("Previous move was null, but a move has been made: " +
                    "call initiate first.");
        }
        if (history == null) {
            throw new Exception("History was null, but a move has been made. History may be corrupted.");
        }
        // Update state based on move and return.
        SolitaireState state = new CardCalculator().updateState(cardData, history.peek(), prevMove);
        return state;
    }

    public void saveState(SolitaireState state, List<Move> currentMoves) throws Exception {
        if (logger == null) {
            throw new Exception("The StateLogger was null, but a move has been made. Log may be corrupted.");
        }
        if (history == null) {
            throw new Exception("History was null, but a move has been made. History may be corrupted.");
        }
        if (currentMoves == null) {
            throw new Exception("currentMoves was null. Call this method after calculating moves.");
        }
        state.setSuggestedMoves(currentMoves);
        history.push(state);
        logger.logState(state);
    }

    public Stack<SolitaireState> getHistory() throws Exception {
        if (history == null) {
            throw new Exception("History was null.");
        }
        return history;
    }
}
