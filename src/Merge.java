import org.jfree.data.xy.XYSeries;

public abstract class Merge extends Sort {
    public static void sort(Data data) {
        sort(data, 0, data.getDataSeries().getItemCount() - 1);
    }

    private static void sort(Data data, int lowerBound, int upperBound) {
        if (lowerBound < upperBound) {
            int middlePoint = lowerBound + (upperBound - lowerBound) / 2;
            sort(data, lowerBound, middlePoint);
            sort(data, middlePoint + 1, upperBound);
            merge(data, lowerBound, middlePoint + 1, upperBound);
        }
    }

    private static void merge(Data data, int lowerBound, int middlePoint, int upperBound) {
        BarGraphPanel graphPanel = data.getGraphPanel();
        Data auxiliaryData = new Data(upperBound - lowerBound + 1, true, data.getSize());

        graphPanel.highlightBounds(lowerBound,upperBound);
        graphPanel.highlightPivot(middlePoint);

        int leftIndex = 0;
        int rightIndex = middlePoint - lowerBound;

        for (int i = lowerBound; i < middlePoint; i++)
            copy(data, i, auxiliaryData, leftIndex++);
        for (int j = middlePoint; j <= upperBound; j++)
            copy(data, middlePoint + upperBound - j, auxiliaryData, rightIndex++);

        leftIndex = 0;
        rightIndex = auxiliaryData.getSize() - 1;
        for (int k = lowerBound; k <= upperBound; k++) {
            if (less(auxiliaryData, rightIndex, leftIndex, data)) {
                copy(auxiliaryData, rightIndex--, data,k);
            } else {
                copy(auxiliaryData, leftIndex++, data, k);
            }
        }
        auxiliaryData.closeMergeWindow();
        graphPanel.unhighlightPivot();
        graphPanel.unhighlightBounds();
    }

    private static void copy(Data data, int index, Data auxiliaryData, int auxIndex) {
        XYSeries dataSeries = data.getDataSeries();
        XYSeries auxiliarySeries = auxiliaryData.getDataSeries();

        data.increaseCopyCount();
        auxiliarySeries.updateByIndex(auxIndex, dataSeries.getY(index));
        (new Thread(new RunnableHighlight(data, index))).start();
        auxiliaryData.getGraphPanel().highlightCopy(auxIndex);
    }

    private static class RunnableHighlight implements Runnable{
        private final Data data;
        private final int index;

        RunnableHighlight(Data data, int index) {
            this.data = data;
            this.index = index;
        }
        @Override
        public void run() {
            data.getGraphPanel().highlightCopy(index);
        }
    }
}
