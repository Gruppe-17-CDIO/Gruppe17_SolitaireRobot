import dataObjects.Move;
import dataObjects.SolitaireState;
import java.util.List;

public interface I_Logic {

    List<Move> getMoves(SolitaireState cards);
}
