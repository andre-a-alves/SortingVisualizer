import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;

public class BarGraphPanel extends ChartPanel{
    private final XYSeries pivotSeries;
    private final XYSeries boundSeries;
    private final XYSeries swapSeries;
    private final XYSeries compareSeries;
    private final XYSeries dataSeries;
    public static int delayFactor = 10;
    private final XYTextAnnotation comparisonCounterAnnotation;
    private final XYTextAnnotation swapCounterAnnotation;

    public BarGraphPanel(XYSeriesCollection dataSeriesCollection, String comparisonCounter,
                         String swapCounter) {
        super(ChartFactory.
                createXYBarChart("",
                        "",
                        false,"",
                        dataSeriesCollection));
        pivotSeries = dataSeriesCollection.getSeries("pivot");
        boundSeries = dataSeriesCollection.getSeries("bound");
        swapSeries = dataSeriesCollection.getSeries("swap");
        compareSeries = dataSeriesCollection.getSeries("compare");
        dataSeries = dataSeriesCollection.getSeries("data");
        comparisonCounterAnnotation = annotationBuilder(1, TextAnchor.BASELINE_LEFT,
                comparisonCounter);
        swapCounterAnnotation = annotationBuilder((int)dataSeries.getMaxX(),
                TextAnchor.BASELINE_RIGHT, swapCounter);

        JFreeChart dataChart = this.getChart();
        XYPlot plot = dataChart.getXYPlot();

        plot.getRangeAxis().setVisible(false);
        plot.getRangeAxis().setInverted(true);
        plot.getDomainAxis().setVisible(false);
        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinesVisible(false);
        plot.getRangeAxis().setRange(0, dataSeries.getMaxY() + 4);
        plot.addAnnotation(comparisonCounterAnnotation);
        plot.addAnnotation(swapCounterAnnotation);
        ((XYBarRenderer) plot.getRenderer()).setBarPainter(new StandardXYBarPainter());
        dataChart.getLegend().setPosition(RectangleEdge.TOP);
    }

    private XYTextAnnotation annotationBuilder(int xLocation, TextAnchor anchor, String text) {
        XYTextAnnotation annotation = new XYTextAnnotation(text, xLocation,
                dataSeries.getMaxY() + 2);
        annotation.setTextAnchor(anchor);
        annotation.setFont(new Font(annotation.getFont().getName(), Font.PLAIN,
                16));
        return annotation;
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

    public void clearHighlights() {
        unhighlightPivot();
        unhighlightBounds();
        swapSeries.clear();
        compareSeries.clear();
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

    public void setSize(int newDataMax) {
        getChart().getXYPlot().getRangeAxis().setRange(0, newDataMax + 4);
        comparisonCounterAnnotation.setX(1);
        comparisonCounterAnnotation.setY(newDataMax + 2);
        swapCounterAnnotation.setX(newDataMax);
        swapCounterAnnotation.setY(newDataMax + 2);
    }

    public void updateComparisonAnnotation(String newText) {
        comparisonCounterAnnotation.setText(newText);
    }

    public void updateSwapAnnotation(String newText) {
        swapCounterAnnotation.setText(newText);
    }

    public void configureForMultipleConditionDisplay() {
        this.getChart().getLegend().setVisible(false);
    }

    public void configureForMultipleMethodDisplay(SortingMethods method) {
        this.getChart().getLegend().setVisible(false);
        this.getChart().getTitle().setVisible(true);
        this.getChart().setTitle(method.title);
    }
}
