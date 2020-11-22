import org.jfree.layout.CenterLayout;

import java.awt.*;

public class SingleDataHandler extends DataHandler {
    private final Data data;

    public SingleDataHandler() {
        super();
        data = new Data();
        setDisplay();
        setLayout(new CenterLayout());
    }

    private void setDisplay() {
        for (Component component : this.getComponents()) {
            remove(component);
        }
        add(data.getGraphPanel());
    }

    public void setBehavior(SortingMethods sortingMethod) {
        data.setSortMethod(sortingMethod);
    }

    public void stopThreadsAndInitializeCharts(InitialData action) {
        data.stopThreadAndInitializeChart(action);
    }

    public void sort() {
        data.sort();
    }

    @Override
    void setDataSize(int newSize) {
        dataSize = newSize;
        data.changeSize(newSize);
    }
}
