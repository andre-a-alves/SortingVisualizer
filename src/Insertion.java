
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
                }
                else {
                    break;
                }
            }
            Sort.barGraphPanel.unhighlightBounds();
        }
    }
}
