
public class Quick extends Sort {

    public static void sort() {
        sort(0, dataSeries.getItemCount() - 1);
    }

    private static void sort(int lowerBound, int upperBound) {
        if (upperBound - lowerBound < 1) return;
        int inPlace = partition(lowerBound, upperBound);
        sort(lowerBound, inPlace - 1);
        sort(inPlace + 1, upperBound);
    }

    private static int partition(int lowerBound, int upperBound) {
        Sort.barGraphPanel.highlightBounds(lowerBound,upperBound);
        Sort.barGraphPanel.highlightPivot(upperBound);
        int movingLeft = lowerBound - 1;
        int movingRight = upperBound;
        while (true) {
            while (less(++movingLeft, upperBound)) if (movingLeft == upperBound) break;
            while (less(upperBound, --movingRight)) if (movingRight == lowerBound) break;
            if (movingLeft >= movingRight) break;
            exchange(movingLeft, movingRight);
        }
        exchange(movingLeft, upperBound);
        Sort.barGraphPanel.unhighlightBounds();
        Sort.barGraphPanel.unhighlightPivot();
        return movingLeft;
    }
}
