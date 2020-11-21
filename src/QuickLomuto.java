
public class QuickLomuto extends Sort {

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
        int i = lowerBound;
        for (int j = lowerBound; j <= upperBound; j++) {
            if (less(j, upperBound)) exchange(i++, j);
        }
        exchange(i,upperBound);
        Sort.barGraphPanel.unhighlightBounds();
        Sort.barGraphPanel.unhighlightPivot();
        return i;
    }

    public static void sort(Data data) {
        sort(data, 0, data.getDataSeries().getItemCount() - 1);
    }

    private static void sort(Data data, int lowerBound, int upperBound) {
        if (upperBound - lowerBound < 1) return;
        int inPlace = partition(data, lowerBound, upperBound);
        sort(data, lowerBound, inPlace - 1);
        sort(data, inPlace + 1, upperBound);
    }

    private static int partition(Data data, int lowerBound, int upperBound) {
        BarGraphPanel graphPanel = data.getGraphPanel();

        graphPanel.highlightBounds(lowerBound,upperBound);
        graphPanel.highlightPivot(upperBound);
        int i = lowerBound;
        for (int j = lowerBound; j <= upperBound; j++) {
            if (less(data, j, upperBound)) exchange(data, i++, j);
        }
        exchange(data, i,upperBound);
        graphPanel.unhighlightBounds();
        graphPanel.unhighlightPivot();
        return i;
    }
}
