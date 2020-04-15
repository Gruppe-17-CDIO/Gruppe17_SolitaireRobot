package view.components;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.*;
import view.MainGUI;
import view.components.card.CardUI;
import view.components.card.CardUIGridPane;
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
    private DeckStackPane deck_SP;
    private DeckStackPane drawableDeck_SP;

    private OneCardStackPane heartCollection_SP;
    private OneCardStackPane diamondCollection_SP;
    private OneCardStackPane clubCollection_SP;
    private OneCardStackPane spadeCollection_SP;

    private OneCardStackPane row1_SP;
    private OneCardStackPane row2_SP;
    private OneCardStackPane row3_SP;
    private OneCardStackPane row4_SP;
    private OneCardStackPane row5_SP;
    private OneCardStackPane row6_SP;
    private OneCardStackPane row7_SP;

    //----------------------- Constructor -------------------------

    public SolitaireGridPane () {
        super();
        setupLayout();
    }


    //------------------------ Properties -------------------------

    // region Properties


    // endregion

    //---------------------- Public Methods -----------------------

    public void insetCardInColumn(CardUI cardUI, int rowNumber) {
        switch (rowNumber) {
            case 1:
                row1_SP.addCardToStackPane(cardUI);
                break;
            case 2:
                row2_SP.addCardToStackPane(cardUI);
                break;
            case 3:
                row3_SP.addCardToStackPane(cardUI);
                break;
            case 4:
                row4_SP.addCardToStackPane(cardUI);
                break;
            case 5:
                row5_SP.addCardToStackPane(cardUI);
                break;
            case 6:
                row6_SP.addCardToStackPane(cardUI);
                break;
            case 7:
                row7_SP.addCardToStackPane(cardUI);
                break;
            default:
                break;
        }
    }

    public void insetCardInColumn(CardUIGridPane cardUI, int rowNumber) {
        switch (rowNumber) {
            case 1:
                row1_SP.addCardToStackPane(cardUI);
                break;
            case 2:
                row2_SP.addCardToStackPane(cardUI);
                break;
            case 3:
                row3_SP.addCardToStackPane(cardUI);
                break;
            case 4:
                row4_SP.addCardToStackPane(cardUI);
                break;
            case 5:
                row5_SP.addCardToStackPane(cardUI);
                break;
            case 6:
                row6_SP.addCardToStackPane(cardUI);
                break;
            case 7:
                row7_SP.addCardToStackPane(cardUI);
                break;
            default:
                break;
        }
    }

    public void insertCardInCollectionDeck (CardUI cardUI) {
        switch (cardUI.getSuit()) {
            case Heart:
                heartCollection_SP.addCardToStackPane(cardUI);
                break;
            case Diamond:
                diamondCollection_SP.addCardToStackPane(cardUI);
                break;
            case Club:
                clubCollection_SP.addCardToStackPane(cardUI);
                break;
            case Spade:
                spadeCollection_SP.addCardToStackPane(cardUI);
                break;
            default:
                break;
        }
    }

    public void insertCardInCollectionDeckWithRemove (CardUI cardUI) {
        switch (cardUI.getSuit()) {
            case Heart:
                heartCollection_SP.clearStackPane();
                heartCollection_SP.addCardToStackPane(cardUI);
                break;
            case Diamond:
                diamondCollection_SP.clearStackPane();
                diamondCollection_SP.addCardToStackPane(cardUI);
                break;
            case Club:
                clubCollection_SP.clearStackPane();
                clubCollection_SP.addCardToStackPane(cardUI);
                break;
            case Spade:
                spadeCollection_SP.clearStackPane();
                spadeCollection_SP.addCardToStackPane(cardUI);
                break;
            default:
                break;
        }
    }

    //---------------------- Support Methods ----------------------    

    private void setupLayout() {
        setAlignment(Pos.TOP_CENTER);
        setMaxSize(MAX_WIDTH,MAX_HEIGHT);
        setPrefSize(MAX_WIDTH,MAX_HEIGHT);
        setHgap(HGAP);
        setVgap(VGAP);
        // Set ColumnConstraints to 1/7 for each of the 7 columns.
        for(int i = 0; i < 7 ; i++) {
            ColumnConstraints tempColumnConstraints = new ColumnConstraints();
            tempColumnConstraints.setPercentWidth(50);
            getColumnConstraints().add(tempColumnConstraints);
        }

        // Set RowConstraints Vertical Alignment to "TOP" for both rows.
        for(int i = 0; i < 2 ; i++) {
            RowConstraints tempRowConstraints = new RowConstraints();
            tempRowConstraints.setValignment(VPos.TOP);
            getRowConstraints().add(tempRowConstraints);
        }

        // Adds the cardStackPane that mimics the deck to turn over cards from
        deck_SP = new DeckStackPane();
        deck_SP.showBackSide();
        add(deck_SP,0,0);

        // Adds the cardStackPane that mimics the deck to draw cards from
        drawableDeck_SP = new DeckStackPane();
        add(drawableDeck_SP,1,0);

        heartCollection_SP = new OneCardStackPane();
        diamondCollection_SP = new OneCardStackPane();
        clubCollection_SP = new OneCardStackPane();
        spadeCollection_SP = new OneCardStackPane();

        add(heartCollection_SP,3,0);
        add(diamondCollection_SP,4,0);
        add(clubCollection_SP,5,0);
        add(spadeCollection_SP,6,0);

        row1_SP = new OneCardStackPane();
        row2_SP = new OneCardStackPane();
        row3_SP = new OneCardStackPane();
        row4_SP = new OneCardStackPane();
        row5_SP = new OneCardStackPane();
        row6_SP = new OneCardStackPane();
        row7_SP = new OneCardStackPane();

        add(row1_SP,0,1);
        add(row2_SP,1,1);
        add(row3_SP,2,1);
        add(row4_SP,3,1);
        add(row5_SP,4,1);
        add(row6_SP,5,1);
        add(row7_SP,6,1);

        //TODO Method just created to check insertCardInCollectionWithRemove()
        /*
        setOnMouseClicked(event -> {
            int heartCollectionSizeBefore = heartCollection_SP.getChildren().size();
            MainGUI.printToOutputAreaNewline("HeartCollection Size Before: " + heartCollectionSizeBefore);
            if (heartCollectionSizeBefore == 0) {
                insertCardInCollectionDeckWithRemove(new CardUI("1", SuitEnum.Heart));
            } else {
                int topCardUIValue = Integer.parseInt(((CardUI) heartCollection_SP.getChildren().get(1)).getValue());
                insertCardInCollectionDeckWithRemove(new CardUI(String.valueOf(++topCardUIValue),SuitEnum.Heart));
            }
            MainGUI.printToOutputAreaNewline("HeartCollection Size Before: " + heartCollection_SP.getChildren().size());
            MainGUI.printDivider();
        });

         */
    }
}
