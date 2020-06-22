package view.taps;

import controller.CompletionCallBack;
import controller.Controller;
import controller.I_Controller;
import controller.NextMoveCallBack;
import dataObjects.GlobalEnums;
import dataObjects.Move;
import dataObjects.SolitaireState;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import model.WebCamSettings;
import view.MainGUI;
import view.components.FxUtil;
import view.components.SolitaireGridPane;
import view.components.TabStd;
import view.components.webCamImageView.WebCamImageView;
import view.components.webCamImageView.WebCamStateCallback;
import view.components.webCamManipulationButton.ManipulationStateCallback;
import view.components.webCamManipulationButton.WebCamManiButton;

import java.util.List;
import java.util.Stack;

/**
 * @author Rasmus Sander Larsen
 */
public class GameTab extends TabStd {

    //-------------------------- Fields --------------------------

    private final int MAX_WIDTH = 1000;
    private final int MAX_HEIGHT = 400;
    private final int VGAP = 10;

    private final String TAG = getClass().getSimpleName();

    private WebCamManiButton webCamManiBtn;
    private WebCamImageView webCamImageView;

    private final BorderPane mainPane;

    private GridPane gridPane;
    private VBox bottomBox;

    private final SolitaireGridPane solitaireGridPane;

    private final VBox btnBox = FxUtil.vBox(true);
    private final Text moveText = new Text();
    private final Button btnNextMove = new Button();
    private Button btnStartNewGame = new Button();
    private final ProgressIndicator progressIndicator = new ProgressIndicator(-1.0);

    private final I_Controller controller = new Controller();

    //----------------------- Constructor -------------------------

    public GameTab() {
        super("Game", "Game", "The webcam should point at the solitaire that you have layed out and we will help you to a win!" +
                " Get ready to play!");

        // Setup the GridPane with the right setting for it purpose.
        setupGridPane();

        // Creates and adds the WebCamView to the GridPane.
        createAndAddWebCamToGridPane();

        // Creates and add the SolitaireGridPane that is the GUI representation of the layed out cards.
        solitaireGridPane = new SolitaireGridPane();
        gridPane.add(solitaireGridPane,1,0);

        // Add the GridPane to the Tab with a little bit for spacing above.
        addToContent(FxUtil.emptySpace(Orientation.VERTICAL,5));

        createBottomMenu();

        mainPane = new BorderPane();

        mainPane.setCenter(gridPane);
        mainPane.setBottom(bottomBox);

        addToContent(mainPane);

        setUserData();

        // Runs test mode is active.
        testMode();
    }

    //------------------------ Properties -------------------------

    // region Properties


    // endregion

    //---------------------- Public Methods -----------------------


    //---------------------- Support Methods ----------------------

    private void setupGridPane ()  {
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setMaxSize(MAX_WIDTH,MAX_HEIGHT);
        gridPane.setPrefSize(MAX_WIDTH,MAX_HEIGHT);
        gridPane.setVgap(VGAP);
        // Set ColumnConstraints to 1/7 for each of the 7 columns.
        for(int i = 0; i < 2 ; i++) {
            ColumnConstraints tempColumnConstraints = new ColumnConstraints();
            tempColumnConstraints.setPercentWidth(50);
            gridPane.getColumnConstraints().add(tempColumnConstraints);
        }

        // Set RowConstraints Vertical Alignment to "TOP" for both rows.
        RowConstraints tempRowConstraints = new RowConstraints();
        tempRowConstraints.setValignment(VPos.TOP);
        gridPane.getRowConstraints().add(tempRowConstraints);
    }

    private void createAndAddWebCamToGridPane() {
        webCamImageView = new WebCamImageView();

        // region If Testing is active adds the options to manipulate the view and find the image to insert.
        webCamManiBtn = new WebCamManiButton(new ManipulationStateCallback() {
            @Override
            public void startManipulateAction() {
                webCamImageView.startAndSetManipulationImage(webCamManiBtn.imageOfFile());
            }

            @Override
            public void stopManipulateAction() {
                webCamImageView.stopManipulationOfWebCam();
            }
        });

        if (MainGUI.isTesting) {
            //addToContent(webCamManiBtn);
        }
        addToContent(webCamManiBtn);

        // endregion

        webCamImageView.setStateCallback(new WebCamStateCallback() {
            @Override
            public void onStarted() { }

            @Override
            public void onStopped() { }

            @Override
            public void onDisposed() { }
        });
        webCamImageView.setFitWidth(450);
        webCamImageView.setFitHeight(450d/16*9);

        // Creates the framed view for the webcam
        HBox webCamPane = new HBox();
        webCamPane.setMaxHeight(300);
        webCamPane.setAlignment(Pos.TOP_CENTER);

        webCamPane.setStyle("-fx-border-color: black; -fx-padding :5 ; -fx-border-width: 2");
        webCamPane.getChildren().add(webCamImageView);

        // NEEDS to be in Group.
        // Adds the left side for GameTab
        gridPane.add(new Group(webCamPane),0,0);
    }

