package view.components;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import view.MainGUI;
import view.components.card.CardUI;

/**
 * @author Rasmus Sander Larsen
 */
public class SolitaireGridPane extends GridPane {

    //-------------------------- Fields --------------------------

    private final int MAX_WIDTH = 400;
    private final int MAX_HEIGHT = 400;
    private final int HGAP = 3;
    private final int VGAP = 10;

    private CardUI deckCardUI;


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
                add(cardUI,3,0);
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

        deckCardUI.pressedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

            }
        });
    }

}
