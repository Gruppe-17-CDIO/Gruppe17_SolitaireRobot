package view.components.cardStackPanes;

import view.components.card.CardUI;

/**
 * @author Rasmus Sander Larsen
 */
public enum HighlightType {
    TO(";-fx-border-color: darkgreen; -fx-border-width: "+(CardUI.CARD_PADDING+2)+"; -fx-border-radius: 5;"),
    FROM(";-fx-border-color: darkorange; -fx-border-width: "+(CardUI.CARD_PADDING+2)+"; -fx-border-radius: 5;");

    public String highlightStyle;

    HighlightType(String highlightStyle) {
        this.highlightStyle = highlightStyle;
    }

    public String getHighlightStyle() {
        return highlightStyle;
    }
}
