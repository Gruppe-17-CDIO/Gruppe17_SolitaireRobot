package view.taps;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import view.components.OutputTextArea;
import view.components.TabStd;

/**
 * @author Rasmus Sander Larsen
 */
public class OutputTab extends TabStd {

    //-------------------------- Fields --------------------------

    private OutputTextArea outputTextArea;

    //----------------------- Constructor -------------------------

    public OutputTab() {
        super("Output Terminal", "Output", "All the below text is prints from within the application");
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10));
        outputTextArea = new OutputTextArea();
        outputTextArea.setMinHeight(400);
        vBox.getChildren().add(outputTextArea);
        addToContent(vBox);
    }

    //------------------------ Properties -------------------------

    // region Properties


    // endregion

    //---------------------- Public Methods -----------------------

    public void appendTextNewlineToOutput(String message) {
        outputTextArea.appendTextNewline(message);
    }
    public void appendTextToOutput(String message) {
        outputTextArea.appendText(message);
    }

    public void printDivider () {
        outputTextArea.printDivider();
    }

    //---------------------- Support Methods ----------------------    

    @Override
    protected void testMode() {

    }

}
