package dataObjects;

public class Move {
    private final MoveType moveType;
    private final DestinationType destinationType;
    private final int[] position;
    private final int destPosition;
    private final MoveBenefit benefit;

    public Move(MoveType moveType, int[] position, DestinationType destinationType, int destPosition, MoveBenefit benefit) {
        this.moveType = moveType;
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

    public String toString() {
        if (moveType == MoveType.DRAW) {
            return "Draw a new card from the stock. (Turn the pile if there are no cards left.)";
        } else if (moveType == MoveType.FACEUP) {
            return "Turn pile " + position[0] + ", card " + position[1] + " face up.";
        } else if (moveType == MoveType.USEDRAWN) {
            return "Move card from drawn cards to " + destinationType + " " + destPosition + ".";
        } else if (moveType == MoveType.MOVE) {
            return "Move from piles " + position[0] + ", card " + position[1] +
                    " to " + destinationType + " " + destPosition + ".";
        } else {
            return "No Movetype";
        }
    }

    public MoveBenefit getBenefit() {
        return benefit;
    }

    public enum MoveType {
        FACEUP,
        MOVE,
        USEDRAWN,
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
    }
}
