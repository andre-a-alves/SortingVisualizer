import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class GuiHandler extends JFrame {
    private DataControlPanel dataControlPanel;
    private final JLabel titleBar;
    private DataHandler dataHandler;
    private final MainMenuBar menuBar;

    public GuiHandler() {
        super("Sorting Visualizer");
        dataHandler = new SingleDataHandler();
        titleBar = makeTitleBar();
        dataControlPanel = new SingleDataControlPanel(this, dataHandler);
        menuBar = new MainMenuBar(dataControlPanel, this);
        setJMenuBar(menuBar);

        makeMasterFrame();
        setVisible(true);
    }

    private JLabel makeTitleBar() {
        JLabel titleBar = new JLabel();

        titleBar.setHorizontalAlignment(0);
        titleBar.setVerticalAlignment(0);
        titleBar.setFont(new Font(titleBar.getFont().getName(), Font.BOLD, 24));

        return titleBar;
    }

    private void makeMasterFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel masterContentPane = new JPanel();

        masterContentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        masterContentPane.setLayout(new BorderLayout(10, 10));
        masterContentPane.add(dataHandler, BorderLayout.CENTER);
        masterContentPane.add(dataControlPanel, BorderLayout.WEST);
        masterContentPane.add(titleBar, BorderLayout.NORTH);

        setContentPane(masterContentPane);
        pack();
        setLocationByPlatform(true);
    }

    public void setMultipleInitialConditionComparisonMode() {
        dataHandler = new MultipleInitialConditionDataHandler();
        dataControlPanel = new MultipleInitialConditionDataControlPanel(this, dataHandler);
        menuBar.setDataControlPanel(dataControlPanel);
        setPreferredSize(new Dimension(1920, 1080));
        makeMasterFrame();
        revalidate();
        repaint();
    }

    public void setMultipleSortMethodComparisonMode(ArrayList<SortingMethods> methods) {
        dataHandler = new MultipleSortingMethodsDataHandler(this, methods);
        dataControlPanel = new MultipleSortingMethodDataControlPanel(this, dataHandler);
        menuBar.setDataControlPanel(dataControlPanel);
        setPreferredSize(new Dimension(1920, 1080));
        makeMasterFrame();
        revalidate();
        repaint();
    }

    public void setSingleMethodMode(SortingMethods method) {
        setSingleMethodMode();
        dataControlPanel.setSortType(method);
    }

    public void setSingleMethodMode() {
        dataHandler = new SingleDataHandler();
        dataControlPanel = new SingleDataControlPanel(this, dataHandler);
        menuBar.setDataControlPanel(dataControlPanel);
        setPreferredSize(null);
        makeMasterFrame();
        revalidate();
        repaint();
    }

    public void setTitle(String newText) {
        titleBar.setText(newText);
    }
}
