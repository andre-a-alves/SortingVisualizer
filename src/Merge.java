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
//        int rightHighlight = middlePoint;
        for (int k = lowerBound; k <= upperBound; k++) {
//            Sort.barGraphPanel.highlightCompare(leftHighlight, rightHighlight);
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
}
