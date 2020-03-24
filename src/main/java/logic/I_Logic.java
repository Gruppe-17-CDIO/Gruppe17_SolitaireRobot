package logic;

import repositories.Move;
import repositories.SolitaireCards;

public interface I_Logic {

    Move getMove(SolitaireCards cards);
}
