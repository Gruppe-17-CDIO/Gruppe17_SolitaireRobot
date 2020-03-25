package CV_simulation;

import dataObjects.SolitaireCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    List<SolitaireCard> cards = new ArrayList<>();
    List<SolitaireCard.Suit> suits = new ArrayList<>();

    public Deck() {
        for (int i = 1; i <= 13; i++) {
            for (SolitaireCard.Suit suit : SolitaireCard.Suit.values()) {
                try {
                    cards.add(new SolitaireCard(suit, i));
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

    public SolitaireCard getCard() {
        SolitaireCard card = cards.remove(cards.size() - 1);
        return card;
    }

    private void print() {

        for (int i = 0; i < 13; i++) {
            String line = "";
            int localI = 4 * i;
            for (int j = 0; j < 4; j++) {
                line += String.format("%-" + 20 + "s", cards.get(localI + j));
            }
            System.out.println(line);
        }
    }

}