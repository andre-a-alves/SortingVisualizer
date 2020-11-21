public abstract class Bubble extends Sort {
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