    private void createBottomMenu() {
        bottomBox = new VBox();
        bottomBox.setMaxHeight(100);
        bottomBox.setAlignment(Pos.BOTTOM_CENTER);

        btnNextMove.setText("Get Next Move");
        btnNextMove.setDisable(false);
        btnNextMove.setPrefWidth(150);
        btnNextMove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Creates and runs a background Thread to do the getNextMove logic.
                runNextMoveTask();
            }
        });

        progressIndicator.setVisible(false);
        progressIndicator.setMinSize(20,20);
        progressIndicator.setMaxSize(20,20);

        moveText.setFont(FxUtil.textFont());

        btnStartNewGame = new Button("Start new game");
        btnStartNewGame.setPrefWidth(150);
        btnStartNewGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.startNewGame(webCamImageView.getImage(), defaultNextMoveCallback());
            }
        });

        bottomBox.getChildren().addAll(moveText, btnNextMove, progressIndicator, btnStartNewGame);
    }

    private NextMoveCallBack defaultNextMoveCallback () {
        return new NextMoveCallBack() {
            @Override
            public void OnSuccess(Move move, SolitaireState state, GlobalEnums.GameProgress gameProgress) {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        solitaireGridPane.ofSolitaireState(state);
                        switch (gameProgress) {
                            case PLAYING:
                                MainGUI.printToOutputAreaNewline(move.toString());
                                moveText.setText(move.toString());
                                solitaireGridPane.highlightOfMove(move);
                                Platform.runLater(postPlayingControlCall());
                                break;
                            case WON:
                                MainGUI.printToOutputAreaNewline("YOU HAVE WON");
                                moveText.setText("YOU HAVE WON!");
                                btnNextMove.setDisable(true);
                                onShotOfGifConfetti();
                                Platform.runLater(postWonControlCall());
                                break;
                            case LOST:
                                MainGUI.printToOutputAreaNewline("YOU HAVE LOST");
                                moveText.setText("YOU HAVE LOST!");
                                btnNextMove.setDisable(true);
                                Platform.runLater(postLostControlCall());
                                break;
                            default:
                                break;
                        }
                    }
                });


            }

            @Override
            public void OnFailure(String message, List<Move> moves, Stack<SolitaireState> history) {
                MainGUI.printToOutputAreaNewline("Failure: " +message);
                solitaireGridPane.ofSolitaireState(history.peek());
                Platform.runLater(postFailedControlCall());
            }

            @Override
            public void OnError(Exception e) {
                e.printStackTrace();
                moveText.setText(e.getMessage());
                Platform.runLater(postErrorControlCall());
            }
        };
    }

    private void runNextMoveTask () {

        Task<Integer> nextMoveTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                Platform.runLater(preControlCall());

                controller.getNextMove(webCamImageView.getImage(),defaultNextMoveCallback());

                btnNextMove.requestFocus();
                return null;
            }
        };

        Thread nextMoveThread = new Thread(nextMoveTask);
        nextMoveThread.setDaemon(true);
        nextMoveThread.start();
    }

    private Runnable preControlCall () {
        return () -> {
            progressIndicator.setVisible(true);
            btnNextMove.setDisable(true);
            btnStartNewGame.setDisable(true);
        };
    }

    private Runnable postPlayingControlCall () {
        return () -> {
            progressIndicator.setVisible(false);
            btnNextMove.setDisable(false);
            btnStartNewGame.setDisable(false);
            btnNextMove.requestFocus();
        };
    }
    private Runnable postWonControlCall () {
        return () -> {
            progressIndicator.setVisible(false);
            btnNextMove.setDisable(true);
            btnStartNewGame.setDisable(false);
            btnStartNewGame.requestFocus();
        };
    }
    private Runnable postLostControlCall () {
        return () -> {
            progressIndicator.setVisible(false);
            btnNextMove.setDisable(true);
            btnStartNewGame.setDisable(false);
            //btnStartNewGame.requestFocus();
        };
    }

    private Runnable postFailedControlCall () {
        return () -> {
            progressIndicator.setVisible(false);
            btnNextMove.setDisable(false);
            btnStartNewGame.setDisable(false);
            btnNextMove.requestFocus();
        };
    }
    private Runnable postErrorControlCall () {
        return () -> {
            progressIndicator.setVisible(false);
            btnNextMove.setDisable(false);
            btnStartNewGame.setDisable(false);
            btnNextMove.requestFocus();
        };
    }

    private void setUserData () {
        TabUserData tabUserData = new TabUserData.Builder()
                .usedInClassName(TAG)
                .openRunnable(new Runnable() {
                    @Override
                    public void run() {
                        if (WebCamSettings.getInstance().setWebCamImageViewWithSetting(TAG,webCamImageView)){
                            webCamImageView.startRunning();
                            btnNextMove.setDisable(false);
                            btnStartNewGame.setDisable(false);
                        } else {
                            if (webCamManiBtn.isWebCamManipulated()) {
                                webCamImageView.startAndSetManipulationImage(webCamManiBtn.imageOfFile());
                            } else {
                                webCamManiBtn.setManipulationState(false);
                            }
                            btnNextMove.setDisable(true);
                            btnStartNewGame.setDisable(true);
                        }
                    }
                })
                .closeRunnable(new Runnable() {
                  @Override
                  public void run() {
                      webCamImageView.stopRunning();
                  }
                })
                .build();

        setUserData(tabUserData);
    }

    @Override
    protected void testMode() {

        if (MainGUI.isTesting) {
            // Controller TESTING mode.
            controller.setTestModeOn(true, new CompletionCallBack() {
                @Override
                public void OnSuccess(String status) {
                    MainGUI.printToOutputAreaNewline(status);
                }

                @Override
                public void OnFailure(String message) {
                    MainGUI.printToOutputAreaNewline(message);
                }

                @Override
                public void OnError(Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
