public abstract class Heap extends Sort{
    public static void sort(Data data) {
        heapify(data, data.getSize() - 1);
        sort(data, data.getSize() - 1);
    }

    private static void heapify(Data data, int upperBound) {
        BarGraphPanel graphPanel = data.getGraphPanel();

        for (int i = upperBound; i > 0; i--) {
            graphPanel.highlightBounds(0,i);
            swim(data, i, upperBound);
            graphPanel.unhighlightBounds();
        }
    }

    private static void swim(Data data, int index, int upperBound) {
        int parent = (index - 1) / 2;
        if (less(data, parent, index)) {
            sink(data, parent, upperBound);
        }
    }

    private static void sink(Data data, int parent, int upperBound) {
        int firstChild = 2 * parent + 1;
        int secondChild = firstChild + 1;
        if (firstChild <= upperBound && secondChild <= upperBound) {
            int largerChild = less(data, firstChild, secondChild) ? secondChild : firstChild;
            if (less(data, parent, largerChild)) {
                exchange(data, parent, largerChild);
                sink(data, largerChild, upperBound);
            }
        } else if (firstChild <= upperBound && less(data, parent, firstChild)) {
            exchange(data, parent, firstChild);
        }
    }

    private static void sort(Data data, int upperBound) {
        BarGraphPanel graphPanel = data.getGraphPanel();
        for (int i = upperBound; i >= 0;) {
            graphPanel.highlightBounds(0,i);
            exchange(data, 0, i);
            sink(data, 0, --i);
            graphPanel.unhighlightBounds();
        }
    }
}
