import java.awt.*;
import java.util.ArrayList;

public class MultipleInitialConditionDataHandler extends DataHandler {
    private final ArrayList<Data> dataList;
    private final int numberOfDisplays;

    public MultipleInitialConditionDataHandler() {
        super();
        this.numberOfDisplays = 4;
        dataList = new ArrayList<>();
        initializeData();
        setLayout(new GridLayout(2,2));
    }

    private void initializeData() {
        for (int i = 0; i < numberOfDisplays; i++) {
            dataList.add(new Data());
            add(dataList.get(i).getGraphPanel());
            dataList.get(i).getGraphPanel().configureForMultipleConditionDisplay();
        }
    }

    @Override
    public void stopThreadsAndInitializeCharts(InitialData configuration) {
        int iterator = 0;
        for (InitialData condition : InitialData.values()) {
            if (condition == InitialData.PREVIOUS) break;
            dataList.get(iterator).stopThreadAndInitializeChart(condition);
            dataList.get(iterator++).getGraphPanel().clearHighlights();
        }
    }

    @Override
    public void sort() {
        for (Data data : dataList) data.sort();
    }

    @Override
    public void setDataSize(int size) {
        this.dataSize = size;
        for (Data data : dataList) data.changeSize(size);
    }

    @Override
    void setBehavior(SortingMethods sortingMethod) {
        for (Data data : dataList) {
            data.setSortMethod(sortingMethod);
        }
        stopThreadsAndInitializeCharts(InitialData.IN_ORDER);
    }
}
