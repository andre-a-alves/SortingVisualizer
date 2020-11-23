import java.util.Stack;

public abstract class ShellKnuth extends Shell{
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
            if (gap > 1) drawPivots(data, gap, size - 1);
            sort(data, gap, size -1);
            graphPanel.clearHighlights();
        }
    }
}
