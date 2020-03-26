package CV_simulation;

import dataObjects.Card;
import dataObjects.SolitaireState;

import java.util.ArrayList;
import java.util.List;

public class CV_Simulator {
    SolitaireState state = new SolitaireState();
    private boolean stockEmpty = true; // Cards to draw, face not visible
    private List<Card> drawnCards; // Drawn cards, 0 to 3
    private Card[] foundations = new Card[4]; // Four piles, goal, only top card visible
    private List<List<Card>> piles; // The seven rows

    public static void main(String[] args) {
        CV_Simulator sim = new CV_Simulator();
        try {

            StatePrinter.printState(sim.getTestState(1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SolitaireState getTestState(int i) throws Exception {
        if (i == 1) {
            stockEmpty = false;
            state.setStockEmpty(stockEmpty);
            // Drawn cards
            drawnCards = new ArrayList<>();
            drawnCards.add(new Card(Card.Suit.CLUBS, 1));
            drawnCards.add(new Card(Card.Suit.HEARTS, 5));
            drawnCards.add(new Card(Card.Suit.CLUBS, 6));
            state.setDrawnCards(drawnCards);
            // Foundations
            foundations = new Card[4];
            foundations[0] = new Card(Card.Suit.SPADES, 1);
            foundations[1] = null;
            foundations[2] = new Card(Card.Suit.DIAMONDS, 2);
            foundations[2] = null;
            state.setFoundations(foundations);
            // Piles
            piles = new ArrayList<>();
            ArrayList<Card> pile1 = new ArrayList<>();
            pile1.add(new Card(Card.Suit.CLUBS, 9));
            pile1.add(new Card(Card.Suit.DIAMONDS, 8));
            piles.add(pile1);
            ArrayList<Card> pile2 = new ArrayList<>();
            pile2.add(null);
            pile2.add(new Card(Card.Suit.CLUBS, 8));
            pile2.add(new Card(Card.Suit.HEARTS, 7));
            piles.add(pile2);
            ArrayList<Card> pile3 = new ArrayList<>();
            pile3.add(null);
            pile3.add(null);
            pile3.add(new Card(Card.Suit.SPADES, 12));
            pile3.add(new Card(Card.Suit.DIAMONDS, 11));
            pile3.add(new Card(Card.Suit.SPADES, 10));
            piles.add(pile3);
            ArrayList<Card> pile4 = new ArrayList<>();
            pile4.add(new Card(Card.Suit.HEARTS, 13));
            pile4.add(new Card(Card.Suit.CLUBS, 12));
            pile4.add(new Card(Card.Suit.HEARTS, 11));
            pile4.add(new Card(Card.Suit.CLUBS, 10));
            pile4.add(new Card(Card.Suit.HEARTS, 9));
            piles.add(pile4);
            ArrayList<Card> pile5 = new ArrayList<>();
            pile5.add(null);
            pile5.add(null);
            pile5.add(null);
            pile5.add(null);
            pile5.add(new Card(Card.Suit.CLUBS, 5));
            pile5.add(new Card(Card.Suit.DIAMONDS, 4));
            piles.add(pile5);
            ArrayList<Card> pile6 = new ArrayList<>();
            pile6.add(null);
            pile6.add(null);
            pile6.add(new Card(Card.Suit.DIAMONDS, 10));
            pile6.add(new Card(Card.Suit.SPADES, 9));
            pile6.add(new Card(Card.Suit.CLUBS, 8));
            piles.add(pile6);
            ArrayList<Card> pile7 = new ArrayList<>();
            pile7.add(null);
            pile7.add(null);
            pile7.add(null);
            pile7.add(null);
            pile7.add(null);
            pile7.add(null);
            pile7.add(new Card(Card.Suit.CLUBS, 3));
            pile7.add(new Card(Card.Suit.DIAMONDS, 2));
            piles.add(pile7);
            state.setPiles(piles);
        }
        return state;
    }
}
