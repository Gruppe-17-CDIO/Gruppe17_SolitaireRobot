package dataObjects;

import static dataObjects.Move.DestinationType.FOUNDATION;
import static dataObjects.Move.MoveBenefit.TURN_STOCK;

public class Move {
    private final MoveType moveType;
    private final DestinationType destinationType;
    private final int[] position;
    private final int destPosition;
    private final MoveBenefit benefit;

    private final Card card;

    public Move(MoveType moveType, Card card, int[] position, DestinationType destinationType, int destPosition, MoveBenefit benefit) {
        this.moveType = moveType;
        this.card = card;
        this.destinationType = destinationType;
        this.position = position;
        this.destPosition = destPosition;
        this.benefit = benefit;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public DestinationType getDestinationType() {
        return destinationType;
    }

    public int[] getPosition() {
        return position;
    }

    public int getDestPosition() {
        return destPosition;
    }

    public Card getCard() {
        return card;
    }

    public MoveBenefit getBenefit() {
        return benefit;
    }

    public String toString() {
        String s;
        if (moveType == MoveType.DRAW) {
            s = "";
            if (benefit == TURN_STOCK) {
                s += "Turn the pile. ";
            }
            s += "Draw a new card from the stock.";

        } else if (moveType == MoveType.FACE_UP_IN_PILE) {
            return "Turn pile " + (position[0] + 1) + ", card " + (position[1] + 1) + " face up.";
        } else if (moveType == MoveType.USE_DRAWN) {
            s = "Move the " + card + " from drawn cards to ";
            if (destinationType == FOUNDATION) {
                s += card.getSuit() + " foundation.";
            } else {
                s += destinationType + " " + (destPosition + 1) + ".";
            }
        } else if (moveType == MoveType.MOVE_FROM_PILE) {

            s = "Move the " + card + " from pile " + (position[0] + 1) + " to ";
            if (destinationType == FOUNDATION) {
                s += card.getSuit() + " foundation.";
            } else {
                s += destinationType + " " + (destPosition + 1) + ".";
            }
        } else {
            return "No Movetype";
        }
        return s;
    }

    public enum MoveType {
        FACE_UP_IN_PILE,
        MOVE_FROM_PILE,
        USE_DRAWN,
        DRAW,
    }

    public enum DestinationType {
        SELF,
        FOUNDATION,
        PILE
    }

    public enum MoveBenefit {
        CLEAN_PILE,
        PLACE_KING,
        REVEAL_CARD,
        NO_BENEFIT,
        TURN_STOCK
    }
}
