package view.components;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import view.MainGUI;
import view.components.card.CardUI;
import view.components.card.CardUIGridPane;

/**
 * @author Rasmus Sander Larsen
 */
public class OneCardStackPane extends StackPane {

    //-------------------------- Fields --------------------------

    private final String CARD_OUTLINE = ";-fx-border-color: grey; -fx-border-width: 1; -fx-border-radius: 5; -fx-padding: 3;";
    private final String HIGHLIGHT_OUTLINE = ";-fx-border-color: darkgreen; -fx-border-width: 5; -fx-border-radius: 5;";
    private final int WIDTH = 60;
    private final int HEIGHT = 75;

    public int pressCounterBoolean = 0;
    private boolean isHighlighted = false;

    private boolean spreadCardsInStack = false;

    private  HBox outlineBox;

    //----------------------- Constructor -------------------------

    public OneCardStackPane() {
        defaultL();
    }

    //------------------------ Properties -------------------------

    // region Properties

    public String getCARD_OUTLINE() {
        return CARD_OUTLINE;
    }

    public String getHIGHLIGHT_OUTLINE() {
        return HIGHLIGHT_OUTLINE;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getPressCounterBoolean() {
        return pressCounterBoolean;
    }

    public void setPressCounterBoolean(int pressCounterBoolean) {
        this.pressCounterBoolean = pressCounterBoolean;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }

    public boolean isSpreadCardsInStack() {
        return spreadCardsInStack;
    }

    public void setSpreadCardsInStack(boolean spreadCardsInStack) {
        this.spreadCardsInStack = spreadCardsInStack;
    }

    public HBox getOutlineBox() {
        return outlineBox;
    }

    public void setOutlineBox(HBox outlineBox) {
        this.outlineBox = outlineBox;
    }

    // endregion

    //---------------------- Public Methods -----------------------

    public void highlight(boolean toHighlight) {
        if (toHighlight) {
            setStyle(HIGHLIGHT_OUTLINE);
            outlineBox.setVisible(false);
        } else {
            setStyle(null);
            outlineBox.setVisible(true);
        }
    }

    public void addCardToStackPane (CardUI cardUI) {
        int noOfChildrenInStackPane = getChildren().size();
        VBox tempVBox = new VBox();
        tempVBox.setAlignment(Pos.TOP_CENTER);
        tempVBox.getChildren().add(FxUtil.emptySpace(Orientation.VERTICAL,20*(noOfChildrenInStackPane-1)));
        //tempVBox.setPadding(new Insets(10*(noOfChildrenInStackPane-1),0,0,0));
        tempVBox.getChildren().add(cardUI);
        setMinHeight((CardUI.CARD_HEIGHT+5) + (20*(noOfChildrenInStackPane-1)));
        setMaxHeight((CardUI.CARD_HEIGHT+5) + (20*(noOfChildrenInStackPane-1)));
        getChildren().add(tempVBox);
    }

    public void addCardToStackPane (CardUIGridPane cardUI) {
        int noOfChildrenInStackPane = getChildren().size();
        int cardUICounter = 0;
        for (Node node : getChildren()) {
            if (node instanceof CardUIGridPane) {
                cardUICounter++;
                try {
                    ((CardUIGridPane) node).setEmptySpaceHeight(20*cardUICounter-1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        cardUI.setEmptySpaceHeight(20*(cardUICounter));
        setMinHeight((CardUI.CARD_HEIGHT+5) + (20*(noOfChildrenInStackPane-1)));
        setMaxHeight((CardUI.CARD_HEIGHT+5) + (20*(noOfChildrenInStackPane-1)));
        getChildren().add(cardUI);
    }

    public CardUI drawTopCardFromStackPane() {
        int sizeOfStackPaneChildren = getChildren().size();
        CardUI topCard = null;
        if (sizeOfStackPaneChildren != 1) {
            try {
                topCard = (CardUI) getChildren().get(sizeOfStackPaneChildren-1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return topCard;
    }

    public void clearStackPane() {
        getChildren().removeAll(getChildren());
        createAndAddOutLineBox();
    }

    //---------------------- Support Methods ----------------------    


    private void defaultL (){
        setMinSize(CardUI.CARD_WIDTH+5,CardUI.CARD_HEIGHT+5);
        setMaxSize(CardUI.CARD_WIDTH+5,CardUI.CARD_HEIGHT+5);
        setAlignment(Pos.CENTER);

        createAndAddOutLineBox();

        pressedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (press()%2==0){
                    isHighlighted = !isHighlighted;
                    highlight(isHighlighted);
                    MainGUI.printToOutputAreaNewline("No of Children: " + getChildren().size());
                }
            }
        });
    }

    private void createAndAddOutLineBox() {
        outlineBox = new HBox();
        outlineBox.setStyle(CARD_OUTLINE);
        outlineBox.setMinSize(CardUI.CARD_WIDTH,CardUI.CARD_HEIGHT);
        outlineBox.setMaxSize(CardUI.CARD_WIDTH,CardUI.CARD_HEIGHT);
        getChildren().add(outlineBox);
    }

    public int press (){
        return pressCounterBoolean = ++pressCounterBoolean %2;
    }
}
