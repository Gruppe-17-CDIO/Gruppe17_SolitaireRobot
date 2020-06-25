package Data;

import computerVision.Converter.Util.Util;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Andreas B.G. Jensen
 * Class used for avereging a X-coordinate for more outputelements of the same type.
 */
public class InterMidiateXClass {
    String type;
    ArrayList<Double> xCoordinates = new ArrayList<>();

    public InterMidiateXClass(){}


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public void addXCoordinate(double xValue){
        xCoordinates.add(xValue);
    }

    public double getAverageX(){
        Collections.sort(xCoordinates);
        if(!xCoordinates.isEmpty()) {
            return Util.calculateAverageX(xCoordinates.get(0),xCoordinates.get(xCoordinates.size()-1));
        }
        return 0;
    }
}