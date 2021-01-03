import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class MergeViewer extends JFrame {

    public MergeViewer(Data data) {
        super("Auxiliary Merge Data");
        BarGraphPanel graphPanel = data.getGraphPanel();

        graphPanel.setPreferredSize(new Dimension(600,400));
        add(graphPanel);

        setContentPane(graphPanel);
        pack();
        setLocationByPlatform(true);
        setVisible(true);
    }

    public void closeWindow() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
