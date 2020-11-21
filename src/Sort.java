import org.jfree.data.xy.XYSeries;

public abstract class Sort {
    protected static Data data;
    protected static XYSeries dataSeries;
    protected static int size;
    protected static BarGraphPanel barGraphPanel;


    public static void exchange(int indexOne, int indexTwo) {
        barGraphPanel.highlightSwap(indexTwo, indexOne);
        Number orig = dataSeries.getY(indexOne);
        dataSeries.updateByIndex(indexOne, dataSeries.getY(indexTwo));
        dataSeries.updateByIndex(indexTwo, orig);
        barGraphPanel.highlightSwap(indexTwo, indexOne);
    }

    public static void exchange(int indexOne, int indexTwo, boolean delay) {
        if (delay) exchange(indexOne, indexTwo);
        else {
            Number orig = dataSeries.getY(indexOne);
            dataSeries.updateByIndex(indexOne, dataSeries.getY(indexTwo));
            dataSeries.updateByIndex(indexTwo, orig);
        }
    }

    public static void exchange(Data data, int indexOne, int indexTwo) {
        exchange(data, indexOne, indexTwo, true);
    }

    public static void exchange(Data data, int indexOne, int indexTwo, boolean highlight) {
        XYSeries dataSeries = data.getDataSeries();
        BarGraphPanel graphPanel = data.getGraphPanel();

        if (highlight) barGraphPanel.highlightSwap(indexTwo, indexOne);
        Number orig = dataSeries.getY(indexOne);
        dataSeries.updateByIndex(indexOne, dataSeries.getY(indexTwo));
        dataSeries.updateByIndex(indexTwo, orig);
        if (highlight) barGraphPanel.highlightSwap(indexTwo, indexOne);
    }

    public static boolean less(int firstIndex, int secondIndex) {
        if (secondIndex < 0) return false;
        barGraphPanel.highlightCompare(firstIndex, secondIndex);
        return dataSeries.getY(firstIndex).intValue() < dataSeries.getY(secondIndex).intValue();
    }

    public static boolean less(int firstIndex, int secondIndex, boolean delay) {
        if (delay) less(firstIndex, secondIndex);
        return dataSeries.getY(firstIndex).intValue() < dataSeries.getY(secondIndex).intValue();
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

    public static String getVersion() {
        return "0.1";
    }

    public static void initialize(int size, Data data) {
        dataSeries = data.makeDataSeriesCollection().getSeries("data");
        Sort.data = data;
        Sort.size = size;
    }

//    public static void setSize(int size) {
//        Sort.size = size;
//        barGraphPanel.getChart().getXYPlot().getRangeAxis().setRange(0, size + 4);
//    }

    public static void setBarGraphPanel(BarGraphPanel barGraphPanel) {
        Sort.barGraphPanel = barGraphPanel;
    }
}
