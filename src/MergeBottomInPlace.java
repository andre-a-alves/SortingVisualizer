import org.jfree.data.xy.XYSeries;

import java.util.LinkedList;

public abstract class MergeBottomInPlace extends Merge {
    public static void sort(Data data) {
        LinkedList<Integer> queue = new LinkedList<>();
        Data retrievedData;

        for (int i = 0; i < data.getSize(); i++) {
            queue.addLast(i);
            queue.addLast(1);
        }

        while (true) {
            int leftIndex = queue.removeFirst();
            int leftSize = queue.removeFirst();
            if (queue.isEmpty()) break;
            int rightIndex = queue.removeFirst();
            int rightSize = queue.removeFirst();
            merge(data, leftIndex, leftSize, rightIndex, rightSize);
            queue.addLast(leftIndex);
            queue.addLast(leftSize + rightSize);
        }
    }

    private static void merge(Data data, int firstIndex, int firstSize, int secondIndex,
                              int secondSize) {
        data.getGraphPanel().highlightBounds(firstIndex, secondIndex + secondSize - 1);
        int leftIndex = firstIndex;
        while(true) {
            if (leftIndex >= firstIndex + firstSize) {
                data.getGraphPanel().unhighlightBounds();
                return;
            }
            else if (less(data, leftIndex, secondIndex)) leftIndex++;
            else {
                while (true) {
                    exchange(data, leftIndex, secondIndex);
                    bubbleSortPass(data, secondIndex, secondIndex + secondSize - 1);
                    if (less(data, leftIndex, secondIndex)) {
                        leftIndex++;
                        break;
                    }
                }
            }
        }
    }

    private static void bubbleSortPass(Data data, int lowerBound, int upperBound) {
        int iterationIndex = lowerBound;
        while(iterationIndex < upperBound) {
            if (less(data, ++iterationIndex, --iterationIndex )) exchange(data, iterationIndex,
                    ++iterationIndex);
            else return;
        }
    }
}
