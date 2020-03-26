package CV_simulation;

import dataObjects.Card;
import dataObjects.SolitaireState;

import java.util.ArrayList;
import java.util.List;

public class TestStateGenerator {
    SolitaireState state = new SolitaireState();
    private boolean stockEmpty = true; // Cards to draw, face not visible
    private List<Card> drawnCards; // Drawn cards, 0 to 3
    private Card[] foundations = new Card[4]; // Four piles, goal, only top card visible
    private List<List<Card>> piles; // The seven rows

    public static void main(String[] args) {
        TestStateGenerator sim = new TestStateGenerator();
        try {

            SolitaireState privateState = sim.getTestState(1);
            privateState.printState();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SolitaireState getTestState(int id) throws Exception {
        if (id == 1) {
            stockEmpty = false;
            state.setStockEmpty(stockEmpty);
            // Drawn cards
            drawnCards = new ArrayList<>();
            drawnCards.add(new Card(Card.Suit.CLUB, 1));
            drawnCards.add(new Card(Card.Suit.HEART, 5));
            drawnCards.add(new Card(Card.Suit.CLUB, 6));
            state.setDrawnCards(drawnCards);
            // Foundations
            foundations = new Card[4];
            foundations[0] = new Card(Card.Suit.SPADE, 1);
            foundations[1] = null;
            foundations[2] = new Card(Card.Suit.DIAMOND, 2);
            foundations[2] = null;
            state.setFoundations(foundations);
            // Piles
            piles = new ArrayList<>();
            ArrayList<Card> pile1 = new ArrayList<>();
            pile1.add(new Card(Card.Suit.CLUB, 9));
            pile1.add(new Card(Card.Suit.DIAMOND, 8));
            piles.add(pile1);
            ArrayList<Card> pile2 = new ArrayList<>();
            pile2.add(new Card(Card.Status.FACEDOWN));
            pile2.add(new Card(Card.Suit.CLUB, 8));
            pile2.add(new Card(Card.Suit.HEART, 7));
            piles.add(pile2);
            ArrayList<Card> pile3 = new ArrayList<>();
            pile3.add(new Card(Card.Status.FACEDOWN));
            pile3.add(new Card(Card.Status.FACEDOWN));
            pile3.add(new Card(Card.Suit.SPADE, 12));
            pile3.add(new Card(Card.Suit.DIAMOND, 11));
            pile3.add(new Card(Card.Suit.SPADE, 10));
            piles.add(pile3);
            ArrayList<Card> pile4 = new ArrayList<>();
            pile4.add(new Card(Card.Suit.HEART, 13));
            pile4.add(new Card(Card.Suit.CLUB, 12));
            pile4.add(new Card(Card.Suit.HEART, 11));
            pile4.add(new Card(Card.Suit.CLUB, 10));
            pile4.add(new Card(Card.Suit.HEART, 9));
            piles.add(pile4);
            ArrayList<Card> pile5 = new ArrayList<>();
            pile5.add(new Card(Card.Status.FACEDOWN));
            pile5.add(new Card(Card.Status.FACEDOWN));
            pile5.add(new Card(Card.Status.FACEDOWN));
            pile5.add(new Card(Card.Status.FACEDOWN));
            pile5.add(new Card(Card.Suit.CLUB, 5));
            pile5.add(new Card(Card.Suit.DIAMOND, 4));
            piles.add(pile5);
            ArrayList<Card> pile6 = new ArrayList<>();
            pile6.add(new Card(Card.Status.FACEDOWN));
            pile6.add(new Card(Card.Status.FACEDOWN));
            pile6.add(new Card(Card.Suit.DIAMOND, 10));
            pile6.add(new Card(Card.Suit.SPADE, 9));
            pile6.add(new Card(Card.Suit.CLUB, 8));
            piles.add(pile6);
            ArrayList<Card> pile7 = new ArrayList<>();
            pile7.add(new Card(Card.Status.FACEDOWN));
            pile7.add(new Card(Card.Status.FACEDOWN));
            pile7.add(new Card(Card.Status.FACEDOWN));
            pile7.add(new Card(Card.Status.FACEDOWN));
            pile7.add(new Card(Card.Status.FACEDOWN));
            pile7.add(new Card(Card.Status.FACEDOWN));
            pile7.add(new Card(Card.Suit.CLUB, 3));
            pile7.add(new Card(Card.Suit.DIAMOND, 2));
            piles.add(pile7);
            state.setPiles(piles);
        }
        return state;
    }
}
