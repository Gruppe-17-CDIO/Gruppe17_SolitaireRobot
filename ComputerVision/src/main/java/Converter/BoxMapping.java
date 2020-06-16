package Converter;

import Converter.Util.SortingHelperClass;
import Data.PreCard;
import dataObjects.Card;
import dataObjects.TopCards;

import java.util.List;

public class BoxMapping {
    private SortingHelperClass sorting = new SortingHelperClass();
    private static List<PreCard> currentPreCardList;
    private static int getNumberOfAnalysedImage = 0;


    public TopCards makeBoxMapping(List<PreCard> preCardList, TopCards topCards) throws Exception {

        getNumberOfAnalysedImage++;
        currentPreCardList = sorting.acceptOnlyDublicate(preCardList);

        //Sorting for smalles y to get the cards in the first row
        List<PreCard> smallestY = sorting.sortingTheListOfPrecardsAccordingToY(currentPreCardList);

        if(getNumberOfAnalysedImage==1){
            Card draw = new Card(createSuit(smallestY.get(0)),smallestY.get(0).getRank());
            topCards.setDrawnCard(draw);
            smallestY.remove(0);

            List<PreCard> smallestX = sorting.sortingTheListOfPrecardsAccordingToY(smallestY);

        }
        //TODO: Make a lower boundary of the upper row





    }

    private Card.Suit createSuit(PreCard preCard){

        switch (preCard.getColor()){
            case "H": return Card.Suit.HEART;
            case "D": return Card.Suit.DIAMOND;
            case "S": return Card.Suit.SPADE;
            case "K": return Card.Suit.CLUB;

        }
        return null;
    }

}
