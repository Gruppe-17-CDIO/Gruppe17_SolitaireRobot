package Converter;

import Converter.Util.SortingHelperClass;
import Converter.Util.Util;
import DarkNet_Connection.Darknet_Stub;
import DarkNet_Connection.DatknetConnection;
import DarkNet_Connection.I_Connection;
import Data.JsonDTO;
import Data.PreCard;
import com.google.gson.JsonArray;
import com.mashape.unirest.http.exceptions.UnirestException;
import dataObjects.TopCards;
import javafx.scene.image.Image;

import java.util.*;

/**
 * * @author Andreas B.G. Jensen
 */
public class Convertion implements I_ComputerVisionController {
    SortingHelperClass sorting = new SortingHelperClass();
    Util utility = new Util();
    BoxMapping mapping = new BoxMapping();
    I_Connection connection = new Darknet_Stub();
    ImageBoxes boxCreator = new ImageBoxes();
    Image img;



//TODO: Implement so that this method
    @Override
    public TopCards getSolitaireCards(Image img) {

    try {
        List<JsonDTO> returnImages = ConvertImage(img);
        //List<double[]> boxesArea = boxCreator.returnImgBoxes(img, returnImages);
        mapping.makeBoxMapping(returnImages, new TopCards());
        System.out.println("Test");
        //return boxCreator.boxMapping(returnImages,boxesArea,img);
        return null;

    }catch (Exception e){
        e.printStackTrace();
    }
    return null;
    }





    public List<JsonDTO> ConvertImage(Image img){
        JsonArray returnArray = null;
        try {
            return connection.Get_Image_Information(img);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
       // return utility.getPreCard(returnArray);
        return null;

    }






}
