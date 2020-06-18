package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.components.OutputTextArea;
import view.components.TopMenuBar;
import view.taps.TabPane;

/**
 * @author Rasmus Sander Larsen
 */
public class MainGUI extends Application {

    // -------------------------- Fields --------------------------

    private final String WINDOW_TITLE = "7 Solitaire";
    private final String WINDOW_ICON_PATH = "/game_icon.png";

    public final static int sceneWidth = 1000;
    public final static int sceneHeight = 600;

    private static OutputTextArea outputTextArea;

    public static boolean isTesting = false;

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
        //tabPane.getSelectionModel().selectLast();
        //tabPane.getSelectionModel().clearAndSelect(0);

        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.HORIZONTAL);
        splitPane.setDividerPositions(500);
        splitPane.getItems().addAll(tabPane, outputTextArea);

        outputTextArea.setMinWidth(500);
        outputTextArea.setEditable(false);
        tabPane.setMinWidth(500);

        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(splitPane);
        if (isTesting){
            mainPane.setTop(new TopMenuBar());
        }

        primaryStage.setScene(new Scene(mainPane,1000,600));
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        for (Thread thread :  Thread.getAllStackTraces().keySet()){
            if (thread.getName().equals("ImageBindingThread")) {
                thread.interrupt();
            }
            MainGUI.printToOutputAreaNewline(thread.getId() +"@" +thread.getName() +" is alive");
        }
        //TODO: Handle shutdown
    }

    //------------------------ Properties -------------------------

    // region Properties


    // endregion


    //---------------------- Public Methods -----------------------

    public static void printToOutputAreaNewline (String text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                outputTextArea.appendTextNewline(text);
            }
        });

    }
    public static void printSuccessToOutputArea (String text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                outputTextArea.appendTextNewline("Success:\n" + text);
            }
        });
    }
    public static void printFailureToOutputArea (String text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                outputTextArea.appendTextNewline("Failure:\n" +text);
            }
        });
    }
    public static void printErrorToOutputArea (String text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                outputTextArea.appendTextNewline("Error:\n" +text);
            }
        });
    }

    public static void printToOutputArea (String text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                outputTextArea.appendText(text);
            }
        });
    }
    public static void printDivider() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                outputTextArea.printDivider();
            }
        });

    }

    //---------------------- Support Methods ----------------------

    private void printTestStatus () {
        if (isTesting)
        printToOutputAreaNewline("**** Testing Mode: ACTIVE ****");
    }
}
