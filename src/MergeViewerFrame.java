import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class MergeViewerFrame extends JFrame {
    private final ArrayList<BarGraphPanel> viewers;
    private JPanel contentPanel;

    public MergeViewerFrame() {
        super("Auxiliary Merge Data");
        viewers = new ArrayList<>();
        makeContentPanel();
    }

    private void makeContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(1,1));
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPanel);
        setPreferredSize(new Dimension(1280, 720));
        pack();
        setLocationByPlatform(true);

    }

    public void addViewer(BarGraphPanel viewer) {
        viewers.add(viewer);
        resetDisplay();
        if (!this.isVisible())
            openDisplay();
    }

    public void removeViewer(BarGraphPanel viewer) {
        viewers.remove(viewer);
        if (viewers.size() >= 1)
            resetDisplay();
        else
            closeDisplay();
    }

    public void openDisplay() {
        setVisible(true);
    }

    public void closeDisplay() {
        setVisible(false);
    }

    public void resetDisplay() {
        int squareRootOfNumberOfViewers =
                Double.valueOf(Math.ceil(Math.sqrt(viewers.size()))).intValue();
        contentPanel.removeAll();
        contentPanel.setLayout(new GridLayout(squareRootOfNumberOfViewers,squareRootOfNumberOfViewers));
        for (BarGraphPanel viewer : viewers)
            contentPanel.add(viewer);
        pack();
    }
}
