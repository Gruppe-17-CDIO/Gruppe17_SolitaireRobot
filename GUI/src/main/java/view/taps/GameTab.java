package view.taps;

import controller.CompletionCallBack;
import controller.Controller;
import controller.I_Controller;
import controller.NextMoveCallBack;
import dataObjects.Move;
import dataObjects.SolitaireState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import model.WebCamSettings;
import view.MainGUI;
import view.components.FxUtil;
import view.components.SolitaireGridPane;
import view.components.TabStd;
import view.components.card.CardUI;
import view.components.card.SuitEnum;
import view.components.webCamImageView.WebCamImageView;
import view.components.webCamImageView.WebCamStateCallback;
import view.components.webCamManipulationButton.ManipulationStateCallback;
import view.components.webCamManipulationButton.WebCamManiButton;
import view.components.BoardGenerator;
import javafx.scene.control.ToggleButton;

import java.util.List;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Rasmus Sander Larsen
 */
public class GameTab extends TabStd {
    private BoardGenerator cg;
    private ToggleButton toggleButton;
    private Timer testTimer;

    //-------------------------- Fields --------------------------

    private final String TAG = getClass().getSimpleName();

    private WebCamManiButton webCamManiBtn;
    private WebCamImageView webCamImageView ;

    private SolitaireGridPane solitaireGridPane;

    HBox bottomButtonBox;
    private Button btnNextMove = new Button();
    private Button btnMoveCompleted = new Button();

    private I_Controller controller = new Controller();
    private boolean isGameStarted= false;

    //----------------------- Constructor -------------------------

    public GameTab () {
        super("Game", "Game", "The webcam should point at the solitaire that you have layed out and we will help you to a win!" +
                " Get ready to play!");

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
            addToContent(webCamManiBtn);
        }

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

        HBox webCamPane = new HBox();
        webCamPane.setMaxHeight(300);
        webCamPane.setAlignment(Pos.TOP_CENTER);

        webCamPane.setStyle("-fx-border-color: black; -fx-padding :5 ; -fx-border-width: 2");
        webCamPane.getChildren().add(webCamImageView);

        //addToContent(new Group(webCamPane));

        solitaireGridPane = new SolitaireGridPane();

        /*
        solitaireGridPane.createFullRow(1, 1, new CardUI("4", SuitEnum.Diamond));
        solitaireGridPane.createFullRow(2, 2, new CardUI("5", SuitEnum.Spade));
        solitaireGridPane.createFullRow(3, 3, new CardUI("10", SuitEnum.Diamond));
        solitaireGridPane.createFullRow(4, 4, new CardUI("K", SuitEnum.Heart));
        solitaireGridPane.createFullRow(5, 5, new CardUI("J", SuitEnum.Diamond));
        solitaireGridPane.createFullRow(6, 6, new CardUI("D", SuitEnum.Club));
        solitaireGridPane.createFullRow(7, 7, new CardUI("8", SuitEnum.Spade));
         */

        addToContent(solitaireGridPane);

        addNextMoveButton();
        addMoveCompletedButton();

        bottomButtonBox = FxUtil.hBox(true);
        bottomButtonBox.getChildren().addAll(btnNextMove, btnMoveCompleted);

        addAllToContent(bottomButtonBox);

        setUserData();

