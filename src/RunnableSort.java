public class RunnableSort implements Runnable{
    private Data data;
    SortingMethods currentMethod;

    public RunnableSort(Data data) {
        this.data = data;
        currentMethod = SortingMethods.SELECTION;
    }

    public void setSortingMethod(SortingMethods newMethod) {
        currentMethod = newMethod;
    }

    public void run(){
        switch (currentMethod) {
            case INSERTION:
                Insertion.sort(data);
                break;
            case SHELL_SHELL:
                Shell.sort(data);
                break;
            case SHELL_KNUTH:
                ShellKnuth.sort(data);
                break;
            case BUBBLE:
                Bubble.sort(data);
                break;
            case MERGE:
                Merge.sort(data);
                break;
            case QUICK:
                QuickHighIndex.sort(data);
                break;
            case QUICK_HOARE:
                QuickHoare.sort(data);
                break;
            case QUICK_LOMUTO:
                QuickLomuto.sort(data);
                break;
            case QUICK_MEDIAN:
                QuickMedian.sort(data);
                break;
            case QUICK_INSERTION:
                QuickInsertion.sort(data);
                break;
            case HEAP:
                Heap.sort(data);
                break;
            case SELECTION:
            default:
                Selection.sort(data);
        }
    }
}
