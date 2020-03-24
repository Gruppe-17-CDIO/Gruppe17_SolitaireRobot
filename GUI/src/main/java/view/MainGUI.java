package view;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.components.OutputTextArea;
import view.taps.TabPane;

/**
 * @author Rasmus Sander Larsen
 */
public class MainGUI extends Application {

    // -------------------------- Fields --------------------------

    private final String WINDOW_TITLE = "7 Solitaire";
    private final String WINDOW_ICON_PATH = "/game_icon.png";

    private static OutputTextArea outputTextArea;

    public static boolean isTesting = true;

    // ----------------------- Constructor -------------------------

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.centerOnScreen();

        // Sets Window icon.
        primaryStage.getIcons().add(
               new Image(getClass().getResourceAsStream(WINDOW_ICON_PATH)));
        primaryStage.show();

        outputTextArea = new OutputTextArea();
        printTestStatus();
        TabPane tabPane = new TabPane();

        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.HORIZONTAL);
        splitPane.setDividerPositions(500);
        splitPane.getItems().addAll(tabPane, outputTextArea);

        outputTextArea.setMinWidth(500);
        outputTextArea.setEditable(false);
        tabPane.setMinWidth(500);
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Runnable updater = (Runnable) newValue.getUserData();
                if (updater != null) {
                    updater.run();
                }
            }
        });

        primaryStage.setScene(new Scene(splitPane,1000,600));
    }

    //------------------------ Properties -------------------------

    // region Properties


    // endregion


    //---------------------- Public Methods -----------------------

    public static void printToOutputAreaNewline (String text) {
        outputTextArea.appendTextNewline(text);
    }
    public static void printSuccessToOutputArea (String text) {outputTextArea.appendTextNewline("Success:\n" +text);}
    public static void printFailureToOutputArea (String text) {outputTextArea.appendTextNewline("Failure:\n" +text);}
    public static void printErrorToOutputArea (String text) {outputTextArea.appendTextNewline("Error:\n" +text);}
    public static void printToOutputArea (String text) {
        outputTextArea.appendText(text);
    }
    public static void printDivider() {
        outputTextArea.printDivider();
    }

    //---------------------- Support Methods ----------------------

    private void printTestStatus () {
        if (isTesting) {
            printToOutputAreaNewline("**** Testing Mode: ACTIVE ****");
        }
    }
}
