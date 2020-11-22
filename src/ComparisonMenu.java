import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class ComparisonMenu extends JFrame {
    private final GuiHandler guiHandler;
    private final static ArrayList<JCheckBox> sortingMethodBoxes = new ArrayList<>();
    private final JPanel mainPanel;

    public ComparisonMenu(GuiHandler guiHandler) {
            super("Choose Sorting Methods");
            this.guiHandler = guiHandler;
            mainPanel = new JPanel();
            mainPanel.setBorder(new EmptyBorder(15,15,15,15));
            mainPanel.setLayout(new GridLayout(0,1));
            makeSortMethodCheckBoxes();
            makeSubmitButton();
            add(mainPanel);
            pack();

            setVisible(true);
    }

    private void makeSortMethodCheckBoxes() {
        JPanel normal = new JPanel();
        JPanel shell = new JPanel();
        JPanel quick = new JPanel();
        ItemListener checkCounter = new CheckCounter();

        for (SortingMethods method : SortingMethods.values()) {
            JCheckBox box = new JCheckBox(method.title);
            box.setName(method.name());
            if (method.shellType) shell.add(box);
            else if (method.quickType) quick.add(box);
            else normal.add(box);
            box.addItemListener(checkCounter);
            sortingMethodBoxes.add(box);
        }
        normal.setLayout(new GridLayout(0, 3));
        shell.setLayout(new GridLayout(0,3));
        quick.setLayout(new GridLayout(0,2));
        mainPanel.add(new JLabel("General Sorting Algorithms:"));
        mainPanel.add(normal);
        mainPanel.add(new JLabel("Shell Sorting Algorithms:"));
        mainPanel.add(shell);
        mainPanel.add(new JLabel("Quick Sorting Algorithms:"));
        mainPanel.add(quick);
    }

    private void makeSubmitButton() {
        JButton modeChanger = new JButton("Compare Selected Sorting Methods");
        modeChanger.addActionListener(e-> changeMode());
        mainPanel.add(modeChanger);
    }

    private void changeMode() {
        int checkedBoxes = 0;
        for (JCheckBox box : sortingMethodBoxes)
            if (box.isSelected())
                checkedBoxes++;
        if (checkedBoxes < 2) {
            showNoChoices();
            return;
        }

        ArrayList<SortingMethods> checkedMethods = new ArrayList<>();
        for (JCheckBox box : sortingMethodBoxes)
            if (box.isSelected())
                checkedMethods.add(SortingMethods.valueOf(box.getName()));
        guiHandler.setMultipleSortMethodComparisonMode(checkedMethods);
        sortingMethodBoxes.clear();
        checkedMethods.clear();
        dispose();
    }

    private void showNoChoices()
    {
        JOptionPane.showMessageDialog(this,
                "Please select at least two sorting methods to compare.",
                "No Sorting Methods Selected",
                JOptionPane.PLAIN_MESSAGE);
    }

    static class CheckCounter implements ItemListener {
        private final int MAX_BOXES = 4;
        private int checkedBoxes = 0;

        @Override
        public void itemStateChanged(ItemEvent e) {
            JCheckBox source = (JCheckBox) e.getSource();

            if (source.isSelected()) {
                checkedBoxes++;
                if (checkedBoxes == MAX_BOXES)
                    for (JCheckBox box : sortingMethodBoxes)
                        if (!box.isSelected())
                            box.setEnabled(false);
            } else {
                checkedBoxes--;
                if (checkedBoxes < MAX_BOXES)
                    for (JCheckBox box : sortingMethodBoxes)
                        box.setEnabled(true);
            }
        }
    }
}
