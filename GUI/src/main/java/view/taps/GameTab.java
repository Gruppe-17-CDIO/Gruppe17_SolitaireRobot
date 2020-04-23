package view.taps;

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


/**
 * @author Rasmus Sander Larsen
 */
public class GameTab extends TabStd {

    //-------------------------- Fields --------------------------

    private final String TAG = getClass().getSimpleName();

    private WebCamManiButton webCamManiBtn;
    private WebCamImageView webCamImageView ;

    HBox bottomButtonBox;
    private Button btnNextMove;
    private Button btnMoveDone;


    //----------------------- Constructor -------------------------

    public GameTab () {
        super("Game", "Title", "Desc");

        webCamImageView = new WebCamImageView();
        // If Testing is active adds the options to manipulate the view and find the image to insert.
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

        addToContent( new Group(webCamPane));

        SolitaireGridPane solitaireGridPane = new SolitaireGridPane();

        solitaireGridPane.createFullRow(1, 1, new CardUI("4", SuitEnum.Diamond));
        solitaireGridPane.createFullRow(2, 2, new CardUI("5", SuitEnum.Spade));
        solitaireGridPane.createFullRow(3, 3, new CardUI("10", SuitEnum.Diamond));
        solitaireGridPane.createFullRow(4, 4, new CardUI("K", SuitEnum.Heart));
        solitaireGridPane.createFullRow(5, 5, new CardUI("J", SuitEnum.Diamond));
        solitaireGridPane.createFullRow(6, 6, new CardUI("D", SuitEnum.Club));
        solitaireGridPane.createFullRow(7, 7, new CardUI("8", SuitEnum.Spade));

        //addToContent(solitaireGridPane);

        btnNextMove = new Button("Get Next Move");
        btnNextMove.setDisable(false);
        btnNextMove.setPrefWidth(150);
        btnNextMove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO: Her skal vores Controller interface indsættes.
                MainGUI.printToOutputAreaNewline("Controller Interface .getNextMove()");
                btnNextMove.setDisable(true);
                btnMoveDone.setDisable(false);
            }
        });

        btnMoveDone = new Button("Move is completed");
        btnMoveDone.setDisable(true);
        btnMoveDone.setPrefWidth(150);
        btnMoveDone.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO: Her skal vores Controller interface indsættes.
                MainGUI.printToOutputAreaNewline("Controller Interface \".etEllerAndet()\"");
                btnMoveDone.setDisable(true);
                btnNextMove.setDisable(false);
            }
        });

        bottomButtonBox = FxUtil.hBox(true);
        bottomButtonBox.getChildren().addAll(btnNextMove,btnMoveDone);

        addAllToContent(bottomButtonBox);

        setUserData();
    }

    //------------------------ Properties -------------------------

    // region Properties


    // endregion

    //---------------------- Public Methods -----------------------


    //---------------------- Support Methods ----------------------

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

    }
}
