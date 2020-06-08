package view.components;

import view.components.card.CardUI;

/**
 * @author Rasmus Sander Larsen
 */
public class DeckStackPane extends CardStackPane {

    //-------------------------- Fields --------------------------

    private CardUI topCard;

    //----------------------- Constructor -------------------------

    public DeckStackPane() {
        super();
        applyDefault();
    }

    public DeckStackPane(boolean isTopCardVisible) {
        super();
        applyDefault();
        showCards(isTopCardVisible);
    }

    //------------------------ Properties -------------------------

    // region Properties


    // endregion

    //---------------------- Public Methods -----------------------

    public void showBackSide () {
        if (topCard.isFrontShowing()){
            topCard.showBackside();
            showCards(true);
        }
    }

    public void showFrontSide (CardUI cardUI) {
        if (!topCard.isFrontShowing()) {
            topCard.showFrontSide();
            showCards(true);
        }
        topCard.setValueAndApply(cardUI.getValue());
        topCard.setSuitAndApply(cardUI.getSuit());
    }

    //---------------------- Support Methods ----------------------    

    private void applyDefault () {
        topCard = new CardUI();
        addCardToStackPane(topCard);
    }

}
