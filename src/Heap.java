public class Heap extends Sort{

    public static void sort() {
        heapify(size - 1);
        sort(size - 1);
    }

    private static void heapify(int upperBound) {
        for (int i = upperBound; i > 0; i--) {
            Sort.barGraphPanel.highlightBounds(0,i);
            swim(i, upperBound);
            Sort.barGraphPanel.unhighlightBounds();
        }
    }

    private static void swim(int index, int upperBound) {
        int parent = (Double.valueOf(Math.floor((index - 1) / 2))).intValue();
        if (less(parent, index)) {
            sink(parent, upperBound);
        }
    }

    private static void sink(int parent, int upperBound) {
        int firstChild = 2 * parent + 1;
        int secondChild = firstChild + 1;
        if (firstChild <= upperBound && secondChild <= upperBound) {
            int largerChild = less(firstChild, secondChild) ? secondChild : firstChild;
            if (less(parent, largerChild)) {
                exchange(parent, largerChild);
                sink(largerChild, upperBound);
            }
        } else if (firstChild <= upperBound && less(parent, firstChild)) {
            exchange(parent, firstChild);
        }
    }

    private static void sort(int upperBound) {
        for (int i = upperBound; i >= 0;) {
            Sort.barGraphPanel.highlightBounds(0,i);
            exchange(0, i);
            sink(0, --i);
            Sort.barGraphPanel.unhighlightBounds();
        }
    }


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
        int parent = (Double.valueOf(Math.floor((index - 1) / 2))).intValue();
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
        } else if (firstChild <= upperBound && less(parent, firstChild)) {
            exchange(data, parent, firstChild);
        }
    }

    private static void sort(Data data, int upperBound) {
        BarGraphPanel graphPanel = data.getGraphPanel();
        for (int i = upperBound; i >= 0;) {
            graphPanel.highlightBounds(0,i);
            exchange(0, i);
            sink(0, --i);
            graphPanel.unhighlightBounds();
        }
    }
}
