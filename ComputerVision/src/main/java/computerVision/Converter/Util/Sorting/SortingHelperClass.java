package computerVision.Converter.Util.Sorting;

import Data.JsonDTO;

import java.util.*;

public class SortingHelperClass implements I_Sorting {

    @Override
    public List<JsonDTO> sortingListAccordingToX(List<JsonDTO> preCardList) {

        Comparator<JsonDTO> sortingPreCardList = new Comparator<JsonDTO>() {
            @Override
            public int compare(JsonDTO o1, JsonDTO o2) {
                if (o1.getX() <= o2.getX()) {
                    return -1;
                } else {
                    return 1;

                }

            }
        };

        Collections.sort(preCardList, sortingPreCardList);

        return preCardList;
    }

    @Override
    public List<JsonDTO> sortingListAccordingToY(List<JsonDTO> preCardList) {

        Comparator<JsonDTO> sortingPreCardList = new Comparator<JsonDTO>() {
            @Override
            public int compare(JsonDTO o1, JsonDTO o2) {
                if (o1.getY() <= o2.getY()) {
                    return -1;
                } else {
                    return 1;

                }

            }
        };

        Collections.sort(preCardList, sortingPreCardList);

        return preCardList;
    }

    @Override
    public List<JsonDTO> removeNonDublicatesFromList(List<JsonDTO> preCardList) {
        Map<String, JsonDTO> charMap = new HashMap<String, JsonDTO>();
        List<JsonDTO> sortedPreCard = new ArrayList<>();

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

    @Override
    public List<JsonDTO> acceptOnlyDublicate(List<JsonDTO> preCardList){

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
            if (entry.getValue() > 2 ) {
                System.out.printf("%s : %d %n", entry.getKey(), entry.getValue());
                removeSecondInstance(preCardList,entry.getKey(),"more");

            }else if(entry.getValue() < 2){
                removeSecondInstance(preCardList,entry.getKey(),"single");
            }

        }

        return preCardList;
    }


    //TODO: Fix this method so that it will check for which elements are removed. We want the diagonal box coordinates if possible.
    @Override
    public void removeSecondInstance(List<JsonDTO> preCardList, String entryKey, String com){
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
