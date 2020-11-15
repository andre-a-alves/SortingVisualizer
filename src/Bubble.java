import java.util.List;

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
}
