import javax.swing.*;
import java.awt.*;
import java.util.Dictionary;
import java.util.Hashtable;

public abstract class DataControlPanel extends JPanel {
    protected final GuiHandler guiHandler;
    protected final DataHandler dataHandler;

    public DataControlPanel(GuiHandler guiHandler, DataHandler dataHandler) {
        super();
        this.dataHandler = dataHandler;
        this.guiHandler = guiHandler;
        setLayout(new GridLayout(3,1));

        add(makeToggles());
    }

    private JPanel makeToggles() {
        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        labels.put(DataHandler.MIN_SIZE, new JLabel(String.valueOf(DataHandler.MIN_SIZE)));
        labels.put(64, new JLabel("64"));
        labels.put(96, new JLabel("96"));
        labels.put(DataHandler.DEFAULT_SIZE, new JLabel(String.valueOf(DataHandler.DEFAULT_SIZE)));
        labels.put(DataHandler.MAX_SIZE, new JLabel(String.valueOf(DataHandler.MAX_SIZE)));
        JPanel togglePanel = new JPanel();
        JSlider dataSize = new JSlider(JSlider.HORIZONTAL, SingleDataHandler.MIN_SIZE,
                SingleDataHandler.MAX_SIZE,
                SingleDataHandler.DEFAULT_SIZE);
        dataSize.setMajorTickSpacing(32);
        dataSize.setMinorTickSpacing(16);
        dataSize.setLabelTable(labels);
        dataSize.setPaintLabels(true);
        dataSize.setPaintTicks(true);
        JSlider animationSpeed = new JSlider(JSlider.HORIZONTAL, SingleDataHandler.MIN_DELAY_FACTOR,
                SingleDataHandler.MAX_DELAY_FACTOR,
                SingleDataHandler.DEFAULT_DELAY_FACTOR);
        animationSpeed.setInverted(true);
        animationSpeed.setMajorTickSpacing((DataHandler.MAX_DELAY_FACTOR - DataHandler.MIN_DELAY_FACTOR) / 4);
        animationSpeed.setMinorTickSpacing((DataHandler.MAX_DELAY_FACTOR - DataHandler.MIN_DELAY_FACTOR) / 8);
        animationSpeed.setPaintTicks(true);

        togglePanel.add(new JLabel("Data Size"));
        togglePanel.add(dataSize);
        togglePanel.add(new JLabel("Animation Speed"));
        togglePanel.add(animationSpeed);

        togglePanel.setLayout(new GridLayout(4,1));

        dataSize.addChangeListener(e -> {
            if(!dataSize.getValueIsAdjusting()) {
                dataHandler.stopThreadsAndInitializeCharts(InitialData.IN_ORDER);
                dataHandler.setDataSize(dataSize.getValue());
            }
        });
        animationSpeed.addChangeListener(e -> {
            if(!animationSpeed.getValueIsAdjusting()) {
                dataHandler.setDelayFactor(animationSpeed.getValue());
            }
        });

        return togglePanel;
    }

    public void setSortType(SortingMethods newMethod) {
        dataHandler.setBehavior(newMethod);
        guiHandler.setTitle(newMethod.title);
    }
}
