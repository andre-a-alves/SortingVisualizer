import org.jfree.data.xy.XYSeries;

import java.sql.Array;
import java.util.ArrayList;

public class Merge extends Sort {

    public static void sort() {
        sort(0, dataSeries.getItemCount() - 1);
    }

    private static void sort(int lowerBound, int upperBound) {
        if (lowerBound < upperBound) {
            int middlePoint = lowerBound + (upperBound - lowerBound) / 2;
            sort(lowerBound, middlePoint);
            sort(middlePoint + 1, upperBound);
            merge(lowerBound, middlePoint + 1, upperBound);
        }
    }

    private static void merge(int lowerBound, int middlePoint, int upperBound) {
        int[] auxillary = new int[upperBound - lowerBound + 1];
        Sort.barGraphPanel.highlightBounds(lowerBound,upperBound);
        Sort.barGraphPanel.highlightPivot(middlePoint);

        if (upperBound - lowerBound == 1) {
            if (less(upperBound,lowerBound)) exchange(lowerBound, upperBound);
            Sort.barGraphPanel.unhighlightPivot();
            Sort.barGraphPanel.unhighlightBounds();
            return;
        }

        int leftIndex = 0;
        int rightIndex = middlePoint - lowerBound;

        for (int i = lowerBound; i < middlePoint; i++) auxillary[leftIndex++] =
                dataSeries.getY(i).intValue();
        for (int j = middlePoint; j <= upperBound; j++) auxillary[rightIndex++] =
                dataSeries.getY(middlePoint + upperBound - j).intValue();

        leftIndex = 0;
        int leftHighlight = lowerBound;
        rightIndex = auxillary.length - 1;
        for (int k = lowerBound; k <= upperBound; k++) {
            if (auxillary[rightIndex] < auxillary[leftIndex]) {
                dataSeries.updateByIndex(k, auxillary[rightIndex--]);
                Sort.barGraphPanel.highlightSwap(leftHighlight++, leftHighlight);
            } else {
                dataSeries.updateByIndex(k, auxillary[leftIndex++]);
                Sort.barGraphPanel.highlightSwap(leftHighlight, leftHighlight++);
            }
        }

        Sort.barGraphPanel.unhighlightPivot();
        Sort.barGraphPanel.unhighlightBounds();
    }


    public static void sort(Data data) {
        sort(data, 0, data.getDataSeries().getItemCount() - 1);
    }

    private static void sort(Data data, int lowerBound, int upperBound) {
        if (lowerBound < upperBound) {
            int middlePoint = lowerBound + (upperBound - lowerBound) / 2;
            sort(data, lowerBound, middlePoint);
            sort(data, middlePoint + 1, upperBound);
            merge(data, lowerBound, middlePoint + 1, upperBound);
        }
    }

    private static void merge(Data data, int lowerBound, int middlePoint, int upperBound) {
        BarGraphPanel graphPanel = data.getGraphPanel();
        XYSeries dataSeries = data.getDataSeries();

        int[] auxiliary = new int[upperBound - lowerBound + 1];
        graphPanel.highlightBounds(lowerBound,upperBound);
        graphPanel.highlightPivot(middlePoint);

        if (upperBound - lowerBound == 1) {
            if (less(data, upperBound,lowerBound)) exchange(data, lowerBound, upperBound);
            graphPanel.unhighlightPivot();
            graphPanel.unhighlightBounds();
            return;
        }

        int leftIndex = 0;
        int rightIndex = middlePoint - lowerBound;

        for (int i = lowerBound; i < middlePoint; i++) auxiliary[leftIndex++] =
                dataSeries.getY(i).intValue();
        for (int j = middlePoint; j <= upperBound; j++) auxiliary[rightIndex++] =
                dataSeries.getY(middlePoint + upperBound - j).intValue();

        leftIndex = 0;
        int leftHighlight = lowerBound;
        rightIndex = auxiliary.length - 1;
        for (int k = lowerBound; k <= upperBound; k++) {
            if (auxiliary[rightIndex] < auxiliary[leftIndex]) {
                dataSeries.updateByIndex(k, auxiliary[rightIndex--]);
                graphPanel.highlightSwap(leftHighlight++, leftHighlight);
            } else {
                dataSeries.updateByIndex(k, auxiliary[leftIndex++]);
                graphPanel.highlightSwap(leftHighlight, leftHighlight++);
            }
        }

        graphPanel.unhighlightPivot();
        graphPanel.unhighlightBounds();
    }
}
