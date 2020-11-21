public enum SortingMethods {
    SELECTION("Selection Sort", "Selection Sort"),
    INSERTION("Insertion Sort", "Insertion Sort"),
    SHELL_SHELL("Shell Sort using Shell's Sequence", "Shell", false, true),
    SHELL_KNUTH("Shell Sort using Knuth Sequence", "Knuth", false, true),
    BUBBLE("Bubble Sort", "Bubble Sort"),
    MERGE("Merge Sort", "Merge Sort"),
    QUICK("Quick Sort with Highest Index as Pivot", "High Index Pivot", true, false),
    QUICK_HOARE("Quick Sort as Written by Hoare (1962)", "Original", true, false),
    QUICK_LOMUTO("Quick Sort: Lomuto Implementation", "Lomuto", true, false),
    QUICK_MEDIAN("Quick Sort: Hoare with Median-of-Three Pivot (Sedgewick)", "Median-of-Three",
            true, false),
    QUICK_INSERTION("Quick Sort: Hoare with Insertion Sort for Small Lists", "With Insertion",
            true, false),
    HEAP("Heap Sort", "Heap Sort");

    public String title;
    public String menuTitle;
    public boolean quickType;
    public boolean shellType;


    SortingMethods(String title, String menuTitle) {
        this.title = title;
        this.menuTitle = menuTitle;
        quickType = false;
        shellType = false;
    }

    SortingMethods(String title, String menuTitle, boolean quickType, boolean shellType){
        this(title, menuTitle);
        this.quickType = quickType;
        this.shellType = shellType;
    }
}
