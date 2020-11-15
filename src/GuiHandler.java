import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GuiHandler extends JFrame {
    private final DataControlPanel dataControlPanel;
    private final BarGraphPanel barGraphPanel;
    private final JLabel titleBar;

    public GuiHandler() {
        super("Visual Sorter");
        Data data = new Data();
        titleBar = makeTitleBar();
        dataControlPanel = new DataControlPanel(data, this);
        this.setJMenuBar(new MainMenuBar(dataControlPanel));
        barGraphPanel = new BarGraphPanel(data);
        makeMasterFrame();
        setVisible(true);
    }

    private JLabel makeTitleBar() {
        JLabel titleBar = new JLabel();

        titleBar.setHorizontalAlignment(0);
        titleBar.setVerticalAlignment(0);
        titleBar.setFont(new Font(titleBar.getFont().getName(), Font.BOLD, 24));

        return titleBar;
    }

    private void makeMasterFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Creates a master pane.
        JPanel masterContentPane = new JPanel();

        masterContentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        masterContentPane.setLayout(new BorderLayout(10, 10));
        masterContentPane.add(barGraphPanel, BorderLayout.CENTER);
        masterContentPane.add(dataControlPanel, BorderLayout.WEST);
        masterContentPane.add(titleBar, BorderLayout.NORTH);

        setContentPane(masterContentPane);

        pack();
    }

    public void setTitle(String newText) {
        titleBar.setText(newText);
    }
}
