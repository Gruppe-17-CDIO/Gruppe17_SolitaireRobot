package Data;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * * @author Andreas B.G. Jensen
 */
public class PreCard{
    private Point upperCoordinate;
    private Point lowerCoordinate;
    private String Color;
    private int rank;

    public Point getUpperCoordinate() {
        return upperCoordinate;
    }

    public void setUpperCoordinate(Point upperCoordinate) {
        this.upperCoordinate = upperCoordinate;
    }

    public Point getLowerCoordinate() {
        return lowerCoordinate;
    }

    public void setLowerCoordinate(Point lowerCoordinate) {
        this.lowerCoordinate = lowerCoordinate;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String toString(){
        return this.getColor()+this.getRank();
    }


}
