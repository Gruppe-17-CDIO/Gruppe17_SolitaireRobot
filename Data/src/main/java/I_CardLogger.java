import java.util.List;

/**
 * @author Erlend
 */

public interface I_CardLogger {

    // Log the current state of the game board 'SolitaireCards'
    void logCards(SolitaireCards currentGameCards);

    // Returns states from this session and previous.
    List<SolitaireCards> getHistory();

    // Deletes ALL data on file.
    void deleteAllData();
}
