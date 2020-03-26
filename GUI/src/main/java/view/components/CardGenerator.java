package view.components;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CardGenerator {
    private final int DEFAULT_SPACING = 5;
    private VBox board;
    private HBox top, topLeft, topRight, bottom;
    private VBox[] cards; // card 0-1 is deck and turned deck cards, 2-5 is

    public CardGenerator() {
        board = new VBox();
        top = new HBox();
        topLeft = new HBox();
        topRight = new HBox();
        bottom = new HBox();
        cards = new VBox[13];

        board.setSpacing(20);
        top.setSpacing(DEFAULT_SPACING);
        topLeft.setSpacing(DEFAULT_SPACING);
        topRight.setSpacing(DEFAULT_SPACING);
        bottom.setSpacing(DEFAULT_SPACING);

        drawBoard();
    }

    private void drawBoard() {
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new VBox();
            cards[i].setPrefHeight(100);
            cards[i].setPrefWidth(75);
            cards[i].setPadding(new Insets(5, 5, 5, 5));
            cards[i].setStyle("-fx-border-color: lightgrey; -fx-border-width: 1; -fx-border-radius: 5;");

            if (i == 0) {
                // The deck
                cards[i].setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5;" +
                        "-fx-background-color: darkred; -fx-background-insets: 1; -fx-background-radius: 5;");
                topLeft.getChildren().addAll(cards[i]);
            } else if (i == 1) {
                // The turned deck card
                topLeft.getChildren().addAll(cards[i]);

                // Empty spacer vbox
                VBox spacer = new VBox();
                spacer.setPrefHeight(100);
                spacer.setPrefWidth(75);
                topLeft.getChildren().addAll(spacer);
            } else if (i < 6) {
                topRight.getChildren().addAll(cards[i]);
            } else {
                bottom.getChildren().addAll(cards[i]);
            }
        }

        top.getChildren().addAll(topLeft, topRight);
        board.getChildren().addAll(top, bottom);
    }

    public VBox getBoard() {
        return board;
    }





    /*
    public VBox makeCard(String value, String suit) {
        // Input handling
        if (!(suit.equals("club") || suit.equals("diamond") || suit.equals("heart") || suit.equals("spade"))) {
            System.out.println("Unknown suit type, try again");
            return new VBox();
        }

        // The card's vbox
        VBox card = new VBox();
        card.setPrefHeight(100);
        card.setPrefWidth(75);
        card.setPadding(new Insets(5, 5, 5, 5));
        card.setSpacing(5);

        card.setStyle("-fx-background-color: white;-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 5");

        // The card's value in text packed in a vbox
        Text textValue = new Text(value);
        textValue.setFont(FxUtil.fontDefault(13));


        VBox textBox = new VBox();

        // The card's suit
        ImageView imgSuit = new ImageView();
        imgSuit.setImage(new Image(getClass().getResourceAsStream("/" + suit + "24.png")));
        imgSuit.setPreserveRatio(true);
        imgSuit.setFitHeight(15);
        imgSuit.setFitWidth(15);

        textBox.getChildren().addAll(textValue, imgSuit);

        // Adding value and suit to the card's box
        card.getChildren().addAll(textBox);

        return card;
    }
    */

}