        // Runs test mode is active.
        testMode();
    }

    //------------------------ Properties -------------------------

    // region Properties


    // endregion

    //---------------------- Public Methods -----------------------


    //---------------------- Support Methods ----------------------

    private void addNextMoveButton() {
        btnNextMove.setText("Get Next Move");
        btnNextMove.setDisable(false);
        btnNextMove.setPrefWidth(150);
        btnNextMove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO: Her skal vores controller.Controller interface indsættes.
                // Game is running.
                if (isGameStarted) {
                    controller.getNextMove(webCamImageView.getImage(), new NextMoveCallBack() {
                        @Override
                        public void OnSuccess(List<Move> moves, Stack<SolitaireState> history, boolean won) {
                            try {
                                MainGUI.printToOutputAreaNewline(history.peek().getPrintFormat());
                                solitaireGridPane.ofSolitaireState(history.peek());
                                MainGUI.printToOutputAreaNewline("piles:" +history.peek().getPiles().get(2).toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            playTurn(moves, history);
                        }

                        @Override
                        public void OnFailure(String message, List<Move> moves, Stack<SolitaireState> history) {
                            MainGUI.printToOutputAreaNewline("Failure: " +message);
                            solitaireGridPane.ofSolitaireState(history.peek());
                        }

                        @Override
                        public void OnError(Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
                // Runs the initializing of the game.
                else {
                    controller.startNewGame(webCamImageView.getImage(), new NextMoveCallBack() {

                        @Override
                        public void OnSuccess(List<Move> moves, Stack<SolitaireState> history, boolean won) {
                            try {
                                MainGUI.printToOutputAreaNewline(history.peek().getPrintFormat());
                                solitaireGridPane.ofSolitaireState(history.peek());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            playTurn(moves, history);
                        }

                        @Override
                        public void OnFailure(String message, List<Move> moves, Stack<SolitaireState> history) {
                            MainGUI.printToOutputAreaNewline(message);
                            solitaireGridPane.ofSolitaireState(history.peek());
                        }

                        @Override
                        public void OnError(Exception e) {
                            e.printStackTrace();
                        }
                    });
                    isGameStarted = true;
                }

                MainGUI.printToOutputAreaNewline("controller.Controller Interface .getNextMove()");
                btnNextMove.setDisable(true);
                btnMoveCompleted.setDisable(false);
                btnMoveCompleted.requestFocus();
            }
        });
    }

    // Playing turn.
    private void playTurn(List<Move> moves, Stack<SolitaireState> history) {
        MainGUI.printToOutputAreaNewline("Options 0 is used.");
        controller.performMove(moves.get(0), new CompletionCallBack() {
            @Override
            public void OnSuccess(String status) {
                MainGUI.printToOutputAreaNewline("PlayTurn.OnSuccess - Status: " + status);
                solitaireGridPane.ofSolitaireState(history.peek());
                seeResults();
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

    // Print result
    private void seeResults() {
        controller.getNextMove(webCamImageView.getImage(), new NextMoveCallBack() {
            @Override
            public void OnSuccess(List<Move> moves, Stack<SolitaireState> history, boolean won) {
                try {
                    MainGUI.printToOutputAreaNewline(history.peek().getPrintFormat());
                    solitaireGridPane.ofSolitaireState(history.peek());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //playTurn(moves, history);
            }

            @Override
            public void OnFailure(String message, List<Move> moves, Stack<SolitaireState> history) {
                System.out.println(message);
            }

            @Override
            public void OnError(Exception e) {
                e.printStackTrace();
            }
        });
    }


    private void addMoveCompletedButton() {
        btnMoveCompleted.setText("Move is completed");
        btnMoveCompleted.setDisable(true);
        btnMoveCompleted.setPrefWidth(150);
        btnMoveCompleted.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO: Her skal vores controller.Controller interface indsættes.
                MainGUI.printToOutputAreaNewline("controller.Controller Interface \".etEllerAndet()\"");
                btnMoveCompleted.setDisable(true);
                btnNextMove.setDisable(false);
                btnNextMove.requestFocus();
            }
        });
    }

    private void setUserData () {
        TabUserData tabUserData = new TabUserData.Builder()
                .usedInClassName(TAG)
                .openRunnable(new Runnable() {
                    @Override
                    public void run() {
                        if (WebCamSettings.getInstance().setWebCamImageViewWithSetting(TAG,webCamImageView)){
                            webCamImageView.startRunning();
                            bottomButtonBox.setDisable(false);
                        } else {
                            if (webCamManiBtn.isWebCamManipulated()) {
                                webCamImageView.startAndSetManipulationImage(webCamManiBtn.imageOfFile());
                            } else {
                                webCamManiBtn.setManipulationState(false);
                            }
                            bottomButtonBox.setDisable(true);
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
                    //setupGame();
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



            /*


            toggleButton.setVisible(true);

            String[] testValues = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "test"};
            String[] testSuits = {"club", "diamond", "heart", "spade", "test"};

            toggleButton.setOnAction(event -> {
                if (toggleButton.isSelected()) {

                    // Adds random cards to the board each second
                    GameTab.this.testTimer = new Timer(); // Access the outer class variable from this inner class
                    testTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            cg.clearHighligt();

                            int randomPlacement = (int)(Math.random() * 16 - 2); // Tests range -1 to 13
                            int randomCardValue = (int)(Math.random() * 14); // Tests range 0 to 13
                            int randomCardSuit = (int)(Math.random() * 5); // Tests range 0 to 4

                            System.out.println("[INFO] Automatically adding random card: " +
                                    testValues[randomCardValue] + " of " + testSuits[randomCardSuit] +
                                    " at placement: " + randomPlacement);

                            cg.addCard(randomPlacement, testValues[randomCardValue], testSuits[randomCardSuit]);

                            // Random movement highligt
                            int randomPlacement2 = (int)(Math.random() * 16 - 2);
                            int randomPlacement3 = (int)(Math.random() * 16 - 2);
                            System.out.println("[INFO] Highlighting placements: " +
                                    randomPlacement2 + " to " + randomPlacement3);
                            cg.highlightMove(randomPlacement2, randomPlacement3);
                        }
                    }, 0, 250);

                } else {
                    testTimer.cancel();
                }
            });

             */
        }
    }
}
