public abstract class Shell extends Sort{
    public static void sort(Data data) {
        BarGraphPanel graphPanel = data.getGraphPanel();
        int size = data.getSize();

        int gap = size / 2;
        while (gap >= 1) {
            drawPivots(data, gap, size - 1);
            sort(data, gap, size -1);
            graphPanel.unhighlightPivot();
            gap = Math.floorDiv(gap, 2);
        }
    }

    private static void sort(Data data, int gap, int upperbound) {
        for (int i = 0; i < gap; i++) {
            for (int k = i; k <= upperbound; k += gap) {
                for (int j = k; j > 0; j -= gap) {
                    if (less(data, j, j - gap)) exchange(data, j, j - gap);
                    else break;
                }
            }
        }
    }

    private static void drawPivots(Data data, int gap, int upperBound) {
        BarGraphPanel graphPanel = data.getGraphPanel();
        for (int i = 0; i <= upperBound; i += gap) graphPanel.highlightPivot(i);
    }
}
