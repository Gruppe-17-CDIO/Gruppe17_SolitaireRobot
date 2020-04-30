package dataObjects;

public class Move {
    private final MoveType moveType;
    private final DestinationType destinationType;
    private final int[] position;
    private final int destPosition;

    public Move(MoveType moveType, int[] position, DestinationType destinationType, int destPosition) {
        this.moveType = moveType;
        this.destinationType = destinationType;
        this.position = position;
        this.destPosition = destPosition;
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
            return "Draw a card from the stock.";
        } else if (moveType == MoveType.FACEUP) {
            return "Turn " + position[0] + " " + position[1] + " face up.";
        } else {
            return "Move" + position[0] + " " + position[1] + " to " + destinationType + " " + destPosition;
        }
    }

    public enum MoveType {
        FACEUP,
        MOVE,
        DRAW
    }

    public enum DestinationType {
        PILE,
        FOUNDATION,
        SELF
    }
}
