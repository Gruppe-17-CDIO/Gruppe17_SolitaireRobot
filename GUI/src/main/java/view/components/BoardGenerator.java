package view.components;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import view.MainGUI;

public class BoardGenerator {
    private final int DEFAULT_SPACING = 5;
    private VBox board;
    private HBox top, topLeft, topRight, bottom;

    // card 0-1 is deck and turned deck cards, 2-5 is the top right ace cards and 5-12 is the bottom cards
    private VBox[] cardBox;
    private Text[] cardValue;
    private ImageView[] cardSuit;

    public BoardGenerator() {
        board = new VBox();
        top = new HBox();
        topLeft = new HBox();
        topRight = new HBox();
        bottom = new HBox();

        cardBox = new VBox[13];
        cardValue = new Text[13];
        cardSuit = new ImageView[13];

        board.setSpacing(20);
        top.setSpacing(DEFAULT_SPACING);
        topLeft.setSpacing(DEFAULT_SPACING);
        topRight.setSpacing(DEFAULT_SPACING);
        bottom.setSpacing(DEFAULT_SPACING);

        drawBoard();
    }

    private void drawBoard() {
        for (int i = 0; i < cardBox.length; i++) { /* Setting up the VBoxes for the cards*/
            cardBox[i] = new VBox();
            cardBox[i].setMinSize(75, 100);
            cardBox[i].setPadding(new Insets(5, 5, 5, 5));
            cardBox[i].setStyle("-fx-border-color: lightgrey; -fx-border-width: 1; -fx-border-radius: 5;");

            if (i == 0) {
                // The deck (card backside style is set here)
                cardBox[i].setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5;" +
                        "-fx-background-color: darkred; -fx-background-insets: 3; -fx-background-radius: 5;");
                topLeft.getChildren().addAll(cardBox[i]);
            } else if (i == 1) {
                // The turned deck card
                topLeft.getChildren().addAll(cardBox[i]);

                // Empty spacer vbox
                VBox spacer = new VBox();
                spacer.setPrefHeight(100);
                spacer.setPrefWidth(75);
                topLeft.getChildren().addAll(spacer);
            } else if (i < 6) {
                // The top right cards
                topRight.getChildren().addAll(cardBox[i]);
            } else {
                // The bottom 7 cards
                bottom.getChildren().addAll(cardBox[i]);
            }

            // Alignment of the value text and suit image
            VBox cardContentAlignment = new VBox();
            BorderPane textAlignment = new BorderPane();
            textAlignment.setMaxWidth(15);
            cardValue[i] = new Text();
            textAlignment.setCenter(cardValue[i]);

            cardSuit[i] = new ImageView();
            cardSuit[i].setPreserveRatio(true);
            cardSuit[i].setFitHeight(15);
            cardSuit[i].setFitWidth(15);

            // Adding the value text and suit image to the card's box
            cardContentAlignment.getChildren().addAll(textAlignment, cardSuit[i]);
            cardBox[i].getChildren().addAll(cardContentAlignment);

        } /* VBox card setup end */

        // Adding the boxes to the board box
        top.getChildren().addAll(topLeft, topRight);
        board.getChildren().addAll(top, bottom);
    }

    public VBox getBoard() {
        return board;
    }

    public VBox addCard(int cardPlacement, String value, String suit) {
        // Input handling
        if (cardPlacement < 1 || cardPlacement > 12) {
            MainGUI.printToOutputAreaNewline("Incorrect index: " + cardPlacement + ", try again");
            return board;
        }
        if (!(suit.equals("club") || suit.equals("diamond") || suit.equals("heart") || suit.equals("spade"))) {
            MainGUI.printToOutputAreaNewline("Unknown suit type: " + suit + ", try again");
            return board;
        }

        // Styling of the card
        cardBox[cardPlacement].setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5;" +
                "-fx-background-color: white; -fx-background-insets: 1; -fx-background-radius: 5;");

        // The text showing the card's value
        cardValue[cardPlacement].setText(value);

        // The image showing the card's suit
        cardSuit[cardPlacement].setImage(new Image(getClass().getResourceAsStream("/" + suit + "24.png")));

        return board;
    }

}
