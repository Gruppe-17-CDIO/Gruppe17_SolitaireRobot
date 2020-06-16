package Converter.Util;

import Data.PreCard;

import java.util.*;

public class SortingHelperClass {

    public List<PreCard> sortingTheListOfPrecardsAccordingToX(List<PreCard> preCardList) {

        Comparator<PreCard> sortingPreCardList = new Comparator<PreCard>() {
            @Override
            public int compare(PreCard o1, PreCard o2) {
                if (o1.getLowerCoordinate().getX() <= o2.getLowerCoordinate().getX()) {
                    return -1;
                } else {
                    return 1;

                }

            }
        };

        Collections.sort(preCardList, sortingPreCardList);

        return preCardList;
    }


    public List<PreCard> sortingTheListOfPrecardsAccordingToY(List<PreCard> preCardList) {

        Comparator<PreCard> sortingPreCardList = new Comparator<PreCard>() {
            @Override
            public int compare(PreCard o1, PreCard o2) {
                if (o1.getLowerCoordinate().getY() <= o2.getLowerCoordinate().getY()) {
                    return -1;
                } else {
                    return 1;

                }

            }
        };

        Collections.sort(preCardList, sortingPreCardList);

        return preCardList;
    }


    public List<PreCard> removeNonDublicatesFromList(List<PreCard> preCardList) {
        Map<String, PreCard> charMap = new HashMap<String, PreCard>();
        List<PreCard> sortedPreCard = new ArrayList<>();

        for (int i = 0; i < preCardList.size(); i++) {
            System.out.println(preCardList.get(i));
            if (charMap.containsKey(preCardList.get(i).toString())) {
                sortedPreCard.add(preCardList.get(i));
            } else {
                charMap.put(preCardList.get(i).toString(), preCardList.get(i));
            }
        }
        return acceptOnlyDublicate(sortedPreCard);
    }


    public List<PreCard> acceptOnlyDublicate(List<PreCard> preCardList){

        Map<String, Integer> charMap = new HashMap<String, Integer>();
        for (int i = 0; i < preCardList.size(); i++) {
            if (charMap.containsKey(preCardList.get(i).toString())) {
                charMap.put(preCardList.get(i).toString(), charMap.get(preCardList.get(i).toString()) + 1);
            } else {
                charMap.put(preCardList.get(i).toString(), 1);
            }
        } // Iterate through HashMap to print all duplicate characters of String
        Set<Map.Entry<String, Integer>> entrySet = charMap.entrySet();
        for (Map.Entry<String, Integer> entry : entrySet) {
            if (entry.getValue() > 2) {
                System.out.printf("%s : %d %n", entry.getKey(), entry.getValue());
                removeSecondInstance(preCardList,entry.getKey(),"more");

            }else{
                removeSecondInstance(preCardList,entry.getKey(),"single");
            }

        }

        return preCardList;
    }


    //TODO: Fix this method so that it will check for which elements are removed. We want the diagonal box coordinates if possible.
    private void removeSecondInstance(List<PreCard> preCardList, String entryKey, String com){
        int counter = 0;
        String command = com;
        switch (command){
            case "single":
                for(int i = 0; i<preCardList.size();i++){
                    if(preCardList.get(i).toString().equals(entryKey)){
                        preCardList.remove(i);
                    }
                }
                break;

            case "more":
                for(int i = 0; i<preCardList.size();i++){
                    if(preCardList.get(i).toString().equals(entryKey)){
                        if(counter==2){
                            preCardList.remove(i);
                        }else{
                            counter++;
                        }
                    }
                }
                break;
        }
    }
}
