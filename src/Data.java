import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.*;

public class Data {
    public static final int defaultSize = 32;
    public static final int minSize = 4;
    public static final int maxSize = 128;
    private int size;
    private final XYSeries dataSeries;
    private final XYSeries compareSeries;
    private final XYSeries swapSeries;
    private final XYSeries boundSeries;
    private final XYSeries pivotSeries;
    private ArrayList<Integer> savedList;
    private Thread sortingThread;
    private final BarGraphPanel graphPanel;
    private final RunnableSort sortRunnable;

    public Data(int size) {
        this.size = size;
        this.savedList = new ArrayList<>();
        dataSeries = new XYSeries("data");
        compareSeries = new XYSeries("compare");
        swapSeries = new XYSeries("swap");
        boundSeries = new XYSeries("bound");
        pivotSeries = new XYSeries("pivot");
        sortRunnable = new RunnableSort(this);

        for (int i=0;i < size; i++) dataSeries.add(i,i);

        randomize();
        saveDataAsPrevious();
        Sort.initialize(size, this);

        graphPanel = new BarGraphPanel(makeDataSeriesCollection());
    }

    public Data() {
        this(defaultSize);
    }

    public void changeSize(int newSize) {
        size = newSize;
//        Sort.setSize(newSize);
        graphPanel.setSize(size);
        randomize();
    }

    public XYSeriesCollection makeDataSeriesCollection() {
        XYSeriesCollection dataSeriesCollection = new XYSeriesCollection();
        dataSeriesCollection.addSeries(swapSeries);
        dataSeriesCollection.addSeries(dataSeries);
        dataSeriesCollection.addSeries(compareSeries);
        dataSeriesCollection.addSeries(pivotSeries);
        dataSeriesCollection.addSeries(boundSeries);

        return  dataSeriesCollection;
    }

    private void saveDataAsPrevious() {
        savedList.clear();
        for (int i = 0; i < dataSeries.getItemCount(); i++)
            savedList.add(dataSeries.getY(i).intValue());
    }

    public void setSortedOrder() {
        saveDataAsPrevious();
        dataSeries.clear();
        for (int i = 1; i <= size; i++) {
            dataSeries.add(i, i);
        }
    }

    public void setReverseOrder() {
        saveDataAsPrevious();
        dataSeries.clear();
        for (int i = 1; i <= size; i++) {
            dataSeries.add(i, size - i + 1);
        }
    }

    public void setOneNotInOrder() {
        if (size > 2) {
            int randomIndex = ThreadLocalRandom.current().nextInt(size - 2);
            setSortedOrder();
                for (int i = size - 2; i >= randomIndex + 1; i--)
                Sort.exchange(i--, i++, false);
        }
    }

    public void randomize() {
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
        sortRunnable.setSortingMethod(method);
    }

    public void selectionSort() {
        saveDataAsPrevious();
        stopThread();
        setSortMethod(SortingMethods.SELECTION);
        sortingThread = new Thread(sortRunnable);
//        sortingThread = new Thread();
        sortingThread.start();
    }

    private void sort() {
        saveDataAsPrevious();
        stopThread();
        sortingThread.start();
    }

    public void insertionSort() {
        saveDataAsPrevious();
        stopThread();
//        sortingThread = new Thread(Insertion::sort);
        setSortMethod(SortingMethods.INSERTION);
        sortingThread = new Thread(sortRunnable);
        sortingThread.start();
    }


    public void bubbleSort() {
        saveDataAsPrevious();
        stopThread();
        setSortMethod(SortingMethods.BUBBLE);
        sortingThread = new Thread(sortRunnable);
//        sortingThread = new Thread(Bubble::sort);
        sortingThread.start();
    }

    public void quickSort() {
        saveDataAsPrevious();
        stopThread();
        setSortMethod(SortingMethods.QUICK);
        sortingThread = new Thread(sortRunnable);
//        sortingThread = new Thread(Quick::sort);
        sortingThread.start();
    }

    public void quickSortLomuto() {
        saveDataAsPrevious();
        stopThread();
        setSortMethod(SortingMethods.QUICK_LOMUTO);
        sortingThread = new Thread(sortRunnable);
//        sortingThread = new Thread(QuickLomuto::sort);
        sortingThread.start();
    }

    public void quickSortHoare() {
        saveDataAsPrevious();
        stopThread();
        setSortMethod(SortingMethods.QUICK_HOARE);
        sortingThread = new Thread(sortRunnable);
//        sortingThread = new Thread(QuickHoare::sort);
        sortingThread.start();
    }

    public void quickSortMedian() {
        saveDataAsPrevious();
        stopThread();
        setSortMethod(SortingMethods.QUICK_MEDIAN);
        sortingThread = new Thread(sortRunnable);
//        sortingThread = new Thread(QuickMedian::sort);
        sortingThread.start();
    }

    public void quickSortInsertion() {
        saveDataAsPrevious();
        stopThread();
        setSortMethod(SortingMethods.QUICK_INSERTION);
        sortingThread = new Thread(sortRunnable);
//        sortingThread = new Thread(QuickInsertion::sort);
        sortingThread.start();
    }

    public void mergeSort() {
        saveDataAsPrevious();
        stopThread();
        setSortMethod(SortingMethods.MERGE);
        sortingThread = new Thread(sortRunnable);
//        sortingThread = new Thread(Merge::sort);
        sortingThread.start();
    }

    public void heapSort() {
        saveDataAsPrevious();
        stopThread();
        setSortMethod(SortingMethods.HEAP);
        sortingThread = new Thread(sortRunnable);
//        sortingThread = new Thread(Heap::sort);
        sortingThread.start();
    }

    public void shellSort() {
        saveDataAsPrevious();
        stopThread();
        setSortMethod(SortingMethods.SHELL_SHELL);
        sortingThread = new Thread(sortRunnable);
//        sortingThread = new Thread(Shell::sort);
        sortingThread.start();
    }

    public void shellKnuthSort() {
        saveDataAsPrevious();
        stopThread();
        setSortMethod(SortingMethods.SHELL_KNUTH);
        sortingThread = new Thread(sortRunnable);
//        sortingThread = new Thread(ShellKnuth::sort);
        sortingThread.start();
    }

    @Override
    public String toString() {
        return dataSeries.getItems().toString();
    }

    public void stopThread() {
        if (sortingThread != null) sortingThread.interrupt();
    }

    public static void main(String[] args) {
        Data testData = new Data();
        testData.randomize();
        System.out.println(testData);
        testData.mergeSort();
        System.out.println("merged: "+ testData.dataSeries.getItems());
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
}
