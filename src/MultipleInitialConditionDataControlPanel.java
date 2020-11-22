import javax.swing.*;
import java.awt.*;

public class MultipleInitialConditionDataControlPanel extends DataControlPanel{

    public MultipleInitialConditionDataControlPanel(GuiHandler guiHandler, DataHandler dataHandler) {
        super(guiHandler, dataHandler);

        JButton sortButton = new JButton("Sort");
        sortButton.setFont(new Font(sortButton.getFont().getName(), Font.PLAIN, 20));
        sortButton.addActionListener(e->dataHandler.sort());
        setSortType(SortingMethods.SELECTION);


        add(makeDataResetButtonPanel());
        add(sortButton);
    }

    private JPanel makeDataResetButtonPanel() {
        JPanel dataResetPanel = new JPanel();
        JButton resetDataButton = new JButton("Reset Data");
        JButton returnSingleButton = new JButton("Exit Comparison Mode");

        dataResetPanel.setLayout(new GridLayout(0,1));

        resetDataButton.setFont(new Font(resetDataButton.getFont().getName(), Font.PLAIN, 20));
        resetDataButton.addActionListener(e->dataHandler.stopThreadsAndInitializeCharts(InitialData.IN_ORDER));
        dataResetPanel.add(resetDataButton);

        returnSingleButton.setFont(new Font(resetDataButton.getFont().getName(), Font.PLAIN, 16));
        returnSingleButton.addActionListener(e->guiHandler.setSingleMethodMode());
        dataResetPanel.add(returnSingleButton);

        return dataResetPanel;
    }
}
