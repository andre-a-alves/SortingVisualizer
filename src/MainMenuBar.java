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
        JMenu sortMenu = new JMenu("Sorting Algortihm");
        JMenu quickMenu = new JMenu("Quick Sort");
        JMenu shellSortMenu = new JMenu("Shell Sort");

        for (SortingMethods method : SortingMethods.values()) {
            JMenuItem methodItem = new JMenuItem(method.menuTitle);
            methodItem.addActionListener(e->dataControlPanel.setSortType(method));
            if (method.quickType) quickMenu.add(methodItem);
            else if (method.shellType) shellSortMenu.add(methodItem);
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
        sortMenu.add(quickMenu);
        sortMenu.add(new JSeparator());
        sortMenu.add(compareMenu);

        return sortMenu;
    }

    private void showAbout()
    {
        JOptionPane.showMessageDialog(this,
                "Version\n" + VERSION +
                        "\nThis application is a visual aid" +
                        "\nto help visualize different sorting" +
                        "\nalgorithms." +
                        "\nPlease direct any questions to" +
                        "\nLord André Avìla Alves, a real noble" +
                        "\nof the Principality of Sealand," +
                        "\nat andre@alves.me",
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
