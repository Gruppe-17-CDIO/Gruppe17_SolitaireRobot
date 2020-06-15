import dataObjects.Move;

import java.util.Comparator;

public class BobbyFischer implements Comparator<Move> {
    @Override
    public int compare(Move o1, Move o2) {
        return 0;
    }

    @Override
    public Comparator<Move> reversed() {
        return null;
    }
}
