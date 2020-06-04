package dataObjects;

import java.util.List;

public class TopCards {

    private Card drawnCard;
    private List<Card> foundations; // 0-3
    private List<Card> piles;


    public Card getDrawnCard() {
        return drawnCard;
    }

    public void setDrawnCard(Card drawnCard) {
        this.drawnCard = drawnCard;
    }

    public List<Card> getFoundations() {
        return foundations;
    }

    public void setFoundations(List<Card> foundations) throws Exception {
        if (foundations.size() > 4) {
            throw new Exception("TopCards: Max 4 foundations");
        }
        this.foundations = foundations;
    }

    public List<Card> getPiles() {
        return piles;
    }

    public void setPiles(List<Card> piles) throws Exception {
        if (foundations.size() > 7) {
            throw new Exception("TopCards: Max 7 piles.");
        }
        this.piles = piles;
    }
}
