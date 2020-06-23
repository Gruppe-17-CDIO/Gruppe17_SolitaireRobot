package DarkNet_Connection;

import Data.JsonDTO;
import Exceptions.DarknetConnectionException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONArray;
import org.apache.http.NoHttpResponseException;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;


/**
 * * @author Andreas B.G. Jensen
 */
public class DatknetConnection implements I_Connection {
    final int reconnectTry = 3;
    int connectionTryes = 0;
    /*
    Sending a post request to python server and return the coordinates of the image recognition.
     */
    @Override
    public List<JsonDTO> Get_Image_Information(Image img) throws DarknetConnectionException {
        try {
            byte[] imageByteArray = convertImageToByteArray(img);
            HttpResponse<String> res = makePOSTRequest(imageByteArray);
            JSONArray jsonArray = new JSONArray(res.getBody());
            System.out.println(jsonArray.toString());

                // JSON array

                // convert JSON array to Java List
                List<JsonDTO> jsonDTOList = new ObjectMapper().readValue(res.getBody(), new TypeReference<List<JsonDTO>>() {
                });

                return jsonDTOList;

        } catch (Exception e) {

            e.printStackTrace();
            throw new DarknetConnectionException(e.getMessage());
        }
    }



    /*
    Author: Andreas Jensen
    Converts an JavaFX Image to a bytearray.
     */
    public byte[] convertImageToByteArray(Image img) throws IOException {
        BufferedImage bImage = SwingFXUtils.fromFXImage(img, null);

        // Remove alpha-channel from buffered image:
        //This is nessesary because there apparently is a bug in ImageIO liabary
        //This codesnippet is from: https://stackoverflow.com/questions/4386446/issue-using-imageio-write-jpg-file-pink-background
        BufferedImage imageRGB = new BufferedImage(bImage.getWidth(), bImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for (int x = 0; x < bImage.getWidth(); x++) {
            for (int y = 0; y < bImage.getHeight(); y++) {
                imageRGB.setRGB(x, y, bImage.getRGB(x, y));
            }
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        System.out.println(os.toByteArray().length);
        ImageIO.write(imageRGB, "jpg", os);// Passing: ​(RenderedImage im, String formatName, OutputStream output)
        return os.toByteArray();
    }

    private HttpResponse<String> makePOSTRequest(byte[] imageByteArray) throws DarknetConnectionException{
        try {

            HttpResponse<String> res = Unirest.post("http://212.237.130.109:6969/detect/hello.png")
                    .header("Content-Type", "image/png")
                    .body(imageByteArray)
                    .asString();

            return res;
        }catch (Exception e){
            if(reconnectTry<connectionTryes) {
                return makePOSTRequest(imageByteArray);
            }
            throw new DarknetConnectionException("Three tryes failed to connect to the Darknet REST endpoint\n" +
                    "Please contact Gruppe 17.\nException message: " +e.getMessage());
        }

    }

}
