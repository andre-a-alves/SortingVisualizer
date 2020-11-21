import javax.swing.*;
import java.awt.*;

public class DataControlPanel extends JPanel{
    private final Data data;
    JButton sortButton;
    GuiHandler guiHandler;

    public DataControlPanel(Data data, GuiHandler guiHandler) {
        super();
        this.data = data;
        this.sortButton = new JButton("Sort");
        this.guiHandler = guiHandler;

        setLayout(new GridLayout(3,1));
        sortButton.setFont(new Font(sortButton.getFont().getName(), Font.PLAIN, 20));
        sortButton.addActionListener(e->data.sort());
        setSortType(SortingMethods.SELECTION);

        add(makeToggles());
        add(makeSortButtonPanel());
        add(sortButton);
    }

    public void setSortType(SortingMethods newMethod) {
        data.setSortMethod(newMethod);
        guiHandler.setTitle(newMethod.title);
    }

    private JPanel makeToggles() {
        JPanel togglePanel = new JPanel();
        JSlider dataSize = new JSlider(JSlider.HORIZONTAL, Data.minSize, Data.maxSize,
                Data.defaultSize);
        JSlider animationSpeed = new JSlider(JSlider.HORIZONTAL, BarGraphPanel.minDelayFactor,
                BarGraphPanel.maxDelayFactor,
                BarGraphPanel.maxDelayFactor - BarGraphPanel.defaultDelayFactor);

        togglePanel.add(new JLabel("Data Size"));
        togglePanel.add(dataSize);
        togglePanel.add(new JLabel("Animation Speed"));
        togglePanel.add(animationSpeed);

        togglePanel.setLayout(new GridLayout(4,1));

        dataSize.addChangeListener(e -> {
            if(!dataSize.getValueIsAdjusting()) {
                data.stopThreadAndFixChart(Data.DataActions.IN_ORDER);
                data.changeSize(dataSize.getValue());
            }
        });
        animationSpeed.addChangeListener(e -> {
            if(!animationSpeed.getValueIsAdjusting()) {
                BarGraphPanel.changeSpeed(BarGraphPanel.maxDelayFactor + 5 - animationSpeed.getValue());
            }
        });

        return togglePanel;
    }

    private JPanel makeSortButtonPanel() {
        JPanel sortButtonPanel = new JPanel();
        JLabel subtitle = new JLabel("Pre-Sort Data:");
        sortButtonPanel.add(subtitle);
        sortButtonPanel.setLayout(new GridLayout(8,1));

        for (Data.DataActions action : Data.DataActions.values()) {
            JButton actionButton = new JButton(action.title);
            actionButton.addActionListener(e->data.stopThreadAndFixChart(action));
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
