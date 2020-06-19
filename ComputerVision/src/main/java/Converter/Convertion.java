package Converter;

import Converter.Util.Sorting.SortingHelperClass;
import DarkNet_Connection.Darknet_Stub;
import DarkNet_Connection.DatknetConnection;
import DarkNet_Connection.I_Connection;
import Data.BufferElement;
import Data.JsonDTO;

import computerVision.I_ComputerVisionController;
import dataObjects.TopCards;
import javafx.scene.image.Image;
import kong.unirest.UnirestException;

import java.util.*;

/**
 * * @author Andreas B.G. Jensen
 */
public class Convertion implements I_ComputerVisionController {

    boolean test = true;
    SortingHelperClass sorting;
    I_Connection connection;
    BufferElement buffer;
    BoxMapping mapper;

    public Convertion(){
        if(test){
            connection = new Darknet_Stub();
        }
        else{
            connection = new DatknetConnection();
        }
        sorting = new SortingHelperClass();

    }



//TODO: Implement so that this method
    @Override
    public TopCards getSolitaireCards(Image img) {

    try {
        List<JsonDTO> returnImages = getOutputDarknet(img);
        buffer = new BufferElement(returnImages,sorting);
        mapper = new BoxMapping(buffer,sorting);

        //List<double[]> boxesArea = boxCreator.returnImgBoxes(img, returnImages);
       // mapping.makeBoxMapping(returnImages, new TopCards());
        System.out.println("Test");
        //return boxCreator.boxMapping(returnImages,boxesArea,img);
        return mapper.makeBoxMapping(returnImages);

    }catch (Exception e){
        e.printStackTrace();
    }
    return null;
    }





    public List<JsonDTO> getOutputDarknet(Image img){

        try {
            return connection.Get_Image_Information(img);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        //Throw an exception eif the connection fails
        return null;

    }






}
