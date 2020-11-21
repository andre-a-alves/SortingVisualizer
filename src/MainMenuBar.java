import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainMenuBar extends JMenuBar implements ActionListener {
    final private String VERSION;
    final private DataControlPanel dataControlPanel;

    public void actionPerformed(ActionEvent event)
    {

    }

    /**
     * Constructor for objects of class MainMenu
     */
    public MainMenuBar(DataControlPanel dataControlPanel)
    {
        super();
        VERSION = Sort.getVersion();
        this.dataControlPanel = dataControlPanel;
        this.add(makeFileMenu());
        this.add(makeSortMenu());
        this.add(makeHelpMenu());
    }

    /**
     * This private method returns a file menu
     * @return a file menu
     */
    private JMenu makeFileMenu()
    {
        final int SHORTCUT_MASK =
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();

        JMenu fileMenu = new JMenu("File");

        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
        quitItem.addActionListener(e -> quitApp());

        fileMenu.addSeparator();
        fileMenu.add(quitItem);

        return fileMenu;
    }

    /**
     * This private method returns a help menu
     * @return a help menu
     */
    private JMenu makeHelpMenu()
    {
        JMenu helpMenu = new JMenu("Help");

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAbout());
        helpMenu.addSeparator();
        helpMenu.add(aboutItem);
        helpMenu.addSeparator();

        return helpMenu;
    }

    private JMenu makeSortMenu() {
        JMenu sortMenu = new JMenu("Sorting Algortihm");

        JMenuItem selectionSort = new JMenuItem("Selection Sort");
        selectionSort.addActionListener(e -> dataControlPanel.setSelectionSort());
        sortMenu.add(selectionSort);

        JMenuItem insertionSort = new JMenuItem("Insertion Sort");
        insertionSort.addActionListener(e -> dataControlPanel.setInsertSort());
        sortMenu.add(insertionSort);

        JMenuItem bubbleSort = new JMenuItem("Bubble Sort");
        bubbleSort.addActionListener(e -> dataControlPanel.setBubbleSort());
        sortMenu.add(bubbleSort);

        JMenu subMenu = new JMenu("Quick Sort");
        sortMenu.add(subMenu);

        JMenuItem quickSort = new JMenuItem("Generic");
        quickSort.addActionListener(e->dataControlPanel.setQuickSort());
        subMenu.add(quickSort);

        JMenuItem quickSortHoare = new JMenuItem("Hoare");
        quickSortHoare.addActionListener(e->dataControlPanel.setQuickSortHoare());
        subMenu.add(quickSortHoare);

        JMenuItem quickSortLomuto = new JMenuItem("Lomuto");
        quickSortLomuto.addActionListener(e->dataControlPanel.setQuickSortLomuto());
        subMenu.add(quickSortLomuto);

        JMenuItem quickSortMedian = new JMenuItem("Median-of-Three");
        quickSortMedian.addActionListener(e->dataControlPanel.setQuickSortMedian());
        subMenu.add(quickSortMedian);

        JMenuItem quickSortInsertion = new JMenuItem("With Insertion");
        quickSortInsertion.addActionListener(e->dataControlPanel.setQuickSortInsertion());
        subMenu.add(quickSortInsertion);

        JMenuItem mergeSort = new JMenuItem("Merge Sort");
        mergeSort.addActionListener(e->dataControlPanel.setMergeSort());
        sortMenu.add(mergeSort);

        JMenuItem heapSort = new JMenuItem("Heap Sort");
        heapSort.addActionListener(e->dataControlPanel.setHeapSort());
        sortMenu.add(heapSort);

        JMenu sortSubMenu = new JMenu("Shell Sort");
        sortMenu.add(sortSubMenu);

        JMenuItem shellSort = new JMenuItem("Shell");
        shellSort.addActionListener(e->dataControlPanel.setShellSort());
        sortSubMenu.add(shellSort);

        JMenuItem shellKnuthSort = new JMenuItem("Knuth");
        shellKnuthSort.addActionListener(e->dataControlPanel.setShellKnuthSort());
        sortSubMenu.add(shellKnuthSort);

        return sortMenu;
    }

    private void showAbout()
    {
        JOptionPane.showMessageDialog(this,
                "Version\n" + VERSION +
                        "\nThis application is a visual aid" +
                        "\nto help visualize different sorting" +
                        "\nalgorithms." +
                        "\nPlease direct any questions to" +
                        "\nLord André Avìla Alves, a real noble" +
                        "\nof the Principality of Sealand," +
                        "\nat andre@alves.me",
                "About This Application",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void quitApp()
    {
        System.exit(0);
    }
}
