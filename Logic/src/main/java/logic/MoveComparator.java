package logic;

import com.google.common.collect.ComparisonChain;
import dataObjects.Move;

import java.util.Comparator;

public class MoveComparator implements Comparator<Move> {
    @Override
    public int compare(Move m1, Move m2) {
        return ComparisonChain.start()
                .compare(benefitTypeToInt(m1), benefitTypeToInt(m2))
                .compare(moveTypeToInt(m1), moveTypeToInt(m2))
                .compare(destTypeToInt(m1), destTypeToInt(m2))
                .result();
    }

    int moveTypeToInt(Move move) {
        switch (move.getMoveType()) {
            case FACE_UP_IN_PILE:
                return 0;
            case MOVE_FROM_PILE:
                return 1;
            case USE_DRAWN:
                return 2;
            default:
                return 3;
        }
    }

    int benefitTypeToInt(Move move) {
        if (move.getBenefit() == null) {
            return 0;
        }
        switch (move.getBenefit()) {
            case REVEAL_CARD:
                return 0;
            case PLACE_KING:
                return 1;
            case CLEAN_PILE:
                return 2;
            case NO_BENEFIT:
                return 3;
            default:
                return 0;
        }
    }

    int destTypeToInt(Move move) {
        if (move.getDestinationType() == null) {
            return 0;
        }
        switch (move.getDestinationType()) {
            case SELF:
                return 0;
            case FOUNDATION:
                return 1;
            case PILE:
                return 2;
            default:
                return 0;
        }
    }

    @Override
    public Comparator<Move> reversed() {
        return null;
    }
}
