package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.tabs.OutputTab;
import view.tabs.TabPane;

/**
 * @author Rasmus Sander Larsen
 */
public class MainGUI extends Application {

    // -------------------------- Fields --------------------------

    private final String WINDOW_TITLE = "7 Solitaire";
    private final String WINDOW_ICON_PATH = "/game_icon.png";

    public final static int SCREEN_WIDTH = 1000;
    public final static int SCREEN_HEIGHT = 700;

    private static TabPane tabPane;

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

        tabPane = new TabPane();

        printTestStatus();

        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(tabPane);

        primaryStage.setScene(new Scene(mainPane,SCREEN_WIDTH,SCREEN_HEIGHT));
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

                tabPane.appendTextNewlineToOutput(text);
            }
        });

    }
    public static void printSuccessToOutputArea (String text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tabPane.appendTextNewlineToOutput("Success:\n" + text);
            }
        });
    }
    public static void printFailureToOutputArea (String text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tabPane.appendTextNewlineToOutput("Failure:\n" +text);
            }
        });
    }
    public static void printErrorToOutputArea (String text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tabPane.appendTextNewlineToOutput("Error:\n" +text);
            }
        });
    }

    public static void printToOutputArea (String text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tabPane.appendTextToOutput(text);
            }
        });
    }
    public static void printDivider() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tabPane.printDivider();
            }
        });

    }

    //---------------------- Support Methods ----------------------

    private void printTestStatus () {
        if (isTesting)
        printToOutputAreaNewline("**** Testing Mode: ACTIVE ****");
    }
}
