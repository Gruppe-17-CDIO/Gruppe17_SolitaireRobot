package Converter;

import Converter.Util.Util;
import DarkNet_Connection.Darknet_Stub;
import DarkNet_Connection.I_Connection;
import Data.PreCard;
import com.google.gson.JsonArray;
import dataObjects.Card;
import javafx.scene.image.Image;

import java.util.List;

public class Convertion implements I_ComputerVisionController {
    Util utility = new Util();
    I_Connection connection = new Darknet_Stub();
    Image img;
    double upperHeight;
    double lowerHeight;
    double boxWidth;
    double foundationBoxes[] = new double[4];
    double drawPile[] = new double[1];
    int numberOfBoxes = 7; //The number of boxes the width should be divided with.
    double pileBoxes[] = new double[numberOfBoxes];



    @Override
    public Card[] getSolitaireCards(Image img) {

        this.img = img;

        return new Card[0];
    }


    public void calibrateImgBoxes(Image img){

        lowerHeight = img.getHeight()/2;
        upperHeight = img.getHeight()/2+lowerHeight;

        //Creating boxessizes for piles
        boxWidth = img.getWidth()/7;

        //Creating gridboxes
        for(int i = 1; i<=numberOfBoxes;i++){
            pileBoxes[i-1]= boxWidth *i;
            System.out.println("pileBox: "+pileBoxes[i-1]);

            //Creating four foundation boxes
            if(numberOfBoxes-i<=3){
                foundationBoxes[numberOfBoxes-i] = boxWidth*i;
                System.out.println("foundationBox: "+foundationBoxes[numberOfBoxes-i]);
            }

            //Creating bow for drawpile
            if(numberOfBoxes-i==0){
                drawPile[numberOfBoxes-i]=boxWidth*3;
                System.out.println("drawPile: "+drawPile[numberOfBoxes-i]);
            }
        }
    }


    private JsonArray ConvertImage(Image img){
        JsonArray returnArray = connection.Get_Image_Information(img);
        List<PreCard> preCardList = utility.getPreCard(returnArray);

        return null;

    }
}
