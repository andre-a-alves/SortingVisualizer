import org.jfree.data.xy.XYSeries;

public abstract class Sort {
    public static void exchange(Data data, int indexOne, int indexTwo) {
        exchange(data, indexOne, indexTwo, true);
    }

    public static void exchange(Data firstData, int firstIndex, Data secondData, int secondIndex) {
        XYSeries firstSeries = firstData.getDataSeries();
        XYSeries secondSeries = secondData.getDataSeries();
        BarGraphPanel firstGraphPanel = firstData.getGraphPanel();
        BarGraphPanel secondGraphPanel = secondData.getGraphPanel();

        firstData.increaseSwapCount();
        secondData.increaseSwapCount();
        firstGraphPanel.highlightSwap(firstIndex,firstIndex);
        secondGraphPanel.highlightSwap(secondIndex, secondIndex);

        Number orig = firstSeries.getY(firstIndex);
        firstSeries.updateByIndex(firstIndex, secondSeries.getY(secondIndex));
        secondSeries.updateByIndex(secondIndex, orig);

        firstGraphPanel.highlightSwap(firstIndex, firstIndex);
        secondGraphPanel.highlightSwap(secondIndex, secondIndex);
    }

    public static void exchange(Data data, int indexOne, int indexTwo, boolean highlight) {
        XYSeries dataSeries = data.getDataSeries();
        BarGraphPanel graphPanel = data.getGraphPanel();

        data.increaseSwapCount();
        if (highlight) graphPanel.highlightSwap(indexTwo, indexOne);
        Number orig = dataSeries.getY(indexOne);
        dataSeries.updateByIndex(indexOne, dataSeries.getY(indexTwo));
        dataSeries.updateByIndex(indexTwo, orig);
        if (highlight) graphPanel.highlightSwap(indexTwo, indexOne);
    }

    public static boolean less(Data data, int firstIndex, int secondIndex) {
        return less(data, firstIndex, secondIndex, true);
    }

    public static boolean less(Data data, int firstIndex, int secondIndex, Data mainData) {
        mainData.increaseComparisonCount();
        return less(data, firstIndex, secondIndex, true);
    }

    public static boolean less(Data data, int firstIndex, int secondIndex, boolean highlight) {
        data.increaseComparisonCount();
        if (secondIndex < 0) return false;

        XYSeries dataSeries = data.getDataSeries();
        BarGraphPanel graphPanel = data.getGraphPanel();

        if (highlight) graphPanel.highlightCompare(firstIndex, secondIndex);
        return dataSeries.getY(firstIndex).intValue() < dataSeries.getY(secondIndex).intValue();
    }
}
