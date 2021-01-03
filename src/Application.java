/**
 *  This class only houses the main() method that will run the GUI of the program and returns the
 *  version number.
 */

public abstract class Application {
    /**
     * This method starts the application GUI.
     * @param args ignore
     */
    public static void main(String[] args) {
        GuiHandler appGUI = new GuiHandler();
    }

    /**
     * Method returns the version number of this application.
     * @return the version number of this application
     */
    public static String getVersion() {
        return "03 January 2021";
    }
}
