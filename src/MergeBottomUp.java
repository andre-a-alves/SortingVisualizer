import java.util.LinkedList;

public abstract class MergeBottomUp extends Merge {
    public static void sort(Data data) {
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
            copy(retrievedData, i, data, i);
        }
        retrievedData.removeThisMergeDataset();
    }
}
