package stateBuilding;

import dataObjects.Card;
import dataObjects.TopCards;

import java.util.ArrayList;
import java.util.List;

public class TopCardsSimulator {
    Deck deck = new Deck();
    List<Card> usedCards = new ArrayList();

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

    public void setUsedCards(List<Card> cards) {
        usedCards = cards;
    }

    public List<Card> getUsedCards() throws Exception {
        return usedCards;
    }
}
