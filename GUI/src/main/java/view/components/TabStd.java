package view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import view.MainGUI;
import view.components.FxUtil;

import java.io.File;

/**
 * @author Rasmus Sander Larsen
 */
public abstract class TabStd extends Tab {

    //-------------------------- Fields --------------------------

    protected String header, desc;
    protected VBox content = FxUtil.vBox(true);

    //----------------------- Constructor -------------------------

    public TabStd(String title, String header, String desc) {
        this.header = header;
        this.desc = desc;
        setText(title);
        setHeaderAndDesc();
        applySettings();
    }


    //------------------------ Properties -------------------------

    // <editor-folder desc="Properties"


    // </editor-folder>


    //---------------------- Public Methods -----------------------

    protected void addSeparator() {
        Separator separator = new Separator();
        separator.setMaxWidth(450);
        separator.setPadding(new Insets(5, 0, 5, 0));
        addToContent(separator);
    }

    //---------------------- Support Methods ----------------------

    private void applySettings() {
        content.setAlignment(Pos.TOP_CENTER);
        content.setSpacing(FxUtil.getSpacing());
        content.setPadding(new Insets(FxUtil.getPadding()));
        setClosable(false);
        setContent(content);
    }

    protected void addToContent(Node node) {
        content.getChildren().add(node);
    }

    protected void addAllToContent(Node... nodes) {
        content.getChildren().addAll(nodes);
    }

    protected void setHeaderAndDesc() {
        //region Header
        Text headerText = FxUtil.headerTextDefault(header);
        // endregion

        // region Description
        Text descText = FxUtil.textDefault(desc);
        descText.setWrappingWidth(400);
        descText.setTextAlignment(TextAlignment.JUSTIFY);
        // endregion

        addAllToContent(headerText, descText);
    }

    private File folderOfFile(File file) {
        String filePath = file.getAbsolutePath();
        int lastBackSlash = filePath.lastIndexOf("\\");
        String folder = filePath.substring(0, lastBackSlash);
        return new File(folder);
    }

    protected abstract void testMode(boolean isTesting);
}
