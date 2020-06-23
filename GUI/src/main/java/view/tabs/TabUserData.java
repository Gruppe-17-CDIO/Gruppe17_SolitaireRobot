package view.tabs;

/**
 * @author Rasmus Sander Larsen
 */
public class TabUserData {

    //-------------------------- Fields --------------------------

    private String usedInClassName;

    private Runnable openRunnable;
    private Runnable closeRunnable;


    //----------------------- Constructor -------------------------


    //------------------------ Properties -------------------------

    // region Properties


    // endregion

    //---------------------- Public Methods -----------------------

    // region Builder
    public static class Builder {
        private String usedInClassName;
        private Runnable openRunnable;
        private Runnable closeRunnable;

        public Builder openRunnable (Runnable openRunnable) {
            this.openRunnable = openRunnable;
            return this;
        }

        public Builder closeRunnable (Runnable closeRunnable) {
            this.closeRunnable = closeRunnable;
            return this;
        }

        public Builder usedInClassName(String usedInClassName) {
            this.usedInClassName = usedInClassName;
            return this;
        }

        public TabUserData build() {
            TabUserData tabUserData = new TabUserData();
            tabUserData.openRunnable = this.openRunnable;
            tabUserData.closeRunnable = this.closeRunnable;
            tabUserData.usedInClassName = this.usedInClassName;
            return tabUserData;
        }
    }
    // endregion

    public void runOpenRunnable () {
        if (openRunnable != null) {
            System.out.println(usedInClassName + " // TabUserData.openRunnable() - Running \n{");
            openRunnable.run();
            System.out.println("}");
        }
    }

    public void runCloseRunnable () {
        if (closeRunnable != null) {
            System.out.println(usedInClassName + " // TabUserData.closeRunnable() - Running \n{");
            closeRunnable.run();
            System.out.println("}");
        }
    }

    //---------------------- Support Methods ----------------------    


}
