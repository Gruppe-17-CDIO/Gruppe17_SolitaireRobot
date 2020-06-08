package Converter.Util;

import Data.PreCard;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Util {


    public List<PreCard> getPreCard(JsonArray obj){

        List<PreCard> preCardList = new ArrayList<PreCard>();
        for(int i = 0; i<obj.size();i++){
            PreCard preCard = new PreCard();
            JsonObject element = obj.get(i).getAsJsonObject();
            preCard.setUpperCoordinate(getUpperCoordinate(element));
            preCard.setLowerCoordinate(getLowerCoordinate(element));
            preCard.setColor(getColor(element));
            preCard.setRank(getRank(element));
            preCardList.add(preCard);
        }

        return preCardList;
    }


    private Point getUpperCoordinate(JsonObject obj){
        double X_koordinate = getUpperKoordinate_X(obj);
        double Y_koordinate = getUpperKoordinate_Y(obj);

        Point newPoint= new Point();
        newPoint.setLocation(X_koordinate,Y_koordinate);

        return newPoint;
    }

    private Point getLowerCoordinate(JsonObject obj){
        double X_koordinate = getLowerKoordinate_X(obj);
        double Y_koordinate = getLowerKoordinate_Y(obj);

        Point newPoint= new Point();
        newPoint.setLocation(X_koordinate,Y_koordinate);

        return newPoint;
    }



    private double getUpperKoordinate_X(JsonObject obj){
        return obj.get("upperKoordinate_X").getAsInt();

    }

    private double getUpperKoordinate_Y(JsonObject obj){
        return obj.get("upperKoordinate_Y").getAsInt();

    }

    private double getLowerKoordinate_X(JsonObject obj){
        return obj.get("lowerKoordinate_X").getAsInt();

    }

    private double getLowerKoordinate_Y(JsonObject obj){
        return obj.get("lowerKoordinate_Y").getAsInt();

    }

    private int getRank(JsonObject obj){
        String classsss = obj.get("Classification").toString();
        classsss = classsss.replace("\"","");
        String subString = classsss.substring(1);
        int rank = Integer.parseInt(subString);
        return rank;
    }

    private String getColor(JsonObject obj){
        String classsss = obj.get("Classification").toString();
        classsss = classsss.replace("\"","");
        String color = classsss.substring(0,1);
        return color;
    }

    /*
    private class Sorting {
        boolean moover = true;
        private List<PreCard> Sorting(List<PreCard> list){
            for(int i = 0; i<list.size();i++){

                if(list.get(list.size()-i-1).getUpperCoordinate().getX()<list.get(list.size()-i-1-1).getUpperCoordinate().getX()&&
                        list.get(list.size()-i-1).getUpperCoordinate().getY()<list.get(list.size()-i-1-1).getUpperCoordinate().getY()){

                }

            }
        }

        private void MoveUp(){

        }

        private void MoveDown(){

        }
    }

     */

}
