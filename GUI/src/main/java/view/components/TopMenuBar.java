package view.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.WebCamSettings;
import view.MainGUI;
import view.components.webCamManipulationButton.ManipulationStateCallback;

import java.io.File;

/**
 * @author Rasmus Sander Larsen
 */
public class TopMenuBar extends MenuBar {

    //-------------------------- Fields --------------------------

    Menu mGeneralFunctions;
    MenuItem miPrintThreads;
    MenuItem miPrintWebCamSetting;

    Menu mGameFunctions;
    MenuItem miManipulateImage;

    //----------------------- Constructor -------------------------

    public TopMenuBar () {
        mGeneralFunctions = new Menu("Test Functions");
        miPrintThreads = new MenuItem("Print Alive Thread");
        miPrintThreads.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MainGUI.printDivider();
                for (Thread thread :  Thread.getAllStackTraces().keySet()){
                    MainGUI.printToOutputAreaNewline(thread.getId() +"@" +thread.getName() +" is alive");
                }
                MainGUI.printDivider();
            }
        });

        miPrintWebCamSetting = new MenuItem(" Print WebCamSettings");
        miPrintWebCamSetting.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               MainGUI.printToOutputAreaNewline(WebCamSettings.getInstance().toString());
            }
        });

        mGameFunctions = new Menu("Game Functions");
        miManipulateImage = new WebCamManiMenuItem("Manipulate Image");
        miManipulateImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });



        mGeneralFunctions.getItems().addAll(
                miPrintThreads,
                miPrintWebCamSetting);

        getMenus().add(mGeneralFunctions);
    }

    //------------------------ Properties -------------------------

    // region Properties


    // endregion

    //---------------------- Public Methods -----------------------


    //---------------------- Support Methods ----------------------    


}
