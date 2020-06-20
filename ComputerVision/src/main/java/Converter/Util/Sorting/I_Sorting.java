package Converter.Util.Sorting;

import Data.JsonDTO;

import java.util.List;

public interface I_Sorting {


    List<JsonDTO> sortingTheListAccordingToX(List<JsonDTO> preCardList);
    List<JsonDTO> sortingTheListAccordingToY(List<JsonDTO> preCardList);
    List<JsonDTO> removeNonDublicatesFromList(List<JsonDTO> preCardList);
    List<JsonDTO> acceptOnlyDublicate(List<JsonDTO> preCardList);
    void removeSecondInstance(List<JsonDTO> preCardList, String entryKey, String com);






}
