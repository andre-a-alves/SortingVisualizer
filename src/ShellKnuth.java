import java.util.Stack;

public abstract class ShellKnuth extends Sort{
    public static void sort(Data data) {
        BarGraphPanel graphPanel = data.getGraphPanel();
        int size = data.getSize();

        Stack<Integer> gaps = new Stack<>();
        int n = 1;
        while(true) {
            int nextGap = (Double.valueOf(Math.pow(3,n++)  - 1)).intValue() / 2;
            if (nextGap <= size / 3) gaps.push(nextGap);
            else break;
        }
        while (!gaps.empty()) {
            int gap = gaps.pop();
            drawPivots(data, gap, size - 1);
            sort(data, gap, size -1);
            graphPanel.unhighlightPivot();
        }
    }

    private static void sort(Data data, int gap, int upperbound) {
        for (int i = 0; i < gap; i++) {
            for (int k = i; k <= upperbound; k += gap) {
                for (int j = k; j > 0; j -= gap) {
                    if (less(data, j, j - gap)) exchange(data, j, j - gap);
                    else break;
                }
            }
        }
    }

    private static void drawPivots(Data data, int gap, int upperBound) {
        BarGraphPanel graphPanel = data.getGraphPanel();
        for (int i = 0; i <= upperBound; i += gap) graphPanel.highlightPivot(i);
    }
}
