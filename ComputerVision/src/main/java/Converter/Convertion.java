package Converter;

import Converter.Util.Util;
import DarkNet_Connection.Darknet_Stub;
import DarkNet_Connection.I_Connection;
import Data.PreCard;
import com.google.gson.JsonArray;
import com.mashape.unirest.http.exceptions.UnirestException;
import dataObjects.Card;
import dataObjects.ConvertState;
import javafx.scene.image.Image;

import java.util.List;

public class Convertion implements I_ComputerVisionController {
    Util utility = new Util();
    I_Connection connection = new Darknet_Stub();
    ImageBoxes boxCreator = new ImageBoxes();
    Image img;




    @Override
    public ConvertState getSolitaireCards(Image img) {

        List<PreCard> returnImages = ConvertImage(img);
        List<double[]> boxesArea = boxCreator.calibrateImgBoxes(img);


        return boxCreator.boxMapping(returnImages,boxesArea,img);
    }





    public List<PreCard> ConvertImage(Image img){
        JsonArray returnArray = null;
        try {
            returnArray = connection.Get_Image_Information(img);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return utility.getPreCard(returnArray);

    }
}
