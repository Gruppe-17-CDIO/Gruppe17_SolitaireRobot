package dataObjects;

public class Move {
    private MoveType moveType;
    private DestinationType destinationType;
    private int[] position;
    private int destPosition;

    public Move(MoveType moveType, int[] position, DestinationType destinationType, int destPosition) {
        this.moveType = moveType;
        this.destinationType = destinationType;
        this.position = position;
        this.destPosition = destPosition;
    }

    public enum MoveType {
        FACEUP,
        MOVE,
        DRAW
    }

    public enum DestinationType {
        PILE,
        FOUNDATION
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
}
