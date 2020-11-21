import org.jfree.data.xy.XYSeries;

import java.util.concurrent.ThreadLocalRandom;

public class QuickMedian extends Sort {

    public static void sort() {
        sort(0, dataSeries.getItemCount() - 1);
    }

    private static void sort(int lowerBound, int upperBound) {
        if (upperBound - lowerBound < 1) return;
        int inPlace = partition(lowerBound, upperBound);
        sort(lowerBound, inPlace);
        sort(inPlace + 1, upperBound);

    }

    private static int partition(int lowerBound, int upperBound) {
        Sort.barGraphPanel.highlightBounds(lowerBound, upperBound);
        if (upperBound - lowerBound == 1 && less(lowerBound,upperBound)) {
            Sort.barGraphPanel.unhighlightBounds();
            return lowerBound;
        }
        int pivotIndex, pivot;
        if (upperBound - lowerBound > 1) {
            pivotIndex = medianIndex(lowerBound, upperBound,
                    lowerBound + (upperBound - lowerBound) / 2);
        } else pivotIndex = upperBound;
        pivot = dataSeries.getY(pivotIndex).intValue();
        Sort.barGraphPanel.highlightPivot(pivotIndex);

        int leftMover = lowerBound - 1;
        int rightMover = upperBound + 1;
        while (true) {
            do {
                leftMover++;
            } while (less(leftMover, pivotIndex));
            do {
                rightMover--;
            }
            while (rightMover >= lowerBound && less(pivotIndex,
                    rightMover));
            if (leftMover > rightMover) {
                Sort.barGraphPanel.unhighlightBounds();
                Sort.barGraphPanel.unhighlightPivot();
                return rightMover;
            }
            if (dataSeries.getY(leftMover).intValue() == pivot) pivotIndex = rightMover;
            if (dataSeries.getY(rightMover).intValue() == pivot) pivotIndex = leftMover;
            exchange(leftMover, rightMover);
        }
    }

    private static int medianIndex(int indexOne, int indexTwo, int indexThree) {
        if (less(indexOne,indexTwo)) {
            if (less(indexTwo,indexThree)) return indexTwo;
            if (less(indexOne, indexThree)) return indexThree;
            return indexOne;
        } else {
            if (less(indexOne, indexThree)) return indexOne;
            if (less(indexTwo, indexThree)) return indexThree;
            return indexTwo;
        }
    }

    public static void sort(Data data) {
        sort(data, 0, data.getDataSeries().getItemCount() - 1);
    }

    private static void sort(Data data, int lowerBound, int upperBound) {
        if (upperBound - lowerBound < 1) return;
        int inPlace = partition(data, lowerBound, upperBound);
        sort(data, lowerBound, inPlace);
        sort(data, inPlace + 1, upperBound);

    }

    private static int partition(Data data, int lowerBound, int upperBound) {
        BarGraphPanel graphPanel = data.getGraphPanel();
        XYSeries dataSeries = data.getDataSeries();

        graphPanel.highlightBounds(lowerBound, upperBound);
        if (upperBound - lowerBound == 1 && less(data, lowerBound,upperBound)) {
            graphPanel.unhighlightBounds();
            return lowerBound;
        }
        int pivotIndex, pivot;
        if (upperBound - lowerBound > 1) {
            pivotIndex = medianIndex(data, lowerBound, upperBound,
                    lowerBound + (upperBound - lowerBound) / 2);
        } else pivotIndex = upperBound;
        pivot = dataSeries.getY(pivotIndex).intValue();
        graphPanel.highlightPivot(pivotIndex);

        int leftMover = lowerBound - 1;
        int rightMover = upperBound + 1;
        while (true) {
            do {
                leftMover++;
            } while (less(data, leftMover, pivotIndex));
            do {
                rightMover--;
            }
            while (rightMover >= lowerBound && less(data, pivotIndex,
                    rightMover));
            if (leftMover > rightMover) {
                graphPanel.unhighlightBounds();
                graphPanel.unhighlightPivot();
                return rightMover;
            }
            if (dataSeries.getY(leftMover).intValue() == pivot) pivotIndex = rightMover;
            if (dataSeries.getY(rightMover).intValue() == pivot) pivotIndex = leftMover;
            exchange(data, leftMover, rightMover);
        }
    }

    private static int medianIndex(Data data, int indexOne, int indexTwo, int indexThree) {
        if (less(data, indexOne,indexTwo)) {
            if (less(data, indexTwo,indexThree)) return indexTwo;
            if (less(data, indexOne, indexThree)) return indexThree;
            return indexOne;
        } else {
            if (less(data, indexOne, indexThree)) return indexOne;
            if (less(data, indexTwo, indexThree)) return indexThree;
            return indexTwo;
        }
    }
}
