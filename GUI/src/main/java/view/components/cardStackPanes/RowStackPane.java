package view.components.cardStackPanes;

import dataObjects.Card;
import javafx.scene.Node;
import view.components.card.CardUI;
import view.components.cardStackPanes.CardStackPane;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class RowStackPane extends CardStackPane {

    //-------------------------- Fields --------------------------

    private final int EMPTY_HEIGHT = 20;
    private final double EMPTY_BACKSIDE_PERCENTAGE = 0.6;

    //----------------------- Constructor -------------------------

    public RowStackPane () {
        super();
    }

    //------------------------ Properties -------------------------

    // region Properties


    // endregion

    //---------------------- Public Methods -----------------------

    @Override
    public void addCardToStackPane (CardUI cardUI) {
        int cardUIFrontSides = 0;
        int cardUIBackSides = 0;
        for (Node node : cardStackPane.getChildren()) {
            if (node instanceof CardUI) {
                if (((CardUI) node).isFrontShowing()){
                    cardUIFrontSides++;
                } else {
                    cardUIBackSides++;
                }
            }
        }

        cardUI.setEmptySpaceHeight(calEmptyHeight(cardUIFrontSides,cardUIBackSides));

        adjustHighlightBoxHeight(cardUIFrontSides, cardUIBackSides);

        cardStackPane.getChildren().add(cardUI);
    }

    public void addBacksideCardToStack(){
        CardUI backsideCard = new CardUI();
        backsideCard.showBackside();
        addCardToStackPane(backsideCard);
    }

    public void createFullRowDesign(int totalNoOfCardsInRow, CardUI visibleCardUI) {
        // Clears the stackPane holding the CardUI's
        clearCardStackPane();
        // Inserts 1 card less than the total of cards in the row with the backside showing to the stack.
        for (int i = 0; i <totalNoOfCardsInRow-1; i++) {
            addBacksideCardToStack();
        }
        // Adds the top and visible cardUI.
        addCardToStackPane(visibleCardUI);
    }

    public void createFullRowOfCardList (List<Card> cardList) {
        // Clears the stackPane holding the CardUI's
        clearCardStackPane();
        // Adds cardUI's into row.
        for (Card card : cardList) {
            if (card.getStatus() == Card.Status.FACEDOWN) {
                addBacksideCardToStack();
            } else {
                addCardToStackPane(CardUI.ofCard(card));
            }
        }
    }

    //---------------------- Support Methods ----------------------    

    private void adjustHighlightBoxHeight(int noOfCardUIFrontSides, int noOfCardUIBackSides) {
        int height = CardUI.CARD_HEIGHT_PADDED + calEmptyHeight(noOfCardUIFrontSides,noOfCardUIBackSides);
        highLightBox.setMinHeight(height);
        highLightBox.setMaxHeight(height);
        setMinHeight(highLightBox.getMinHeight());
        setMaxHeight(highLightBox.getMaxHeight());
    }

    private int calEmptyHeight(int noOfCardUIFrontSides, int noOfCardUIBackSides) {
        int frontSideHeight = EMPTY_HEIGHT *(noOfCardUIFrontSides);
        int backSideSideHeight = (int)(EMPTY_HEIGHT *EMPTY_BACKSIDE_PERCENTAGE)*(noOfCardUIBackSides);
        return frontSideHeight+backSideSideHeight;
    }

}
