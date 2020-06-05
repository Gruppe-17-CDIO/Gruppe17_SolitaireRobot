package stateBuilding;

import dataObjects.Card;
import dataObjects.TopCards;

public class TopCardsSimulator {
    Deck deck = new Deck();

    public TopCards getSimTopCards() throws Exception {
        TopCards topCards = new TopCards();

        topCards.setDrawnCard(deck.getCard());
        topCards.setFoundations(new Card[4]);
        Card[] piles = {deck.getCard(), deck.getCard(), deck.getCard(), deck.getCard(),
                deck.getCard(), deck.getCard(), deck.getCard()};
        topCards.setPiles(piles);

        return topCards;
    }

    public Card getCard() throws Exception {
        return deck.getCard();
    }
}
