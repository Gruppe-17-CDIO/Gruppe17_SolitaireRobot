package view.tabs;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

    private final List<Tab> tabList = new ArrayList<>();
    private final CameraSourceTabNew cameraSourceTabNew = new CameraSourceTabNew();
    private final GameTab gameTab = new GameTab();
    private final OutputTab outputTab = new OutputTab();
    /*
    ----------------------- Constructor -------------------------
     */

    public TabPane() {
        setTabMinWidth(TAB_MIN_WIDTH);
        loadDefaultTabs();
        setSelectionProperty();
    }
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

    public void appendTextNewlineToOutput(String message) {
        outputTab.appendTextNewlineToOutput(message);
    }

    public void appendTextToOutput(String message) {
        outputTab.appendTextToOutput(message);
    }

    public void printDivider () {
        outputTab.printDivider();
    }
    
    /*
    ---------------------- Support Methods ----------------------
     */

    private void loadDefaultTabs () {
        tabList.add(cameraSourceTabNew);
        tabList.add(gameTab);
        tabList.add(outputTab);

        getTabs().addAll(tabList);
    }

    private void setSelectionProperty () {
        getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                if (oldValue != null) {
                    TabUserData oldTabUserData = (TabUserData) oldValue.getUserData();
                    if (oldTabUserData != null) {
                        oldTabUserData.runCloseRunnable();
                    }
                }
                if (newValue != null) {
                    TabUserData newTabUserData = (TabUserData) newValue.getUserData();
                    if (newTabUserData != null) {
                        newTabUserData.runOpenRunnable();
                    }
                }

            }
        });
    }

}
