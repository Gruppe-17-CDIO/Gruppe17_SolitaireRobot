package view.components.card;

import dataObjects.Card;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

/**
 * @author Rasmus Sander Larsen
 */
public enum SuitEnum {

    Diamond("Diamond"),
    Spade("Spade"),
    Club("Club"),
    Heart("Heart");


    private String suitName;

    SuitEnum(String suitName) {
        this.suitName = suitName;
    }

    public Image getImage(ImageSize imageSize){
        String imageResourcePath = "/cardUI/" + suitName.toLowerCase() +imageSize.getSizePx() + ".png";
        return new Image(getClass().getResourceAsStream(imageResourcePath));
    };

    public enum ImageSize{

        px24(24),
        px512(512);

        private int sizePx;
        ImageSize(int sizePx) {
            this.sizePx = sizePx;
        }

        protected int getSizePx() {
            return sizePx;
        }
    }

    public static SuitEnum ofSuit(Card.Suit suit) {
        switch (suit) {
            case CLUB:
                return Club;
            case SPADE:
                return Spade;
            case HEART:
                return Heart;
            case DIAMOND:
                return Diamond;
            default:
                return null;
        }
    }



    //-------------------------- Fields --------------------------


    //----------------------- Constructor -------------------------


    //------------------------ Properties -------------------------

    // region Properties


    // endregion

    //---------------------- Public Methods -----------------------


    //---------------------- Support Methods ----------------------    


}
