package CV_simulation;

import dataObjects.SolitaireCard;
import dataObjects.SolitaireState;

import java.util.ArrayList;
import java.util.List;

public class CV_Simulator {

    public static void main(String[] args) {
        CV_Simulator sim = new CV_Simulator();
        sim.getGameFromStart();
    }

    public SolitaireState getGameFromStart() {
        Deck deck = new Deck();
        SolitaireState state = new SolitaireState();
        List<SolitaireCard> drawnCards; // Drawn cards, 0 to 3
        SolitaireCard[] foundations = new SolitaireCard[4]; // Four piles, goal, only top card visible
        List<List<SolitaireCard>> piles; // The seven rows

        piles = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            piles.add(new ArrayList<>());
            for (int j = 0; j <= i; j++) {
                piles.get(i).add(deck.getCard());
            }
        }

        for (List<SolitaireCard> row : piles) {
            System.out.println(row);
        }

        drawnCards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            drawnCards.add(deck.getCard());
        }

        //state.setFoundations();

        //List<SolitaireCard> piles = new ArrayList<>();
        //for (int i = 0; i<3;i++){
        //    drawnCards.add(deck.getCard());
        //}
        return state;
    }
}
