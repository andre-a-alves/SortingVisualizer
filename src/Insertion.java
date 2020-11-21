
public class Insertion extends Sort {
    public static void sort() {
        sort(0, size - 1);
    }

    public static void sort(int indexOne, int indexTwo) {
        for (int i = indexOne + 1; i <= indexTwo; i++) {
            Sort.barGraphPanel.highlightBounds(indexOne, i);
            for (int j = i; j > indexOne; j--) {
                if (less(j, j - 1)) {
                    exchange(j, j - 1);
                } else break;
            }
            Sort.barGraphPanel.unhighlightBounds();
        }
    }

    public static void sort(Data data) {
        sort(data, 0, data.getSize() - 1);
    }

    public static void sort(Data data, int indexOne, int indexTwo) {
        BarGraphPanel graphPanel= data.getGraphPanel();

        for (int i = indexOne + 1; i <= indexTwo; i++) {
            graphPanel.highlightBounds(indexOne, i);
            for (int j = i; j > indexOne; j--) {
                if (less(data, j, j - 1)) {
                    exchange(data, j, j - 1);
                } else break;
            }
            graphPanel.unhighlightBounds();
        }
    }
}
