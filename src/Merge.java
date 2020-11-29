import org.jfree.data.xy.XYSeries;

public abstract class Merge extends Sort {

    protected static void merge(Data auxiliaryDataOne, Data auxiliaryDataTwo, Data data, int lowerBound) {
        int upperBound = lowerBound + auxiliaryDataOne.getSize() + auxiliaryDataTwo.getSize() - 1;
        int leftIndex = 0;
        int rightIndex = 0;
        for (int k = lowerBound; k <= upperBound; k++) {
            if (leftIndex >= auxiliaryDataOne.getSize()) copy(auxiliaryDataTwo, rightIndex++,
                    data, k);
            else if (rightIndex >= auxiliaryDataTwo.getSize()) copy(auxiliaryDataOne, leftIndex++,
                    data, k);
            else if (less(auxiliaryDataOne, leftIndex, auxiliaryDataTwo, rightIndex, data)) {
                copy(auxiliaryDataOne, leftIndex++, data, k);
            } else {
                copy(auxiliaryDataTwo, rightIndex++, data, k);
            }
        }
    }

    protected static boolean less(Data dataOne, int firstIndex, Data dataTwo, int secondIndex,
                               Data mainData) {
        mainData.increaseComparisonCount();

        XYSeries firstDataSeries = dataOne.getDataSeries();
        XYSeries secondDataSeries = dataTwo.getDataSeries();

        highlightCompareTwoDatas(dataOne, firstIndex, dataTwo, secondIndex);
        return firstDataSeries.getY(firstIndex).intValue() < secondDataSeries.getY(secondIndex).intValue();
    }

    protected static void copy(Data fromData, int fromIndex, Data toData, int toIndex) {
        XYSeries dataSeries = fromData.getDataSeries();
        XYSeries auxiliarySeries = toData.getDataSeries();

        fromData.increaseCopyCount();
        auxiliarySeries.updateByIndex(toIndex, dataSeries.getY(fromIndex));
        (new Thread(new RunnableCopyHighlight(fromData, fromIndex))).start();
        toData.getGraphPanel().highlightCopy(toIndex);
    }

    protected static void highlightCompareTwoDatas(Data dataOne, int firstIndex, Data dataTwo,
                                            int secondIndex) {
        dataOne.getGraphPanel().highlightCompare(firstIndex, firstIndex);
        (new Thread(new RunnableComparisonHighlight(dataTwo, secondIndex))).start();
    }

    protected static class RunnableComparisonHighlight implements Runnable {
        private final Data data;
        private final int index;

        RunnableComparisonHighlight(Data data, int index) {
            this.data = data;
            this.index = index;
        }

        @Override
        public void run() {
            data.getGraphPanel().highlightCompare(index,index);
        }
    }

    protected static class RunnableCopyHighlight implements Runnable{
        private final Data data;
        private final int index;

        RunnableCopyHighlight(Data data, int index) {
            this.data = data;
            this.index = index;
        }
        @Override
        public void run() {
            data.getGraphPanel().highlightCopy(index);
        }
    }
}
