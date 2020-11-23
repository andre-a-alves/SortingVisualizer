import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.ui.RectangleAnchor;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.ArrayList;

public class BarGraphPanel extends ChartPanel{
    private final XYSeries swapSeries;
    private final XYSeries compareSeries;
    private final XYSeries dataSeries;
    public static int delayFactor = 10;
    private final XYTextAnnotation comparisonCounterAnnotation;
    private final XYTextAnnotation copyCounterAnnotation;
    private final ValueMarker lowerBound;
    private final ValueMarker upperBound;
    private final ValueMarker pivot;
    private final XYPlot plot;
    private final ArrayList<ValueMarker> sliceMarkers;
    private int annotationLocation = 2;

    public BarGraphPanel(XYSeriesCollection dataSeriesCollection, String comparisonCounter,
                         String copyCounter) {
        super(ChartFactory.
                createXYBarChart("",
                        "",
                        false,"",
                        dataSeriesCollection));
        swapSeries = dataSeriesCollection.getSeries("swap");
        compareSeries = dataSeriesCollection.getSeries("compare");
        dataSeries = dataSeriesCollection.getSeries("data");
        comparisonCounterAnnotation = annotationBuilder(1, TextAnchor.BASELINE_LEFT,
                comparisonCounter);
        copyCounterAnnotation = annotationBuilder((int)dataSeries.getMaxX(),
                TextAnchor.BASELINE_RIGHT, copyCounter);
        lowerBound = new ValueMarker(1);
        upperBound = new ValueMarker(dataSeries.getItemCount());
        pivot = new ValueMarker(0);
        sliceMarkers = new ArrayList<>();


        JFreeChart dataChart = this.getChart();
        plot = dataChart.getXYPlot();
        dataChart.getLegend().setPosition(RectangleEdge.TOP);
        initializePlot();
        initializeBounds();
    }

    private void initializeBounds() {
        lowerBound.setLabel("Lower Bound");
        lowerBound.setLabelAnchor(RectangleAnchor.CENTER);
        lowerBound.setLabelTextAnchor(TextAnchor.BOTTOM_CENTER);
        lowerBound.setPaint(Color.BLACK);

        upperBound.setLabel("Upper Bound");
        upperBound.setLabelAnchor(RectangleAnchor.CENTER);
        upperBound.setLabelTextAnchor(TextAnchor.BOTTOM_CENTER);
        upperBound.setPaint(Color.BLACK);

        pivot.setLabel("Pivot");
        pivot.setLabelAnchor(RectangleAnchor.CENTER);
        pivot.setLabelTextAnchor(TextAnchor.TOP_CENTER);
        pivot.setPaint(Color.BLACK);
    }

    private void initializePlot() {
        plot.getRangeAxis().setVisible(false);
        plot.getRangeAxis().setInverted(true);
        plot.getDomainAxis().setVisible(false);
        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinesVisible(false);
        plot.getRangeAxis().setRange(0, dataSeries.getMaxY() + 4);
        plot.addAnnotation(comparisonCounterAnnotation);
        plot.addAnnotation(copyCounterAnnotation);
        ((XYBarRenderer) plot.getRenderer()).setBarPainter(new StandardXYBarPainter());
    }

    private XYTextAnnotation annotationBuilder(int xLocation, TextAnchor anchor, String text) {
        XYTextAnnotation annotation = new XYTextAnnotation(text, xLocation,
                dataSeries.getMaxY() + annotationLocation++);
        annotation.setTextAnchor(anchor);
        annotation.setFont(new Font(annotation.getFont().getName(), Font.PLAIN,
                16));
        return annotation;
    }

    public void highlightCompare(int indexOne, int indexTwo) {
        compareSeries.add(dataSeries.getX(indexOne), dataSeries.getMaxY() + 1);
        compareSeries.add(dataSeries.getX(indexTwo), dataSeries.getMaxY() + 1);
        delay(2);
        compareSeries.clear();
    }

    public void highlightCopy(int index) {
        swapSeries.add(dataSeries.getX(index), dataSeries.getY(index));
        delay(1);
        swapSeries.clear();
    }

    public void highlightSwap(int indexOne, int indexTwo) {
        swapSeries.add(dataSeries.getX(indexOne), dataSeries.getY(indexOne));
        swapSeries.add(dataSeries.getX(indexTwo), dataSeries.getY(indexTwo));
        delay(1);
        swapSeries.clear();
    }

    public void highlightBounds(int indexOne, int indexTwo) {
        lowerBound.setValue(indexOne + 1);
        upperBound.setValue(indexTwo + 1);
        plot.addDomainMarker(lowerBound);
        plot.addDomainMarker(upperBound);
    }

    public void unhighlightBounds() {
        plot.removeDomainMarker(lowerBound);
        plot.removeDomainMarker(upperBound);
    }

    public void highlightPivot(int index) {
        pivot.setValue(index + 1);
        plot.addDomainMarker(pivot);
    }

    public void unhighlightPivot() {
        plot.removeDomainMarker(pivot);
    }

    public void highlightSlice(int index) {
        ValueMarker sliceMarker = new ValueMarker(index + 1);

        sliceMarkers.add(sliceMarker);
        sliceMarker.setLabel("Slice");
        sliceMarker.setLabelAnchor(RectangleAnchor.CENTER);
        sliceMarker.setLabelTextAnchor(TextAnchor.BOTTOM_CENTER);
        sliceMarker.setPaint(Color.BLACK);
        plot.addDomainMarker(sliceMarker);
    }

    public void clearHighlights() {
        unhighlightPivot();
        unhighlightBounds();
        swapSeries.clear();
        compareSeries.clear();
        sliceMarkers.forEach(plot::removeDomainMarker);
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
        copyCounterAnnotation.setX(newDataMax);
        copyCounterAnnotation.setY(newDataMax + 2);
    }

    public void updateComparisonAnnotation(String newText) {
        comparisonCounterAnnotation.setText(newText);
    }

    public void updateCopyAnnotation(String newText) {
        copyCounterAnnotation.setText(newText);
    }

    public void configureForMultipleConditionDisplay() {
        this.getChart().getLegend().setVisible(false);
    }

    public void configureForMultipleMethodDisplay(SortingMethods method) {
        this.getChart().getLegend().setVisible(false);
        this.getChart().getTitle().setVisible(true);
        this.getChart().setTitle(method.title);
    }

    public void setRangeHeight(int maxValue) {
        plot.getRangeAxis().setRange(0, maxValue + 4);
        plot.removeAnnotation(comparisonCounterAnnotation);
        plot.removeAnnotation(copyCounterAnnotation);
    }
}
