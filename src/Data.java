import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.*;

public class Data {
    public static final int defaultSize = 8;
    public static final int minSize = 2;
    public static final int maxSize = 64;
    private int size;
    private final XYSeries dataSeries;
    private final XYSeries compareSeries;
    private final XYSeries swapSeries;
    private final XYSeries boundSeries;
    private final XYSeries pivotSeries;
    private ArrayList<Integer> savedList;
    private Thread sortingThread;

    public Data(int size) {
        this.size = size;
        this.savedList = new ArrayList<>();
        dataSeries = new XYSeries("data");
        compareSeries = new XYSeries("compare");
        swapSeries = new XYSeries("swap");
        boundSeries = new XYSeries("bound");
        pivotSeries = new XYSeries("pivot");

        for (int i=0;i < size; i++) dataSeries.add(i,i);

        randomize();
        saveDataAsPrevious();
        Sort.initialize(size, this);
    }

    public Data() {
        this(defaultSize);
    }

    public void changeSize(int newSize) {
        size = newSize;
        Sort.setSize(newSize);
        randomize();
    }

    public XYSeriesCollection getDataSeriesCollection() {
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

    public void selectionSort() {
        saveDataAsPrevious();
        sortingThread = new Thread(Selection::sort);
        sortingThread.start();
    }

    public void insertionSort() {
        saveDataAsPrevious();
        sortingThread = new Thread(Insertion::sort);
        sortingThread.start();
    }


    public void bubbleSort() {
        saveDataAsPrevious();
        sortingThread = new Thread(Bubble::sort);
        sortingThread.start();
    }

    public void quickSort() {
        saveDataAsPrevious();
        sortingThread = new Thread(Quick::sort);
        sortingThread.start();
    }

    public void quickSortLomuto() {
        saveDataAsPrevious();
        sortingThread = new Thread(QuickLomuto::sort);
        sortingThread.start();
    }

    public void quickSortHoare() {
        saveDataAsPrevious();
        sortingThread = new Thread(QuickHoare::sort);
        sortingThread.start();
    }

    public void quickSortMedian() {
        saveDataAsPrevious();
        sortingThread = new Thread(QuickMedian::sort);
        sortingThread.start();
    }

    public void quickSortInsertion() {
        saveDataAsPrevious();
        sortingThread = new Thread(QuickInsertion::sort);
        sortingThread.start();
    }

    public void mergeSort() {
        saveDataAsPrevious();
        sortingThread = new Thread(Merge::sort);
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
}
