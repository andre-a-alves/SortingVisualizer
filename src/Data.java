import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.*;

public class Data {
    private int size;
    private final XYSeries dataSeries;
    private final XYSeries compareSeries;
    private final XYSeries swapSeries;
    private ArrayList<Integer> savedList;
    private Thread sortingThread;
    private final BarGraphPanel graphPanel;
    private final RunnableSort sortRunnable;
    private int comparisonCounter;
    private String comparisonCounterString;
    private int swapCounter;
    private String copyCounterString;
    private MergeViewer mergeViewer;

    public Data(int size, boolean mergeFlag, int height) {
        this.size = size;
        this.savedList = new ArrayList<>();
        dataSeries = new XYSeries("data");
        compareSeries = new XYSeries("compare");
        swapSeries = new XYSeries("swap");
        sortRunnable = new RunnableSort(this);
        comparisonCounter = 0;
        swapCounter = 0;
        comparisonCounterString = "";
        copyCounterString = "";

        for (int i=0;i < size; i++) dataSeries.add(i,i);

        randomize();
        saveDataAsPrevious();

        graphPanel = new BarGraphPanel(makeDataSeriesCollection(), comparisonCounterString, copyCounterString);
        resetCounters();
        if (mergeFlag) {
            mergeViewer = new MergeViewer(this);
            graphPanel.setRangeHeight(height);
//            graphPanel.getChart().getTitle().setVisible(true);
            graphPanel.getChart().setTitle("Auxiliary Data for Merge Sort");
        }
    }

    public Data(int size) {
        this(size, false, size);
    }

    public Data() {
        this(SingleDataHandler.DEFAULT_SIZE);
    }

    public void closeMergeWindow() {
        mergeViewer.closeWindow();
    }

    public void resetCounters() {
        swapCounter = 0;
        comparisonCounter = 0;
        updateCopyCounterString();
        updateComparisonCounterString();
    }

    public void increaseComparisonCount() {
        comparisonCounter++;
        updateComparisonCounterString();
    }

    public void increaseSwapCount() {
        swapCounter += 3;
        updateCopyCounterString();
    }

    public void increaseCopyCount() {
        swapCounter++;
        updateCopyCounterString();
    }

    private void updateComparisonCounterString() {
        comparisonCounterString = "Number of Comparison Operations: " + comparisonCounter;
        graphPanel.updateComparisonAnnotation(comparisonCounterString);
    }

    private void updateCopyCounterString() {
        copyCounterString = "Number of Copy Operations: " + swapCounter;
        graphPanel.updateCopyAnnotation(copyCounterString);
    }

    public void changeSize(int newSize) {
        size = newSize;
        graphPanel.setSize(size);
        randomize();
    }

    public XYSeriesCollection makeDataSeriesCollection() {
        XYSeriesCollection dataSeriesCollection = new XYSeriesCollection();
        dataSeriesCollection.addSeries(swapSeries);
        dataSeriesCollection.addSeries(dataSeries);
        dataSeriesCollection.addSeries(compareSeries);

        return  dataSeriesCollection;
    }

    private void saveDataAsPrevious() {
        savedList.clear();
        for (int i = 0; i < dataSeries.getItemCount(); i++)
            savedList.add(dataSeries.getY(i).intValue());
    }

    private void setSortedOrder() {
        saveDataAsPrevious();
        dataSeries.clear();
        for (int i = 1; i <= size; i++) {
            dataSeries.add(i, i);
        }
    }

    private void setReverseOrder() {
        saveDataAsPrevious();
        dataSeries.clear();
        for (int i = 1; i <= size; i++) {
            dataSeries.add(i, size - i + 1);
        }
    }

    private void setOneNotInOrder() {
        if (size > 2) {
            int randomIndex = ThreadLocalRandom.current().nextInt(size - 2);
            setSortedOrder();
                for (int i = size - 2; i >= randomIndex + 1; i--)
                Sort.exchange(this, i--, i++, false);
        }
    }

    private void randomize() {
        saveDataAsPrevious();
        ArrayList<Integer> newList = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            newList.add(i);
        }
        Collections.shuffle(newList, new Random(System.currentTimeMillis()));

        dataSeries.clear();
        for (int i = 0; i < size; i++) {
            dataSeries.add(++i, newList.get(--i));
        }
    }

    public void retrieveOldData() {
        ArrayList<Integer> currentData = new ArrayList<>();
        ArrayList<Integer> savedData = (ArrayList<Integer>)savedList.clone();
        for (int i = 0; i < dataSeries.getItemCount(); i++) {
            currentData.add(dataSeries.getY(i).intValue());
        }
        changeSize(savedList.size());

        dataSeries.clear();
        for (int i = 0; i < savedData.size(); i++) dataSeries.add(i, savedData.get(i));

        savedList = currentData;
    }

    public void setSortMethod(SortingMethods method) {
        stopThread();
        sortRunnable.setSortingMethod(method);
    }

    public void sort() {
        saveDataAsPrevious();
        stopThread();
        sortingThread = new Thread(sortRunnable);
        resetCounters();
        sortingThread.start();
    }

    @Override
    public String toString() {
        return dataSeries.getItems().toString();
    }

    public void stopThread() {
        if (sortingThread == null) return;
        if (!sortingThread.isAlive()) return;
        sortingThread.interrupt();
        retrieveOldData();
        graphPanel.clearHighlights();
    }

    public XYSeries getDataSeries() {
        return dataSeries;
    }

    public BarGraphPanel getGraphPanel() {
        return graphPanel;
    }

    public int getSize() {
        return size;
    }

    public void setDataFromList(ArrayList<Integer> newData) {
        stopThread();
        saveDataAsPrevious();
        dataSeries.clear();
        size = newData.size();
        for (int i = 0; i < size; i++) {
            dataSeries.add(i, newData.get(i));
        }
    }

    public void setPreviousDataFromList(ArrayList<Integer> newData) {
        savedList.clear();
        savedList.addAll(newData);
    }

    public ArrayList<Integer> getCurrentDataList() {
        ArrayList<Integer> currentData = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            currentData.add(dataSeries.getY(i).intValue());
        }
        return currentData;
    }

    public void stopThreadAndInitializeChart(InitialData followOnAction) {
        ArrayList<Integer> previousData = (ArrayList<Integer>)savedList.clone();
        stopThread();
        resetCounters();
        switch (followOnAction) {
            case REVERSE:
                setReverseOrder();
                break;
            case IN_ORDER:
                setSortedOrder();
                break;
            case ONE_OUT_OF_ORDER:
                setOneNotInOrder();
                break;
            case PREVIOUS:
                savedList = previousData;
                retrieveOldData();
                break;
            case RANDOM:
            default:
                randomize();
        }
    }

}
