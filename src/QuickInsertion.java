import org.jfree.data.xy.XYSeries;

import java.util.concurrent.ThreadLocalRandom;

public abstract class QuickInsertion extends QuickHoare {
    public static void sort(Data data) {
        sort(data, 0, data.getDataSeries().getItemCount() - 1);
    }

    private static void sort(Data data, int lowerBound, int upperBound) {
        if (upperBound - lowerBound < 6) Insertion.sort(data, lowerBound, upperBound);
        else {
            int inPlace = partition(data, lowerBound, upperBound);
            sort(data, lowerBound, inPlace);
            sort(data, inPlace + 1, upperBound);
        }
    }
}
