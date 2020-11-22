import java.awt.*;
import java.util.ArrayList;

public class MultipleSortingMethodsDataHandler extends DataHandler {
    private final ArrayList<Data> dataList;
    private final int numberOfDisplays;
    private final ArrayList<SortingMethods> methods;
    private final GuiHandler guiHandler;

    public MultipleSortingMethodsDataHandler(GuiHandler guiHandler,
                                             ArrayList<SortingMethods> methods) {
        super();
        this.guiHandler = guiHandler;
        this.numberOfDisplays = methods.size();
        this.methods = methods;
        dataList = new ArrayList<>();
        chooseAndSetLayout();
        initializeData();
    }

    private void chooseAndSetLayout() {
        if (numberOfDisplays == 2)
            setLayout(new GridLayout(1,2));
        else
            setLayout(new GridLayout(2,2));
    }

    private void initializeData() {
        for (SortingMethods method : methods) {
            Data thisData = new Data();
            dataList.add(thisData);
            add(thisData.getGraphPanel());
            thisData.getGraphPanel().configureForMultipleMethodDisplay(method);
            thisData.setSortMethod(method);
        }
        replicateData();
    }

    private void replicateData() {
        ArrayList<Integer> dataArray = dataList.get(0).getCurrentDataList();
        for (Data data : dataList) {
            data.setDataFromList(dataArray);
            data.setPreviousDataFromList(previousData);
        }
    }

    @Override
    public void stopThreadsAndInitializeCharts(InitialData configuration) {
        previousData.clear();
        previousData.addAll(dataList.get(0).getCurrentDataList());
        for (Data data : dataList) {
            data.stopThreadAndInitializeChart(configuration);
            data.getGraphPanel().clearHighlights();
            data.setPreviousDataFromList(previousData);
        }
        replicateData();
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
    public void setBehavior(SortingMethods sortingMethod) {
//        guiHandler.setSingleMethodMode(sortingMethod);
    }
}
