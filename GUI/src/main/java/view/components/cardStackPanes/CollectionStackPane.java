package view.components.cardStackPanes;

import view.components.card.CardUI;
import view.components.card.SuitEnum;
import view.components.cardStackPanes.CardStackPane;

/**
 * @author Rasmus Sander Larsen
 */
public class CollectionStackPane extends CardStackPane {

    //-------------------------- Fields --------------------------

    private SuitEnum collectionSuit;
    private CardUI topCard;

    //----------------------- Constructor -------------------------

    public CollectionStackPane () {
        super();
        applyDefault();
    }

    public CollectionStackPane (SuitEnum collectionSuit) {
        super();
        this.collectionSuit = collectionSuit;
        applyDefault();
    }

    //------------------------ Properties -------------------------

    // region Properties


    // endregion

    //---------------------- Public Methods -----------------------

    public void setAndShowCard (CardUI cardUI) {
        // Displays CardUI's
        showCards(true);

        // Sets the value of the topCardUI and shows the front side.
        topCard.setValueAndApply(cardUI.getValue());
        topCard.setSuitAndApply(cardUI.getSuit());
        topCard.showFrontSide();
    }

    //---------------------- Support Methods ----------------------    

    private void applyDefault () {
        topCard = new CardUI();
        addCardToStackPane(topCard);
        showCards(false);
    }

}
