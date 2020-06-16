package controller;

import cardCalculator.CardCalculator;
import dataObjects.GlobalEnums.GameProgress;
import dataObjects.Move;
import dataObjects.SolitaireState;
import dataObjects.TopCards;
import logger.StateLogger;
import stateBuilding.TopCardsSimulator;

import java.util.List;
import java.util.Stack;

import static dataObjects.GlobalEnums.GameProgress.LOST;
import static dataObjects.GlobalEnums.GameProgress.WON;

/**
 * Responsibility:
 * 1. Communicate with cardcalculator
 * 2. save state
 * 3. keep track of history
 * 4. return current history to controller
 */

public class StateManager {
    private final CardCalculator cardCalculator = new CardCalculator(); // Updating the state
    private Stack<SolitaireState> history; // This is the main history
    private StateLogger logger; // Making logfiles

    public SolitaireState initiate(TopCards cardData) throws Exception {
        SolitaireState state;
        if (cardData == null) {
            throw new Exception("Card data was null. Can't create state without data from Computer Vision.");
        } else {
            System.out.println("New session started. Creating new state, blank history and logfile. " +
                    "Ignore missing logfile for read!");
            history = new Stack<>();
            logger = new StateLogger();
            state = cardCalculator.initiateState(cardData);
        }
        return state;
    }

    public SolitaireState updateState(Move move, TopCards topCards, TopCardsSimulator topCardsSimulator, Boolean test) throws Exception {
        // Exceptions
        if (logger == null) {
            throw new Exception("The StateLogger was null, but a move has been made. Log may be corrupted.");
        }
        if (history == null) {
            throw new Exception("History was null, but a move has been made. History may be corrupted.");
        }

        // Special case:
        if (move == null) {
            System.out.println("'updatestate' was called twice with no new move. Returning current state.");
            return (history.peek());
        }

        // Update state based on previous state and move.
        if (test) {
            if (topCardsSimulator == null) {
                throw new Exception("updateState: Test needs TopCardsSimulator.");
            }
            return cardCalculator.updateState(history.peek(), move, null, topCardsSimulator, true);
        } else {
            if (topCards == null) {
                throw new Exception("updatestate: Missing TopCards");
            }
            return cardCalculator.updateState(history.peek(), move, topCards, null, false);
        }

    }

    public void saveState(SolitaireState state) throws Exception {
        if (logger == null) {
            throw new Exception("The StateLogger was null, but a move has been made. Log may be corrupted.");
        }
        if (history == null) {
            throw new Exception("History was null, but a move has been made. History may be corrupted.");
        }
        history.push(state);
        logger.logState(state);
    }

    public void addMovesToState(List<Move> suggestedMoves) throws Exception {
        SolitaireState state = history.peek();
        if (suggestedMoves == null) {
            throw new Exception("suggestedMoves was null. Call this method after calculating moves.");
        }
        state.setSuggestedMoves(suggestedMoves);
    }

    public Stack<SolitaireState> getHistory() throws Exception {
        if (history == null) {
            throw new Exception("getHistory: History was null.");
        }
        return history;
    }

    public SolitaireState checkStateAgainstImage(TopCards topCards, SolitaireState state) throws Exception {
        // Checking if state fits with image data.
        return cardCalculator.checkState(topCards, state);
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

    public Move getBestMove() {
        SolitaireState state = history.peek();
        if (state.getSuggestedMoves().size() == 0) {
            return null;
        } else {
            return state.getSuggestedMoves().get(0);
        }
    }

    public GameProgress checkGameProgress(List<Move> moves) {
        SolitaireState state = history.peek();
        if (moves.size() < 1) {
            if (cardCalculator.checkWin(state)) {
                state.setGameProgress(WON);
            } else {
                state.setGameProgress(LOST);
            }
        }
        return state.getGameProgress();
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
