import org.jfree.data.xy.XYSeries;

public abstract class Selection extends Sort {
    public static void sort(Data data) {
        XYSeries dataSeries = data.getDataSeries();
        BarGraphPanel barGraphPanel = data.getGraphPanel();
        int size = data.getSize();

        for (int i = 0; i < size - 1; i++) {
            barGraphPanel.highlightBounds(i, dataSeries.getItemCount() - 1);
            int min = i;
            for (int j = i + 1; j < size; j++) {
                if (less(data, j, min)) {
                    min = j;
                }
            }
            exchange(data, i, min);
            barGraphPanel.unhighlightBounds();
        }
    }
}
