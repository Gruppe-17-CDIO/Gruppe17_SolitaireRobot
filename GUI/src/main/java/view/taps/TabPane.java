package view.taps;

import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class TabPane extends javafx.scene.control.TabPane {

    private final int TAB_MIN_WIDTH = 229;

    /*
    -------------------------- Fields --------------------------
     */

    private List<Tab> tabList = new ArrayList<>();
    private CameraSourceTab cameraSourceTab = new CameraSourceTab();
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public TabPane () {
        setTabMinWidth(TAB_MIN_WIDTH);
        loadDefaultTabs();
    }
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */
    
    /*
    ---------------------- Support Methods ----------------------
     */

    private void loadDefaultTabs () {
        tabList.add(cameraSourceTab);
        tabList.add(new GameTab());

        getTabs().addAll(tabList);
    }

}
