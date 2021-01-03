import java.util.LinkedList;

public abstract class MergeBottomUp extends Merge {
    private static Data sortingData;

    public static void sort(Data data) {
        sortingData = data;
        LinkedList<Data> queue = new LinkedList<>();
        Data retrievedData;

        for (int i = 0; i < data.getSize(); i++) {
            Data singleData = new Data(1, mergeViewerFrame, data.getSize());
            singleData.getDataSeries().updateByIndex(0, data.getDataSeries().getY(i));
            queue.addLast(singleData);
        }


        while (true) {
            retrievedData = queue.removeFirst();
            if (queue.isEmpty()) break;
            Data secondData = queue.removeFirst();
            Data mergedData = new Data(retrievedData.getSize() + secondData.getSize(), mergeViewerFrame,
                    data.getSize());
            merge(retrievedData, secondData, mergedData, 0);
            queue.addLast(mergedData);
            retrievedData.removeThisMergeDataset();
            secondData.removeThisMergeDataset();
        }

        for (int i = 0; i < data.getSize(); i++) {
            sortingData.increaseCopyCount();
            copy(retrievedData, i, data, i);
        }
        retrievedData.removeThisMergeDataset();
    }

    protected static void merge(Data auxiliaryDataOne, Data auxiliaryDataTwo, Data data, int lowerBound) {
        int upperBound = lowerBound + auxiliaryDataOne.getSize() + auxiliaryDataTwo.getSize() - 1;
        int leftIndex = 0;
        int rightIndex = 0;
        for (int k = lowerBound; k <= upperBound; k++) {
            if (leftIndex >= auxiliaryDataOne.getSize()) {
                copy(auxiliaryDataTwo, rightIndex++, data, k);
                sortingData.increaseCopyCount();
            }
            else if (rightIndex >= auxiliaryDataTwo.getSize()) {
                copy(auxiliaryDataOne, leftIndex++, data, k);
                sortingData.increaseCopyCount();
            }
            else if (less(auxiliaryDataOne, leftIndex, auxiliaryDataTwo, rightIndex, data)) {
                sortingData.increaseComparisonCount();
                sortingData.increaseCopyCount();
                copy(auxiliaryDataOne, leftIndex++, data, k);
            } else {
                sortingData.increaseComparisonCount();
                sortingData.increaseCopyCount();
                copy(auxiliaryDataTwo, rightIndex++, data, k);
            }
        }
    }
}
