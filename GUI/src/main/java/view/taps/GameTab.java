package view.taps;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import model.WebCamSettings;
import view.components.SolitaireGridPane;
import view.components.TabStd;
import view.components.card.CardUI;
import view.components.card.CardUIGridPane;
import view.components.card.SuitEnum;
import view.components.webCamImageView.WebCamImageView;
import view.components.webCamImageView.WebCamStateCallback;


/**
 * @author Rasmus Sander Larsen
 */
public class GameTab extends TabStd {

    //-------------------------- Fields --------------------------

    private final String TAG = getClass().getSimpleName();

    private WebCamImageView webCamImageView ;

    //----------------------- Constructor -------------------------

    public GameTab () {
        super("Game", "Title", "Desc");

        webCamImageView = new WebCamImageView();
        webCamImageView.setStateCallback(new WebCamStateCallback() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onStopped() {

            }

            @Override
            public void onDisposed() {

            }
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
        solitaireGridPane.insetCardInColumn(new CardUIGridPane("2", SuitEnum.Club),1);
        solitaireGridPane.insetCardInColumn(new CardUIGridPane("3", SuitEnum.Spade),1);
        solitaireGridPane.insetCardInColumn(new CardUIGridPane("4", SuitEnum.Diamond),1);
        solitaireGridPane.insetCardInColumn(new CardUI("5", SuitEnum.Spade),2);
        solitaireGridPane.insetCardInColumn(new CardUI("10", SuitEnum.Diamond),3);
        solitaireGridPane.insetCardInColumn(new CardUI("K", SuitEnum.Heart),4);
        solitaireGridPane.insetCardInColumn(new CardUI("J", SuitEnum.Diamond),5);
        solitaireGridPane.insetCardInColumn(new CardUI("D", SuitEnum.Club),6);
        solitaireGridPane.insetCardInColumn(new CardUI("8", SuitEnum.Spade),7);

        //solitaireGridPane.insertCardInCollectionDeck(new CardUI("A", SuitEnum.Heart));
        solitaireGridPane.insertCardInCollectionDeck(new CardUI("2", SuitEnum.Diamond));
        solitaireGridPane.insertCardInCollectionDeck(new CardUI("A", SuitEnum.Club));
        solitaireGridPane.insertCardInCollectionDeck(new CardUI("3", SuitEnum.Spade));

        addToContent(solitaireGridPane);

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
                        System.out.println(TAG + " // Use Settings and StartImageBinding");
                        if (WebCamSettings.getInstance().isSettingsSet()) {
                            WebCamSettings.getInstance().setWebCamImageViewWithSetting(webCamImageView);
                            webCamImageView.startRunning();
                        }
                    }
                })
              .closeRunnable(new Runnable() {
                  @Override
                  public void run() {
                      System.out.println(TAG + " // StopWebCamera");
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
