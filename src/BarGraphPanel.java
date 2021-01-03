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

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

/**
 * This class is used to visualize the data being sorted and the operations being performed on
 * that data.
 */
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

    /**
     * Primary constructor which builds an object that corresponds to a Data object.
     * @param dataSeriesCollection An XYSeriesCollection that represents the XYSeries objects
     *                             being used by this ChartPanel's associated Data object.
     * @param comparisonCounter A string that represents the text to be displayed as the
     *                          comparison operation counter.
     * @param copyCounter A string that represents the text to be displayed as the copy operation
     *                    counter.
     */
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

    /**
     * A method that highlights the data being compared by adding a data item to the XYSeries
     * representing data being compared.
     * @param indexOne The index of the first datapoint being compared
     * @param indexTwo The index of the second datapoint being compared
     */
    public void highlightCompare(int indexOne, int indexTwo) {
        try {
            compareSeries.add(dataSeries.getX(indexOne), dataSeries.getMaxY() + 1);
            compareSeries.add(dataSeries.getX(indexTwo), dataSeries.getMaxY() + 1);
            delay(2);
            compareSeries.clear();
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            return;
        }
    }

    /**
     * A method that highlights a data element that is being copied by adding the value of that
     * element to the XYSeries used to highlight copy operations.
     * @param index the index of the data element being copied.
     */
    public void highlightCopy(int index) {
        swapSeries.add(dataSeries.getX(index), dataSeries.getY(index));
        delay(1);
        swapSeries.clear();
    }

    /**
     * A method that highlights two data elements being swapped by adding the value of that
     * element to the XYSeries used to highlight copy operations.
     * @param indexOne the index of the first data element being swapped.
     * @param indexTwo the index of the second data element being swapped.
     */
    public void highlightSwap(int indexOne, int indexTwo) {
        swapSeries.add(dataSeries.getX(indexOne), dataSeries.getY(indexOne));
        swapSeries.add(dataSeries.getX(indexTwo), dataSeries.getY(indexTwo));
        delay(1);
        swapSeries.clear();
    }

    /**
     * Adds two lines representing the boundary of the data being considered at any given time.
     * @param indexOne the index of the lower bound to highlight
     * @param indexTwo the index of the upper bound to highlight
     */
    public void highlightBounds(int indexOne, int indexTwo) {
        lowerBound.setValue(indexOne + 1);
        upperBound.setValue(indexTwo + 1);
        plot.addDomainMarker(lowerBound);
        plot.addDomainMarker(upperBound);
    }

    /**
     * Removes lines added by highlightBounds()
     */
    public void unhighlightBounds() {
        plot.removeDomainMarker(lowerBound);
        plot.removeDomainMarker(upperBound);
    }

    /**
     * Adds a line representing the center point or pivot of a sorting operation.
     * @param index the index of the element that is to be emphasized.
     */
    public void highlightPivot(int index) {
        pivot.setValue(index + 1);
        plot.addDomainMarker(pivot);
    }

    /**
     * Removes lines placed by highlightPivot()
     */
    public void unhighlightPivot() {
        plot.removeDomainMarker(pivot);
    }

    /**
     * Adds a line representing the boundary for slices being considered duringg shell sorting.
     * @param index the index of the element that is to be emphasized
     */
    public void highlightSlice(int index) {
        ValueMarker sliceMarker = new ValueMarker(index + 1);

        sliceMarkers.add(sliceMarker);
        sliceMarker.setLabel("Slice");
        sliceMarker.setLabelAnchor(RectangleAnchor.CENTER);
        sliceMarker.setLabelTextAnchor(TextAnchor.BOTTOM_CENTER);
        sliceMarker.setPaint(Color.BLACK);
        plot.addDomainMarker(sliceMarker);
    }

    /**
     * Clears all highlighted data.
     */
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
            Thread.sleep((long) length * delayFactor);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Changes the value of the integer factor used to generate delays. This factor represents
     * the delay used by this program during animations in milliseconds.
     * @param newDelayFactor an integer number representing the number of milliseconds used for
     *                       delays
     */
    public static void changeSpeed(int newDelayFactor) {
        delayFactor = newDelayFactor;
    }

    /**
     * Changes the number of data elements this chart can support.
     * @param newDataMax The new size of data to be displayed.
     */
    public void setSize(int newDataMax) {
        getChart().getXYPlot().getRangeAxis().setRange(0, newDataMax + 4);
        comparisonCounterAnnotation.setX(1);
        comparisonCounterAnnotation.setY(newDataMax + 2);
        copyCounterAnnotation.setX(newDataMax);
        copyCounterAnnotation.setY(newDataMax + 2);
    }

    /**
     * Updates the string that is used to show the number of comparison operations.
     * @param newText the new String.
     */
    public void updateComparisonAnnotation(String newText) {
        comparisonCounterAnnotation.setText(newText);
    }

    /**
     * Updates the string that is used to show the number of copy operations.
     * @param newText the new String.
     */
    public void updateCopyAnnotation(String newText) {
        copyCounterAnnotation.setText(newText);
    }

    /**
     * Removes the legend from this chart object.
     */
    public void removeChartLegend() {
        this.getChart().getLegend().setVisible(false);
    }

    /**
     * In order to prepare this chart for display along with other charts, this method removes
     * the chart legend and adds a title to the chart.
     * @param method The sorting method for which to add a title to the chart
     */
    public void configureForMultipleMethodDisplay(SortingMethods method) {
        removeChartLegend();
        this.getChart().getTitle().setVisible(true);
        this.getChart().setTitle(method.title);
    }

    /**
     * Changes the maximum y value that can be accomadated by this chart.
     * @param maxValue integer representing the largest data that can be represented by this chart
     */
    public void setRangeHeight(int maxValue) {
        plot.getRangeAxis().setRange(0, maxValue + 4);
        plot.removeAnnotation(comparisonCounterAnnotation);
        plot.removeAnnotation(copyCounterAnnotation);
    }
}
