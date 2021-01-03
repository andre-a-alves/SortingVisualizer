import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainMenuBar extends JMenuBar implements ActionListener {
    private final String VERSION;
    private DataControlPanel dataControlPanel;
    private final GuiHandler guiHandler;

    public void actionPerformed(ActionEvent event)
    {

    }

    /**
     * Constructor for objects of class MainMenu
     */
    public MainMenuBar(DataControlPanel dataControlPanel, GuiHandler guiHandler)
    {
        super();
        this.guiHandler = guiHandler;
        VERSION = Application.getVersion();
        this.dataControlPanel = dataControlPanel;
        this.add(makeFileMenu());
        this.add(makeSortMenu());
        this.add(makeHelpMenu());
    }

    /**
     * This private method returns a file menu
     * @return a file menu
     */
    private JMenu makeFileMenu()
    {
        final int SHORTCUT_MASK =
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();

        JMenu fileMenu = new JMenu("File");

        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
        quitItem.addActionListener(e -> quitApp());

        fileMenu.addSeparator();
        fileMenu.add(quitItem);

        return fileMenu;
    }

    /**
     * This private method returns a help menu
     * @return a help menu
     */
    private JMenu makeHelpMenu()
    {
        JMenu helpMenu = new JMenu("Help");

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAbout());
        helpMenu.addSeparator();
        helpMenu.add(aboutItem);
        helpMenu.addSeparator();

        return helpMenu;
    }

    private JMenu makeSortMenu() {
        JMenu sortMenu = new JMenu("Sorting Algorithm");
        JMenu quickMenu = new JMenu("Quick Sort");
        JMenu shellSortMenu = new JMenu("Shell Sort");
        JMenu mergeMenu = new JMenu("Merge Sort");

        for (SortingMethods method : SortingMethods.values()) {
            JMenuItem methodItem = new JMenuItem(method.menuTitle);
            methodItem.addActionListener(e->dataControlPanel.setSortType(method));
            if (method.quickType) quickMenu.add(methodItem);
            else if (method.shellType) shellSortMenu.add(methodItem);
            else if (method.mergeType) mergeMenu.add(methodItem);
            else sortMenu.add(methodItem);
        }
        JMenu compareMenu = new JMenu("Compare");
        JMenuItem compareItem = new JMenuItem("Compare Initial Conditions");
        compareItem.addActionListener(e->guiHandler.setMultipleInitialConditionComparisonMode());
        compareMenu.add(compareItem);
        JMenuItem compareMethodsItem = new JMenuItem("Compare Sorting Methods");
        compareMenu.add(compareMethodsItem);
        compareMethodsItem.addActionListener(e-> new ComparisonMenu(guiHandler));

        sortMenu.add(shellSortMenu);
        sortMenu.add(mergeMenu);
        sortMenu.add(quickMenu);
        sortMenu.add(new JSeparator());
        sortMenu.add(compareMenu);

        return sortMenu;
    }

    private void showAbout()
    {
        JOptionPane.showMessageDialog(this,
                "This application is a visual aid to help visualize different sorting " +
                        "algorithms." +
                        "\n\nApplication author: Andre Alves\n\n" +
                        "    https://www.linkedin.com/in/andre-a-alves/\n" +
                        "    https://github.com/andre-a-alves\n\n" +
                        "Andre is actively searching for a semester-long internship in the fall of 2021\n" +
                        "as part of his information engineering (computer engineering) study program.\n\n" +
                        "Version: " + VERSION,
                "About This Application",
                JOptionPane.PLAIN_MESSAGE);
    }

    public void setDataControlPanel(DataControlPanel dataControlPanel) {
        this.dataControlPanel = dataControlPanel;
    }

    private void quitApp()
    {
        System.exit(0);
    }
}
