package view.components;

import view.components.card.CardUI;

/**
 * @author Rasmus Sander Larsen
 */
public class DeckStackPane extends OneCardStackPane {

    //-------------------------- Fields --------------------------

    private CardUI topCard;

    //----------------------- Constructor -------------------------

    public DeckStackPane() {
        applyDefault();
    }

    //------------------------ Properties -------------------------

    // region Properties


    // endregion

    //---------------------- Public Methods -----------------------

    public void showBackSide () {
        // Makes sure that the topCard is in stack
        makeSureTopCardIsInDeck();
        topCard.showBackside();
    }

    public void showFrontSide (CardUI cardUI) {
        clearStackPane();
        if (!cardUI.isFrontShowing()) {
            cardUI.showFrontside();
        }
        addCardToStackPane(cardUI);
    }

    //---------------------- Support Methods ----------------------    

    private void applyDefault () {
        setSpreadCardsInStack(false);
    }

    private void makeSureTopCardIsInDeck() {
        if (!getChildren().contains(topCard)){
            if (topCard == null) {
                topCard = new CardUI();
            }
            addCardToStackPane(topCard);
        }
    }

}
