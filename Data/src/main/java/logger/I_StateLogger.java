package logger;

import dataObjects.SolitaireState;

import java.util.List;

/**
 * @author Erlend
 */

public interface I_StateLogger {

    // Log the current state of the game board 'SolitaireCards'
    void logState(SolitaireState currentGameCards);

    // Returns states from this session and previous.
    List<SolitaireState> getHistory();

    // Deletes ALL data on file.
    void deleteCurrentSessionData();
}
