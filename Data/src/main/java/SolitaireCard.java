
/*
This class represent a single card-object.
 */

public class SolitaireCard {


    private enum Foundation {
        HJERTER,
        RUDER,
        KLØVER,
        SPAR
    }
    private int rank;
    private Foundation color;

    /*
    Constructor for testing
     */

    /*Constructor
   return the lower rank (The card value of the next crd in the line ).
    */
    public SolitaireCard(int rank, Foundation color) {
        this.color = color;
        this.rank = rank;

    }

    /*
     return the rank of upper rank (The card value of the next crd in the line ).
     This method have no use so far, but I thought it would be relevant later on.
    */
    public int GetNext () {
        if (rank != 13) {
            return rank += 1;
        }
        return 0;
    }


    /*
    return the lower rank (The card value of the next crd in the line ).
    This method have no use so far, but I thought it would be relevant later on.
     */
    public int GetPrevius(){
        if (rank != 1) {
            return rank-=1;
        }
        return 0;
    }

}

