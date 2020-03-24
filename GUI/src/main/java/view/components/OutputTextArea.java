package view.components;

import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * @author Rasmus Sander Larsen
 */
public class OutputTextArea extends TextArea {

    /*
    -------------------------- Fields --------------------------
     */
    
    
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public OutputTextArea() {
        setFont(Font.font("Tahoma", FontWeight.THIN, 10));
    }
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */
    
    public void appendTextNewline (String textString){
        appendText(textString + "\n");
    }

    public void printDivider() {
        appendTextNewline("--------------------------------------------------------------------------");
    }
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
