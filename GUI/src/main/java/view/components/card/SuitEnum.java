package view.components.card;

import javafx.scene.image.Image;

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



    //-------------------------- Fields --------------------------


    //----------------------- Constructor -------------------------


    //------------------------ Properties -------------------------

    // region Properties


    // endregion

    //---------------------- Public Methods -----------------------


    //---------------------- Support Methods ----------------------    


}
