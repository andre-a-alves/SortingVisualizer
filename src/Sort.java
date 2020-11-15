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

    public static boolean less(int firstIndex, int secondIndex) {
        if (secondIndex < 0) return false;
        barGraphPanel.highlightCompare(firstIndex, secondIndex);
        return dataSeries.getY(firstIndex).intValue() < dataSeries.getY(secondIndex).intValue();
    }

    public static boolean greater(int firstIndex, int secondIndex) {
        barGraphPanel.highlightCompare(firstIndex, secondIndex);
        return dataSeries.getY(firstIndex).intValue() > dataSeries.getY(secondIndex).intValue();
    }

    public static boolean less(int firstIndex, int secondIndex, boolean delay) {
        if (delay) less(firstIndex, secondIndex);
        return dataSeries.getY(firstIndex).intValue() < dataSeries.getY(secondIndex).intValue();
    }

    public static String getVersion() {
        return "0.1";
    }

    public static void initialize(int size, Data data) {
        dataSeries = data.getDataSeriesCollection().getSeries("data");
        Sort.data = data;
        Sort.size = size;
    }

    public static void setSize(int size) {
        Sort.size = size;
        barGraphPanel.getChart().getXYPlot().getRangeAxis().setRange(0, size + 4);
    }

    public static void setBarGraphPanel(BarGraphPanel barGraphPanel) {
        Sort.barGraphPanel = barGraphPanel;
    }
}
