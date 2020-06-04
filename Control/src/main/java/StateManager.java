import cardCalculator.CardCalculator;
import dataObjects.Move;
import dataObjects.SolitaireState;
import dataObjects.TopCards;
import logger.StateLogger;
import stateBuilding.StateGenerator;

import java.util.List;
import java.util.Stack;

/**
 * Responsibility:
 * 1. Communicate with cardcalculator
 * 2. save state
 * 3. keep track of history
 * 4. return current history to controller
 */

public class StateManager {
    private Stack<SolitaireState> history; // This is the main history
    private StateLogger logger; // Making logfiles
    private final CardCalculator cardCalculator = new CardCalculator(); // Updating the state

    public SolitaireState initiate(TopCards cardData) throws Exception {
        SolitaireState state;
        if (cardData == null) {
            throw new Exception("Card data was null. Can't create state without data from Computer Vision.");
        } else {
            System.out.println("New session started. Creating new state, blank history and logfile.");
            history = new Stack<>();
            logger = new StateLogger();
            state = cardCalculator.initiateState(cardData);
        }
        return state;
    }

    // Overridden for test mode only
    public SolitaireState initiate() throws Exception {
        System.out.println("Test session started. Creating new state, blank history and logfile.\n");
        history = new Stack<>();
        logger = new StateLogger();
        return new StateGenerator().getState(2);
    }

    public SolitaireState updateState(Move move) throws Exception {
        if (logger == null) {
            throw new Exception("The StateLogger was null, but a move has been made. Log may be corrupted.");
        }
        if (move == null) {
            throw new Exception("Previous move was null, but a move has been made: " +
                    "call initiate first.");
        }
        if (history == null) {
            throw new Exception("History was null, but a move has been made. History may be corrupted.");
        }
        // Update state based on previous state and move.
        return cardCalculator.updateState(history.peek(), move);
    }

    public void saveState(SolitaireState state, List<Move> suggestedMoves) throws Exception {
        if (logger == null) {
            throw new Exception("The StateLogger was null, but a move has been made. Log may be corrupted.");
        }
        if (history == null) {
            throw new Exception("History was null, but a move has been made. History may be corrupted.");
        }
        if (suggestedMoves == null) {
            throw new Exception("suggestedMoves was null. Call this method after calculating moves.");
        }
        state.setSuggestedMoves(suggestedMoves);
        history.push(state);
        logger.logState(state);
    }

    public Stack<SolitaireState> getHistory() throws Exception {
        if (history == null) {
            throw new Exception("getHistory: History was null.");
        }
        return history;
    }

    public void checkState(TopCards topCards) throws Exception {
        // Checking if state fits with image data.
        cardCalculator.checkState(topCards, history.peek());
    }

    public SolitaireState getState() throws Exception {
        if (history == null) {
            throw new Exception("getState: History was null.");
        } else if (history.peek() == null) {
            throw new Exception("getState: History was empty. No state saved yet.");
        }
        return history.peek();
    }

    public List<Move> getMoves() throws Exception {
        if (history == null) {
            throw new Exception("getMoves: History was null.");
        } else if (history.peek() == null) {
            throw new Exception("getState: History was empty. No state saved yet.");
        } else if (history.peek().getSuggestedMoves() == null) {
            throw new Exception("getState: State has no moves.");
        }
        return history.peek().getSuggestedMoves();
    }

    public void undo() throws Exception {
        history.pop();
        try {
            logger.logState(history.peek());
        } catch (Exception e) {
            throw new Exception("Could not log state. If this is initial state, you should use 'getFirstMove' to restart, not undo.\n" + e.getMessage());
        }
    }
}
