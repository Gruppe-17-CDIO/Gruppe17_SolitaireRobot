package Converter;

import Data.PreCard;
import dataObjects.Card;
import dataObjects.ConvertState;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class ImageBoxes {
    double upperHeight;
    double lowerHeight;
    double lowerBoxWidth;
    double upperBoxWidth;
    int numberOfFoundation = 4;
    int numberOfDrawpile = 1;
    double foundationBoxes[] = new double[numberOfFoundation];
    double drawPile[] = new double[numberOfDrawpile];
    int numberOfBoxes = 7; //The number of boxes the width should be divided with (lower row).
    double pileBoxes[] = new double[numberOfBoxes];
    //List of boxes. Indexes description:
    // 0 will be the draw image
    // 1 will be the foundation
    // 2 will be the piles.
    List<double[]> imageBoxesList = new ArrayList<>();
    /*
   Divide the image into sections. Ex height/2  and length/7.
   _____________________________
   |      |      |      |
   |      |      |      |
   -----------------------------
   |   |   |   |   |   |   |   |
   |   |   |   |   |   |   |   |
   -----------------------------
   These boundary boxes will be used to to check if the incoming coordinates (From darknet) are located in one of the boxes.
    */
    public List<double[]> calibrateImgBoxes(Image img){

        lowerHeight = img.getHeight()/2;
        upperHeight = img.getHeight()/2+lowerHeight;

        //Creating boxessizes for piles in lower row
        lowerBoxWidth = img.getWidth()/numberOfBoxes;
        //Creating boxessizes for piles for upper row

        //Creating gridboxes
        for(int i = 1; i<=numberOfBoxes;i++) {
            pileBoxes[i - 1] = lowerBoxWidth * i;
        }

        //Creating drawPile and foundation pile
        int numberOfBoxesInUppeRow = numberOfDrawpile+numberOfFoundation;

        //Creating boxessizes for piles for upper row
        upperBoxWidth = img.getWidth()/numberOfBoxesInUppeRow;
        for(int i = 0; i<numberOfBoxesInUppeRow;i++){


            //Creating bow for drawpile
            if(i==0){
                drawPile[i]= upperBoxWidth *(i+1);
                System.out.println("drawPile: "+drawPile[i]);
            }else{
                foundationBoxes[i-1] = upperBoxWidth *(i+1);
            }
        }

        imageBoxesList.add(drawPile);
        imageBoxesList.add(foundationBoxes);
        imageBoxesList.add(pileBoxes);

        return imageBoxesList;
    }


    public ConvertState boxMapping(List<PreCard> precard, List<double[]> coordinateList, Image img){
        ConvertState currentState = new ConvertState();

        //Mapping the draw cart
        //It is located in the upper side of the image
        double upperHight = img.getHeight()/2;

        for(int i = 0; i<precard.size();i++){
            if(locate(precard.get(i),coordinateList.get(0)[0],upperHeight)){
                Card card = new Card(precard.get(i).getColor(),precard.get(i).getRank());
                currentState.setDraw(card);
            }
        }

        for(int i = 0; i<precard.size();i++){
            for (int j =0;j<coordinateList.get(1).length;j++) {
                if (locate(precard.get(i), coordinateList.get(1)[j], upperHeight)) {
                    //Create cardobject
                }
            }
        }

        for(int i = 0; i<precard.size();i++){
            for (int j =0;j<coordinateList.get(1).length;j++) {
                if (locate(precard.get(i), coordinateList.get(2)[j], upperHeight)) {
                    //Create cardobject
                }
            }
        }



    }

    private boolean locate(PreCard preCard, double endXCoordinate, double height){
        if(preCard.getUpperCoordinate().getX()<endXCoordinate || preCard.getUpperCoordinate().getY()<=height){
            return true;
        }
        return false;

    }
}
