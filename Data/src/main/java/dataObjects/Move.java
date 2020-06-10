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
            return "Draw a new card from the stock. (Turn the pile if there are no cards left.)";
        } else if (moveType == MoveType.FACEUP) {
            return "Turn " + position[0] + " " + position[1] + " face up.";
        } else if (moveType == MoveType.USEDRAWN) {
            return "Moved card from drawn cards to " + destinationType + " " + destPosition + ".";
        } else if (moveType == MoveType.MOVE) {
            return "Move from piles " + position[0] + ", card " + position[1] +
                    " to " + destinationType + " " + destPosition + ".";
        } else {
            return "No Movetype";
        }
    }

    public enum MoveType {
        DRAW,
        FACEUP,
        USEDRAWN,
        MOVE
    }

    public enum DestinationType {
        PILE,
        FOUNDATION,
        SELF
    }
}
