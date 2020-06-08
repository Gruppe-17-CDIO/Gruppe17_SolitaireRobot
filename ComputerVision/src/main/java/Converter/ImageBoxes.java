package Converter;

import javafx.scene.image.Image;

public class ImageBoxes {
    double upperHeight;
    double lowerHeight;
    double boxWidth;
    double foundationBoxes[] = new double[4];
    double drawPile[] = new double[1];
    int numberOfBoxes = 7; //The number of boxes the width should be divided with.
    double pileBoxes[] = new double[numberOfBoxes];
    double
    /*
   Divide the image into sections. Ex heigth/2  and length/7.
   _____________________________
   |   |   |   |   |   |   |   |
   |   |   |   |   |   |   |   |
   -----------------------------
   |   |   |   |   |   |   |   |
   |   |   |   |   |   |   |   |
   -----------------------------
   These boundary boxes will be used to to check if the incoming coordinates (From darknet) are located in one of the boxes.

    */
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
}
