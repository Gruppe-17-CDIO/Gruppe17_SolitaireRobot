package dataObjects;

public class Move {
    private Type availableType;
    private Type destinationType;
    private int[][] position;
    private int[] destPosition;

    public Move(Type availableType, int[][] position, Type destinationType, int[] destPosition) {
        this.availableType = availableType;
        this.destinationType = destinationType;
        this.position = position;
        this.destPosition = destPosition;
    }

    public enum Type {
        DRAW,
        STOCK,
        PILE,
        FOUNDATION
    }

    public Type getAvailableType() {
        return availableType;
    }

    public Type getDestinationType() {
        return destinationType;
    }

    public int[][] getPosition() {
        return position;
    }

    public int[] getDestPosition() {
        return destPosition;
    }
}
