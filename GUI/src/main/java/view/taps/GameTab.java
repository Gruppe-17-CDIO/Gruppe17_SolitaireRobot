package view.taps;

import javafx.scene.layout.HBox;
import view.components.SolitaireGridPane;
import view.components.TabStd;
import view.components.card.CardUI;
import view.components.card.SuitEnum;


/**
 * @author Rasmus Sander Larsen
 */
public class GameTab extends TabStd {

    //-------------------------- Fields --------------------------


    //----------------------- Constructor -------------------------

    public GameTab () {
        super("Game", "Title", "Desc");

        SolitaireGridPane solitaireGridPane = new SolitaireGridPane();
        solitaireGridPane.showDeck(true);
        solitaireGridPane.insetCardInColumn(new CardUI("2", SuitEnum.Club),0);
        solitaireGridPane.insetCardInColumn(new CardUI("5", SuitEnum.Spade),1);
        solitaireGridPane.insetCardInColumn(new CardUI("10", SuitEnum.Diamond),2);
        solitaireGridPane.insetCardInColumn(new CardUI("K", SuitEnum.Heart),3);
        solitaireGridPane.insetCardInColumn(new CardUI("J", SuitEnum.Diamond),4);
        solitaireGridPane.insetCardInColumn(new CardUI("D", SuitEnum.Club),5);
        solitaireGridPane.insetCardInColumn(new CardUI("8", SuitEnum.Spade),6);

        //solitaireGridPane.insertCardInCollectionDeck(new CardUI("A", SuitEnum.Heart));
        solitaireGridPane.insertCardInCollectionDeck(new CardUI("2", SuitEnum.Diamond));
        solitaireGridPane.insertCardInCollectionDeck(new CardUI("A", SuitEnum.Club));
        solitaireGridPane.insertCardInCollectionDeck(new CardUI("3", SuitEnum.Spade));

        addToContent(solitaireGridPane);
    }

    //------------------------ Properties -------------------------

    // region Properties


    // endregion

    //---------------------- Public Methods -----------------------


    //---------------------- Support Methods ----------------------    


    @Override
    protected void testMode() {

    }
}
