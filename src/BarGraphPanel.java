import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class BarGraphPanel extends ChartPanel{
    public static final int minDelayFactor = 2;
    public static final int maxDelayFactor = 100;
    public static final int defaultDelayFactor = 25;
    private final XYSeries pivotSeries;
    private final XYSeries boundSeries;
    private final XYSeries swapSeries;
    private final XYSeries compareSeries;
    private final XYSeries dataSeries;
    public static int delayFactor = 10;

    public BarGraphPanel(Data data) {
        super(ChartFactory.
                createXYBarChart("",
                        "",
                        false,"",
                        data.getDataSeriesCollection()));
        JFreeChart dataChart = this.getChart();
        dataChart.getXYPlot().getRangeAxis().setVisible(false);
        dataChart.getXYPlot().getDomainAxis().setVisible(false);
        dataChart.getXYPlot().setDomainGridlinesVisible(false);
        dataChart.getXYPlot().setRangeGridlinesVisible(false);
        dataChart.getLegend().setPosition(RectangleEdge.TOP);
//        dataChart.removeLegend();
        ((XYBarRenderer) dataChart.getXYPlot().getRenderer()).setBarPainter(new StandardXYBarPainter());

        XYSeriesCollection dataSeriesCollection = data.getDataSeriesCollection();
        pivotSeries = dataSeriesCollection.getSeries("pivot");
        boundSeries = dataSeriesCollection.getSeries("bound");
        swapSeries = dataSeriesCollection.getSeries("swap");
        compareSeries = dataSeriesCollection.getSeries("compare");
        dataSeries = dataSeriesCollection.getSeries("data");
        Sort.setBarGraphPanel(this);
        dataChart.getXYPlot().getRangeAxis().setRange(0, Sort.size + 4);
    }

    public void highlightCompare(int indexOne, int indexTwo) {
        compareSeries.add(dataSeries.getX(indexOne), dataSeries.getMaxY() + 1);
        compareSeries.add(dataSeries.getX(indexTwo), dataSeries.getMaxY() + 1);
        delay(1);
        compareSeries.clear();
    }

    public void highlightSwap(int indexOne, int indexTwo) {
        swapSeries.add(dataSeries.getX(indexOne), dataSeries.getY(indexOne));
        swapSeries.add(dataSeries.getX(indexTwo), dataSeries.getY(indexTwo));
        delay(2);
        swapSeries.clear();
    }

    public void highlightBounds(int indexOne, int indexTwo) {
        boundSeries.add(dataSeries.getX(indexOne), dataSeries.getMaxY() + 3);
        boundSeries.add(dataSeries.getX(indexTwo), dataSeries.getMaxY() + 3);
    }

    public void unhighlightBounds() {
        boundSeries.clear();
    }

    public void highlightPivot(int index) {
        pivotSeries.add(dataSeries.getX(index), (int)dataSeries.getMaxY() + 2);
    }

    public void unhighlightPivot() {
        pivotSeries.clear();
    }

    private void delay(int length) {
        try
        {
            Thread.sleep(length * delayFactor);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    public static void changeSpeed(int newDelayFactor) {
        delayFactor = newDelayFactor;
    }
}
