public enum SortingMethods {
    SELECTION("Selection Sort", "Selection Sort"),
    INSERTION("Insertion Sort", "Insertion Sort"),
    SHELL_SHELL("Shell Sort using Shell's Sequence", "Shell", false, true),
    SHELL_KNUTH("Shell Sort using Knuth Sequence", "Knuth", false, true),
    BUBBLE("Bubble Sort", "Bubble Sort"),
    MERGE_SENTINEL("Merge Sort Implementing Sentinel", "Implementing Sentinel", false, false, true),
    MERGE_TOPDOWN("Merge Sort: Top-Down", "Top-Down", false, false, true),
    MERGE_BOTTOMUP("Merge Sort: Bottom-Up", "Bottom-Up", false, false, true),
    MERGE_BOTTOM_IN_PLACE("Merge Sort: Bottom-Up, In-Place", "Bottom-Up In-Place", false,false,
            true),
    QUICK_HOARE("Quick Sort as Written by Hoare (1962)", "Original", true),
    QUICK_LOMUTO("Quick Sort: Lomuto Implementation", "Lomuto", true),
    QUICK_H_INDEX("Quick Sort with Highest Index as Pivot", "High Index Pivot", true),
    QUICK_MEDIAN("Quick Sort: Hoare with Median-of-Three Pivot (Sedgewick)", "Median-of-Three",
            true),
    QUICK_INSERTION("Quick Sort: Hoare with Insertion Sort for Small Lists", "With Insertion",
            true, false),
    HEAP("Heap Sort", "Heap Sort");

    public String title;
    public String menuTitle;
    public boolean quickType;
    public boolean shellType;
    public boolean mergeType;


    SortingMethods(String title, String menuTitle) {
        this(title, menuTitle, false);
    }

    SortingMethods(String title, String menuTitle, boolean quickType) {
        this(title, menuTitle, quickType, false);
    }

    SortingMethods(String title, String menuTitle, boolean quickType, boolean shellType) {
        this(title, menuTitle, quickType, shellType, false);
    }

    SortingMethods(String title, String menuTitle, boolean quickType, boolean shellType,
                   boolean mergeType){
        this.title = title;
        this.menuTitle = menuTitle;
        this.quickType = quickType;
        this.shellType = shellType;
        this.mergeType = mergeType;
    }
}
