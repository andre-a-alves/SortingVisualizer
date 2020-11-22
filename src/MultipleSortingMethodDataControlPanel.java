import javax.swing.*;
import java.awt.*;

public class MultipleSortingMethodDataControlPanel extends DataControlPanel{
    public MultipleSortingMethodDataControlPanel(GuiHandler guiHandler, DataHandler dataHandler) {
        super(guiHandler, dataHandler);

        JButton sortButton = new JButton("Sort");
        sortButton.setFont(new Font(sortButton.getFont().getName(), Font.PLAIN, 20));
        sortButton.addActionListener(e->dataHandler.sort());
        setLayout(new GridLayout(4,1));
        guiHandler.setTitle("Comparing Different Sorting Alogorithums");
        add(makeSortButtonPanel());
        add(makeResetButtonPanel());
        add(sortButton);
    }

    private JPanel makeResetButtonPanel() {
        JPanel dataResetPanel = new JPanel();
        JButton returnSingleButton = new JButton("Exit Comparison Mode");

        dataResetPanel.setLayout(new GridLayout(0,1));

        returnSingleButton.setFont(new Font(dataResetPanel.getFont().getName(), Font.PLAIN, 16));
        returnSingleButton.addActionListener(e->guiHandler.setSingleMethodMode());
        dataResetPanel.add(returnSingleButton);

        return dataResetPanel;
    }

    private JPanel makeSortButtonPanel() {
        JPanel sortButtonPanel = new JPanel();
        JLabel subtitle = new JLabel("Pre-Sort Data:");
        sortButtonPanel.add(subtitle);
        sortButtonPanel.setLayout(new GridLayout(8,1));

        for (InitialData action : InitialData.values()) {
            JButton actionButton = new JButton(action.title);
            actionButton.addActionListener(e->dataHandler.stopThreadsAndInitializeCharts(action));
            sortButtonPanel.add(actionButton);
        }

        int lastButtonIndex = sortButtonPanel.getComponents().length - 1;
        JButton lastButton = (JButton)sortButtonPanel.getComponent(lastButtonIndex);
        sortButtonPanel.remove(lastButtonIndex);
        sortButtonPanel.add(new JLabel(""));
        sortButtonPanel.add(lastButton);

        return sortButtonPanel;
    }

    public void setSortType(SortingMethods newMethod) {
        guiHandler.setSingleMethodMode(newMethod);
    }
}
