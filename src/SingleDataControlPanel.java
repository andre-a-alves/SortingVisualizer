import javax.swing.*;
import java.awt.*;

public class SingleDataControlPanel extends DataControlPanel{
    public SingleDataControlPanel(GuiHandler guiHandler, DataHandler dataHandler) {
        super(guiHandler, dataHandler);

        JButton sortButton = new JButton("Sort");
        sortButton.setFont(new Font(sortButton.getFont().getName(), Font.PLAIN, 20));
        sortButton.addActionListener(e->dataHandler.sort());
        setSortType(SortingMethods.SELECTION);

        add(makeSortButtonPanel());
        add(sortButton);
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
}
