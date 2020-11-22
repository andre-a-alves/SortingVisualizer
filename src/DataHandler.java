import javax.swing.*;
import java.util.ArrayList;

public abstract class DataHandler extends JPanel{
    public static final int DEFAULT_SIZE = 32;
    public static final int MIN_SIZE = 4;
    public static final int MAX_SIZE = 128;
    public static final int MIN_DELAY_FACTOR = 1;
    public static final int MAX_DELAY_FACTOR = 100;
    public static final int DEFAULT_DELAY_FACTOR = 40;
    protected int dataSize;
    protected int delayFactor;
    protected ArrayList<Integer> previousData;

    abstract void stopThreadsAndInitializeCharts(InitialData action);
    abstract void sort();
    abstract void setDataSize(int newSize);
    abstract void setBehavior(SortingMethods sortingMethod);
    protected DataHandler() {
        super();
        previousData = new ArrayList<>();
        dataSize = DEFAULT_SIZE;
        delayFactor = DEFAULT_DELAY_FACTOR;
    }

    public void setDelayFactor(int factor) {
        delayFactor = factor;
        BarGraphPanel.changeSpeed(factor);
    }
}
