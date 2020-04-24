package logger;

import dataObjects.SolitaireState;

import java.util.List;

/**
 * @author Erlend
 */

public interface I_CardLogger {

    // Log the current state of the game board 'SolitaireCards'
    void logCards(SolitaireState currentGameCards);

    // Returns states from this session and previous.
    List<SolitaireState> getHistory();

    // Deletes ALL data on file.
    void deleteCurrentSessionData();
}
