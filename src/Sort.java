import org.jfree.data.xy.XYSeries;

public abstract class Sort {
    public static void exchange(Data data, int indexOne, int indexTwo) {
        exchange(data, indexOne, indexTwo, true);
    }

    public static void exchange(Data data, int indexOne, int indexTwo, boolean highlight) {
        XYSeries dataSeries = data.getDataSeries();
        BarGraphPanel graphPanel = data.getGraphPanel();

        if (highlight) graphPanel.highlightSwap(indexTwo, indexOne);
        Number orig = dataSeries.getY(indexOne);
        dataSeries.updateByIndex(indexOne, dataSeries.getY(indexTwo));
        dataSeries.updateByIndex(indexTwo, orig);
        if (highlight) graphPanel.highlightSwap(indexTwo, indexOne);
    }

    public static boolean less(Data data, int firstIndex, int secondIndex) {
        return less(data, firstIndex, secondIndex, true);
    }

    public static boolean less(Data data, int firstIndex, int secondIndex, boolean highlight) {
        if (secondIndex < 0) return false;

        XYSeries dataSeries = data.getDataSeries();
        BarGraphPanel graphPanel = data.getGraphPanel();

        if (highlight) graphPanel.highlightCompare(firstIndex, secondIndex);
        return dataSeries.getY(firstIndex).intValue() < dataSeries.getY(secondIndex).intValue();
    }

}
