import org.jfree.data.xy.XYSeries;

import java.util.concurrent.ThreadLocalRandom;

public abstract class QuickHoare extends Sort {
    public static void sort(Data data) {
        sort(data, 0, data.getDataSeries().getItemCount() - 1);
    }

    private static void sort(Data data, int lowerBound, int upperBound) {
        if (upperBound - lowerBound < 1) return;
        int inPlace = partition(data, lowerBound, upperBound);
        sort(data, lowerBound, inPlace);
        sort(data, inPlace + 1, upperBound);

    }

    protected static int partition(Data data, int lowerBound, int upperBound) {
        BarGraphPanel graphPanel = data.getGraphPanel();
        XYSeries dataSeries = data.getDataSeries();

        graphPanel.highlightBounds(lowerBound, upperBound);
        int pivotIndex = ThreadLocalRandom.current().nextInt(lowerBound, upperBound);
        int pivot = dataSeries.getY(pivotIndex).intValue();
        graphPanel.highlightPivot(pivotIndex);
        if (upperBound - lowerBound == 1 && less(data, lowerBound,upperBound)) {
            graphPanel.unhighlightBounds();
            graphPanel.unhighlightPivot();
            return lowerBound;
        }
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
}
