package stateBuilding;

import dataObjects.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * If you need a random generated deck, here it is!
 */

public class Deck {
    List<Card> cards = new ArrayList<>();
    List<Card.Suit> suits = new ArrayList<>();

    public Deck() {
        for (int i = 1; i <= 13; i++) {
            for (Card.Suit suit : Card.Suit.values()) {
                try {
                    cards.add(new Card(suit, i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        print();
        Collections.shuffle(cards);
        System.out.println("\nShuffle shuffle...\n");
        print();
    }

    public static void main(String[] args) {
        // Test building deck
        Deck deck = new Deck();
    }

    public Card getCard() throws Exception {
        if (cards.size() < 1) {
            throw new Exception("Deck: Deck is now empty.");
        }
        Card card = cards.remove(cards.size() - 1);
        return card;
    }

    private void print() {

        for (int i = 0; i < 13; i++) {
            String line = "";
            int localI = 4 * i;
            for (int j = 0; j < 4; j++) {
                line += String.format("%-" + 12 + "s", cards.get(localI + j));
            }
            System.out.println(line);
        }
    }

}