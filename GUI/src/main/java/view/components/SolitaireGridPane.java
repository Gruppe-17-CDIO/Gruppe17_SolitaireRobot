package view.components;

import dataObjects.Card;
import dataObjects.SolitaireState;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.*;
import view.MainGUI;
import view.components.card.CardUI;
import view.components.card.SuitEnum;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class SolitaireGridPane extends GridPane {

    //-------------------------- Fields --------------------------

    private final int MAX_WIDTH = 400;
    private final int MAX_HEIGHT = 400;
    private final int HGAP = 3;
    private final int VGAP = 10;

    private DeckStackPane deck_SP;
    private DeckStackPane drawableDeck_SP;

    private CollectionStackPane heartCollection_SP;
    private CollectionStackPane diamondCollection_SP;
    private CollectionStackPane clubCollection_SP;
    private CollectionStackPane spadeCollection_SP;

    private RowStackPane row1_SP;
    private RowStackPane row2_SP;
    private RowStackPane row3_SP;
    private RowStackPane row4_SP;
    private RowStackPane row5_SP;
    private RowStackPane row6_SP;
    private RowStackPane row7_SP;

    //----------------------- Constructor -------------------------

    public SolitaireGridPane () {
        super();
        setupLayout();
    }


    //------------------------ Properties -------------------------

    // region Properties


    // endregion

    //---------------------- Public Methods -----------------------

    public void clearAllHighlights() {
        // Clears all row highlights
        for (int rowIndex = 0; rowIndex < 7; rowIndex++) {
            rowHighlight(rowIndex,false);
        }

        // Clears all collection highlights
        for (SuitEnum suitEnum : SuitEnum.values()) {
            collectionHighlight(suitEnum, false);
        }

        // Clears all deck highlights
        deckDrawableHighlight(false);
        deckHighlight(false);
    }

    public void ofSolitaireState(SolitaireState solitaireState) {
        clearAllHighlights();

        if (solitaireState.getDrawnCards().size() != 0) {
            MainGUI.printToOutputAreaNewline("Top Card in DeckDrawn: " +CardUI.ofCard(solitaireState.getDrawnCards().get(solitaireState.getDrawnCards().size()-1)).toString());
            deckDrawableSetAndShowCard(CardUI.ofCard(solitaireState.getDrawnCards().get(solitaireState.getDrawnCards().size()-1)));
        } else {
            MainGUI.printToOutputAreaNewline("Top Card in DeckDrawn: Null");
            deckDrawableHideCards();
        }

        for (Card card : solitaireState.getFoundations()){
            if (card != null) {
                MainGUI.printToOutputAreaNewline("Collection card: " + card);
                collectionSetAndShowCard(CardUI.ofCard(card));
            }
        }

        for (int rowIndex = 0; rowIndex < solitaireState.getPiles().size(); rowIndex++)  {
            MainGUI.printToOutputAreaNewline("~~~~~~~~~~~~~~~~~~~~~~~\nCards in Row " + (rowIndex+1) + ":");
            for (Card card : solitaireState.getPiles().get(rowIndex)) {
                MainGUI.printToOutputAreaNewline(card.toString());
            }
            createFullRowOfCardList((rowIndex+1),solitaireState.getPiles().get(rowIndex));
        }
        MainGUI.printToOutputAreaNewline("~~~~~~~~~~~~~~~~~~~~~~~~~");


    }

    // region Deck Methods

    public void deckShowCard(boolean showCardOnDeck) {
        if (showCardOnDeck){
            deck_SP.showBackSide();
        } else {
            deck_SP.showCards(false);
        }
    }

    public void deckDrawableSetAndShowCard(CardUI cardUI) {
        drawableDeck_SP.showFrontSide(cardUI);
    }

    public void deckDrawableHideCards () {
        drawableDeck_SP.showCards(false);
    }

    public void deckHighlight (boolean isHighlighted) {
        deck_SP.highlight(isHighlighted);
    }

    public void deckDrawableHighlight(boolean isHighlighted) {
       drawableDeck_SP.highlight(isHighlighted);
    }

    // endregion

    // region Row Methods

    public void insetCardInRow(CardUI cardUI, int rowNumber) {
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

    public void insertBacksideCardInRow(int rowNumber) {
        switch (rowNumber) {
            case 1:
                row1_SP.addBacksideCardToStack();
                break;
            case 2:
                row2_SP.addBacksideCardToStack();
                break;
            case 3:
                row3_SP.addBacksideCardToStack();
                break;
            case 4:
                row4_SP.addBacksideCardToStack();
                break;
            case 5:
                row5_SP.addBacksideCardToStack();
                break;
            case 6:
                row6_SP.addBacksideCardToStack();
                break;
            case 7:
                row7_SP.addBacksideCardToStack();
                break;
            default:
                break;
        }
    }

    public void createFullRow(int rowNumber,int totalNoOfCardsInRow, CardUI visibleCard) {
        switch (rowNumber) {
            case 1:
                row1_SP.createFullRowDesign(totalNoOfCardsInRow,visibleCard);
                break;
            case 2:
                row2_SP.createFullRowDesign(totalNoOfCardsInRow,visibleCard);
                break;
            case 3:
                row3_SP.createFullRowDesign(totalNoOfCardsInRow,visibleCard);
                break;
            case 4:
                row4_SP.createFullRowDesign(totalNoOfCardsInRow,visibleCard);
                break;
            case 5:
                row5_SP.createFullRowDesign(totalNoOfCardsInRow,visibleCard);
                break;
            case 6:
                row6_SP.createFullRowDesign(totalNoOfCardsInRow,visibleCard);
                break;
            case 7:
                row7_SP.createFullRowDesign(totalNoOfCardsInRow,visibleCard);
                break;
            default:
                break;
        }
    }

    public void createFullRowOfCardList(int rowNumber, List<Card> cardList) {
        switch (rowNumber) {
            case 1:
                row1_SP.createFullRowOfCardList(cardList);
                break;
            case 2:
                row2_SP.createFullRowOfCardList(cardList);
                break;
            case 3:
                row3_SP.createFullRowOfCardList(cardList);
                break;
            case 4:
                row4_SP.createFullRowOfCardList(cardList);
                break;
            case 5:
                row5_SP.createFullRowOfCardList(cardList);
                break;
            case 6:
                row6_SP.createFullRowOfCardList(cardList);
                break;
            case 7:
                row7_SP.createFullRowOfCardList(cardList);
                break;
            default:
                break;
        }
    }

    public void rowHighlight(int rowNumber, boolean isHighlighted) {
        switch (rowNumber) {
            case 1:
                row1_SP.highlight(isHighlighted);
                break;
            case 2:
                row2_SP.highlight(isHighlighted);
                break;
            case 3:
                row3_SP.highlight(isHighlighted);
                break;
            case 4:
                row4_SP.highlight(isHighlighted);
                break;
            case 5:
                row5_SP.highlight(isHighlighted);
                break;
            case 6:
                row6_SP.highlight(isHighlighted);
                break;
            case 7:
                row7_SP.highlight(isHighlighted);
                break;
            default:
                break;
        }
    }

    // endregion

    // region Collection Methods

    public void collectionSetAndShowCard (CardUI cardUI) {
        switch (cardUI.getSuit()) {
            case Heart:
                heartCollection_SP.setAndShowCard(cardUI);
                break;
            case Diamond:
                diamondCollection_SP.setAndShowCard(cardUI);
                break;
            case Club:
                clubCollection_SP.setAndShowCard(cardUI);
                break;
            case Spade:
                spadeCollection_SP.setAndShowCard(cardUI);
                break;
            default:
                break;
        }
    }

    public void collectionHighlight (SuitEnum suitEnum, boolean isHighlighted) {
        switch (suitEnum) {
            case Heart:
                heartCollection_SP.highlight(isHighlighted);
                break;
            case Diamond:
                diamondCollection_SP.highlight(isHighlighted);
                break;
            case Club:
                clubCollection_SP.highlight(isHighlighted);
                break;
            case Spade:
                spadeCollection_SP.highlight(isHighlighted);
                break;
            default:
                break;
        }
    }

    // endregion

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
        deck_SP = new DeckStackPane(true);
        deck_SP.showBackSide();
        add(deck_SP,0,0);

        // Adds the cardStackPane that mimics the deck to draw cards from
        drawableDeck_SP = new DeckStackPane(false);
        add(drawableDeck_SP,1,0);

        heartCollection_SP = new CollectionStackPane(SuitEnum.Heart);
        diamondCollection_SP = new CollectionStackPane(SuitEnum.Diamond);
        clubCollection_SP = new CollectionStackPane(SuitEnum.Club);
        spadeCollection_SP = new CollectionStackPane(SuitEnum.Spade);

        add(heartCollection_SP,3,0);
        add(diamondCollection_SP,4,0);
        add(clubCollection_SP,5,0);
        add(spadeCollection_SP,6,0);

        row1_SP = new RowStackPane();
        row2_SP = new RowStackPane();
        row3_SP = new RowStackPane();
        row4_SP = new RowStackPane();
        row5_SP = new RowStackPane();
        row6_SP = new RowStackPane();
        row7_SP = new RowStackPane();

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
