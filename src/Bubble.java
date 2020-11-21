public class Bubble extends Sort {
    public static void sort() {
        while (true) {
            boolean swapFlag = false;
            for (int i = 0; i < size - 1; i++) {
                if (less(i + 1, i)) {
                    exchange(i, i + 1);
                    swapFlag = true;
                }
            }
            if (!swapFlag) break;
        }
    }

    public static void sort(Data data) {
        int size = data.getSize();

        while (true) {
            boolean swapFlag = false;
            for (int i = 0; i < size - 1; i++) {
                if (less(data, i + 1, i)) {
                    exchange(data, i, i + 1);
                    swapFlag = true;
                }
            }
            if (!swapFlag) break;
        }
    }
}
