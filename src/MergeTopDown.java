public abstract class MergeTopDown extends Merge {
    public static void sort(Data data) {
        sort(data, 0, data.getDataSeries().getItemCount() - 1);
    }

    private static void sort(Data data, int lowerBound, int upperBound) {
        if (lowerBound >= upperBound) return;

        BarGraphPanel graphPanel = data.getGraphPanel();
        int middlePoint = lowerBound + (upperBound - lowerBound) / 2;
        sort(data, lowerBound, middlePoint);
        sort(data, middlePoint + 1, upperBound);

        graphPanel.highlightBounds(lowerBound,upperBound);
        graphPanel.highlightPivot(middlePoint);

        Data auxiliaryDataOne = new Data(middlePoint - lowerBound + 1, true, data.getSize());
        Data auxiliaryDataTwo = new Data(upperBound - middlePoint, true, data.getSize());

        int leftIndex = 0;
        int rightIndex = 0;
        for (int i = lowerBound; i <= middlePoint; i++)
            copy(data, i, auxiliaryDataOne, leftIndex++);
        for (int j = middlePoint + 1; j <= upperBound; j++)
            copy(data, j, auxiliaryDataTwo, rightIndex++);

        merge (auxiliaryDataOne, auxiliaryDataTwo, data, lowerBound);

        auxiliaryDataOne.closeMergeWindow();
        auxiliaryDataTwo.closeMergeWindow();

        graphPanel.unhighlightPivot();
        graphPanel.unhighlightBounds();
    }
}
