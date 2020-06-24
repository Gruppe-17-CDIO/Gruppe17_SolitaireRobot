package dataObjects;

public class TopCards {

    private Card drawnCard = null;
    private Card[] foundations;
    private Card[] piles;


    public Card getDrawnCard() {
        return drawnCard;
    }

    public void setDrawnCard(Card drawnCard) {
        this.drawnCard = drawnCard;
    }

    public Card[] getFoundations() {
        if (foundations == null) {
            foundations = new Card[4];
        }
        return foundations;
    }

    public void setFoundations(Card[] foundations) throws Exception {
        if (foundations.length != 4) {
            throw new Exception("TopCards: Foundations list must be 4 Cards long. Cards can be null.");
        }
        this.foundations = foundations;
    }

    public Card[] getPiles() {
        if (piles == null) {
            piles = new Card[7];
        }
        return piles;
    }

    public void setPiles(Card[] piles) throws Exception {
        if (piles.length != 7) {
            throw new Exception("TopCards: Piles list must be 7 Cards long. Cards can be null.");
        }
        this.piles = piles;
    }


    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Piles:");
        String drawCard = this.drawnCard.getColor() + Integer.toString(this.drawnCard.getRank());

        for(Card card: piles){
            if(card!=null) {
                stringBuilder.append(card.getSuit() + Integer.toString(card.getRank()) + "\n");
            }

        }

        stringBuilder.append("Draw Card: "+drawCard);
        return stringBuilder.toString();
    }
}
