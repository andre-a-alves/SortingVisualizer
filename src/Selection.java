
public class Selection extends Sort {

    public static void sort() {
        for (int i = 0; i < size - 1; i++) {
            Sort.barGraphPanel.highlightBounds(i, dataSeries.getItemCount() - 1);
            int min = i;
            for (int j = i + 1; j < size; j++) {
                if (less(j, min)) {
                    min = j;
                }
            }
            exchange(i, min);
            Sort.barGraphPanel.unhighlightBounds();
        }
    }
}
