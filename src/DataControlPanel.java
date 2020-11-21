import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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
        setSelectionSort();

        add(makeToggles());
        add(makeSortButtonPanel());
        add(sortButton);
    }

    public void setSelectionSort() {
        clearSortButtonAction();
        sortButton.addActionListener(e -> data.selectionSort());
        guiHandler.setTitle("Selection Sort");
    }

    public void setBubbleSort() {
        clearSortButtonAction();
        sortButton.addActionListener(e -> data.bubbleSort());
        guiHandler.setTitle("Bubble Sort");
    }

    public void setInsertSort() {
        clearSortButtonAction();
        sortButton.addActionListener(e -> data.insertionSort());
        guiHandler.setTitle("Insertion Sort");
    }

    public void setQuickSort() {
        clearSortButtonAction();
        sortButton.addActionListener(e -> data.quickSort());
        guiHandler.setTitle("Quick Sort");
    }

    public void setQuickSortLomuto() {
        clearSortButtonAction();
        sortButton.addActionListener(e->data.quickSortLomuto());
        guiHandler.setTitle("Quick Sort: Lomuto");
    }

    public void setQuickSortHoare() {
        clearSortButtonAction();
        sortButton.addActionListener(e->data.quickSortHoare());
        guiHandler.setTitle("Quick Sort: Hoare (1962)");
    }

    public void setQuickSortMedian() {
        clearSortButtonAction();
        sortButton.addActionListener(e->data.quickSortMedian());
        guiHandler.setTitle("Quick Sort: Hoare with Median-of-Three Pivot (Sedgewick)");
    }

    public void setQuickSortInsertion() {
        clearSortButtonAction();
        sortButton.addActionListener(e->data.quickSortInsertion());
        guiHandler.setTitle("Quick Sort: Hoare with Insertion Sort for Small Lists");
    }

    public void setHeapSort() {
        clearSortButtonAction();
        sortButton.addActionListener(e->data.heapSort());
        guiHandler.setTitle("Heap Sort");
    }

    public void setMergeSort() {
        clearSortButtonAction();
        sortButton.addActionListener(e->data.mergeSort());
        guiHandler.setTitle("Merge Sort");
    }

    public void setShellSort() {
        clearSortButtonAction();
        sortButton.addActionListener(e->data.shellSort());
        guiHandler.setTitle("Shell Sort");
    }

    public void setShellKnuthSort() {
        clearSortButtonAction();
        sortButton.addActionListener(e->data.shellKnuthSort());
        guiHandler.setTitle("Shell Sort using Knuth Sequence");
    }

    private void clearSortButtonAction () {
        for (ActionListener actionListener :
                sortButton.getActionListeners()) {
            sortButton.removeActionListener(actionListener);
        }
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
        JButton autoSort = new JButton("In Order");
        JButton reverseSort = new JButton("Reverse Order");
        JButton oneOut = new JButton("One Out of Order");
        JButton randomize = new JButton("Randomize");
        JButton previousData = new JButton("Previous Data");

        sortButtonPanel.setLayout(new GridLayout(8,1));

        autoSort.addActionListener(e->data.stopThread());
        reverseSort.addActionListener(e->data.stopThread());
        oneOut.addActionListener(e->data.stopThread());
        randomize.addActionListener(e->data.stopThread());
        previousData.addActionListener(e->data.stopThread());

        autoSort.addActionListener(e -> data.setSortedOrder());
        reverseSort.addActionListener(e -> data.setReverseOrder());
        oneOut.addActionListener(e -> data.setOneNotInOrder());
        randomize.addActionListener(e -> data.randomize());
        previousData.addActionListener(e -> data.retrieveOldData());

        sortButtonPanel.add(subtitle);
        sortButtonPanel.add(autoSort);
        sortButtonPanel.add(reverseSort);
        sortButtonPanel.add(oneOut);
        sortButtonPanel.add(randomize);
        sortButtonPanel.add(new JLabel());
        sortButtonPanel.add(previousData);

        return sortButtonPanel;
    }
}
