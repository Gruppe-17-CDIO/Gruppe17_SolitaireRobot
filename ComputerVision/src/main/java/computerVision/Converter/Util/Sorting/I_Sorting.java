package computerVision.Converter.Util.Sorting;

import Data.JsonDTO;

import java.util.List;

public interface I_Sorting {


    List<JsonDTO> sortingListAccordingToX(List<JsonDTO> preCardList);
    List<JsonDTO> sortingListAccordingToY(List<JsonDTO> preCardList);
    List<JsonDTO> removeNonDublicatesFromList(List<JsonDTO> preCardList);
    List<JsonDTO> acceptOnlyDublicate(List<JsonDTO> preCardList);
    void removeSecondInstance(List<JsonDTO> preCardList, String entryKey, String com);






}
