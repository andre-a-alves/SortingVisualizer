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

    public void setSortType(SortingMethods newMethod) {
        switch (newMethod) {
            case MERGE_TOPDOWN:
            case MERGE_BOTTOMUP:
            case MERGE_SENTINEL:
            case MERGE_BOTTOM_IN_PLACE:
                JOptionPane.showMessageDialog(this,
                        "Sorry, but there is a known bug where this application cannot" +
                                "yet support this feature for merge sort. If you wish to have" +
                                "this feature developed, please contact the author, whose " +
                                "contact information can be found in Help->About");
                break;
            default:
                dataHandler.setBehavior(newMethod);
                guiHandler.setTitle(newMethod.title);
        }
    }
}
