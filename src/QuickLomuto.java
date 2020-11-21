
public abstract class QuickLomuto extends Sort {
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
