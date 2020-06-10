package Converter.Util;

import Data.PreCard;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/*
Class used for mapping a JsonObject to PreCard objects.
 */
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

    public static void display(BufferedImage image){
        JFrame frame = null;
        JLabel label = null;
        if(frame==null){
            frame=new JFrame();
            frame.setTitle("stained_image");
            frame.setSize(image.getWidth(), image.getHeight());
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            label=new JLabel();
            label.setIcon(new ImageIcon(image));
            frame.getContentPane().add(label, BorderLayout.CENTER);
            frame.setLocationRelativeTo(null);
            frame.pack();
            frame.setVisible(true);
        }else label.setIcon(new ImageIcon(image));
    }

    public static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }

}
