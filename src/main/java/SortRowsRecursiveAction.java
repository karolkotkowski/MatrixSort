import java.util.*;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class SortRowsRecursiveAction extends RecursiveAction {

    private final Matrix matrix;
    private final int m;

    public SortRowsRecursiveAction(Matrix matrix) {
        this.matrix = matrix;
        m = this.matrix.getRows().size();
    }

    @Override
    protected void compute() {
        if (m > 1)
            ForkJoinTask.invokeAll(createSubtasks());
        else
            mergeSortRow(matrix.getRows().get(0));
    }

    private Collection<SortRowsRecursiveAction> createSubtasks() {
        List<SortRowsRecursiveAction> subtasks = new ArrayList<>(2);
        subtasks.add(new SortRowsRecursiveAction(new Matrix(matrix.getRows().subList(0, m/2))));
        subtasks.add(new SortRowsRecursiveAction(new Matrix(matrix.getRows().subList(m/2, m))));
        return subtasks;
    }

    private void mergeSortRow(List<Integer> row) {
        Collections.sort(row);
    }

    private void bubbleSortRow(List<Integer> row) {
        int n = row.size();
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (row.get(j) > row.get(j+1))
                {
                    int temp = row.get(j);
                    row.set(j, row.get(j+1));
                    row.set(j+1, temp);
                }
    }
}
