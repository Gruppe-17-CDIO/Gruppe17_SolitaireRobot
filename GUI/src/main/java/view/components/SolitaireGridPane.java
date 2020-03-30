package view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import view.MainGUI;
import view.components.card.CardUI;
import view.components.card.SuitEnum;

/**
 * @author Rasmus Sander Larsen
 */
public class SolitaireGridPane extends GridPane {

    //-------------------------- Fields --------------------------

    private final String CARD_OUTLINE = ";-fx-border-color: grey; -fx-border-width: 1; -fx-border-radius: 5";

    private final int MAX_WIDTH = 400;
    private final int MAX_HEIGHT = 400;
    private final int HGAP = 3;
    private final int VGAP = 10;

    private CardUI deckCardUI;
    private StackPane heartCollection_SP;
    private StackPane diamondCollection_SP;
    private StackPane clubCollection_SP;
    private StackPane spadeCollection_SP;

    //----------------------- Constructor -------------------------

    public SolitaireGridPane () {
        super();
        setupLayout();
    }


    //------------------------ Properties -------------------------

    // region Properties


    // endregion

    //---------------------- Public Methods -----------------------

    public void insetCardInColumn(CardUI cardUI, int columnIndex) {
        add(cardUI, columnIndex,1);
    }

    public void insertCardInCollectionDeck (CardUI cardUI) {
        switch (cardUI.getSuit()) {
            case Heart:
                heartCollection_SP.getChildren().add(cardUI);
                break;
            case Diamond:
                add(cardUI,4,0);
                break;
            case Club:
                add(cardUI,5,0);
                break;
            case Spade:
                add(cardUI,6,0);
                break;
            default:
                break;
        }
    }

    public void insertCardInCollectionDeckWithRemove (CardUI cardUI) {
        switch (cardUI.getSuit()) {
            case Heart:
                heartCollection_SP.getChildren().removeAll(heartCollection_SP.getChildren());
                heartCollection_SP.getChildren().add(cardUI);
                break;
            case Diamond:
                diamondCollection_SP.getChildren().removeAll(diamondCollection_SP.getChildren());
                diamondCollection_SP.getChildren().add(cardUI);
                break;
            case Club:
                clubCollection_SP.getChildren().removeAll(clubCollection_SP.getChildren());
                clubCollection_SP.getChildren().add(cardUI);
                break;
            case Spade:
                spadeCollection_SP.getChildren().removeAll(spadeCollection_SP.getChildren());
                spadeCollection_SP.getChildren().add(cardUI);
                break;
            default:
                break;
        }
    }

    public void showDeck (boolean isShowing) {
        deckCardUI.setVisible(isShowing);
    }


    //---------------------- Support Methods ----------------------    

    private void setupLayout() {
        setAlignment(Pos.TOP_CENTER);
        setMaxSize(MAX_WIDTH,MAX_HEIGHT);
        setPrefSize(MAX_WIDTH,MAX_HEIGHT);
        setHgap(HGAP);
        setVgap(VGAP);
        // Set ColumnConstraints to 1/7 for each of the 7 columns
        for(int i = 0; i < 7 ; i++) {
            ColumnConstraints tempColumnConstraints = new ColumnConstraints();
            tempColumnConstraints.setPercentWidth(50);
            getColumnConstraints().add(tempColumnConstraints);
        }

        deckCardUI = new CardUI();
        deckCardUI.showBackside();
        add(deckCardUI,0,0);

        heartCollection_SP = defaultCollectionStackPane(SuitEnum.Heart);
        diamondCollection_SP = defaultCollectionStackPane(SuitEnum.Diamond);
        clubCollection_SP = defaultCollectionStackPane(SuitEnum.Club);
        spadeCollection_SP = defaultCollectionStackPane(SuitEnum.Spade);

        add(heartCollection_SP,3,0);
        add(diamondCollection_SP,4,0);
        add(clubCollection_SP,5,0);
        add(spadeCollection_SP,6,0);

        //TODO Method just created to check insertCardInCollectionWithRemove()
        setOnMouseClicked(event -> {
            int heartCollectionSizeBefore = heartCollection_SP.getChildren().size();
            MainGUI.printToOutputAreaNewline("HeartCollection Size Before: " + heartCollectionSizeBefore);
            if (heartCollectionSizeBefore == 0) {
                insertCardInCollectionDeckWithRemove(new CardUI("1", SuitEnum.Heart));
            } else {
                int topCardUIValue = Integer.parseInt(((CardUI) heartCollection_SP.getChildren().get(0)).getValue());
                insertCardInCollectionDeckWithRemove(new CardUI(String.valueOf(++topCardUIValue),SuitEnum.Heart));
            }
            MainGUI.printToOutputAreaNewline("HeartCollection Size Before: " + heartCollection_SP.getChildren().size());
            MainGUI.printDivider();
        });
    }

    private StackPane defaultCollectionStackPane(SuitEnum suitEnum) {
        StackPane collectionStackPane = new StackPane();
        collectionStackPane.setStyle(CARD_OUTLINE);
        collectionStackPane.setMinSize(60,75);
        collectionStackPane.setMaxSize(60,75);
        collectionStackPane.setAlignment(Pos.CENTER);
        return collectionStackPane;
    }

}
