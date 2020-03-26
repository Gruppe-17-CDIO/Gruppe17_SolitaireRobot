package view.taps;

import javafx.scene.control.Tab;
import view.components.TabStd;
import view.taps.CameraSourceTab;

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
    private GameTab gameTab = new GameTab();
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
        tabList.add(gameTab);

        getTabs().addAll(tabList);
    }

}
