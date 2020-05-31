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
            Matrix.mergeSortRow(matrix.getRows().get(0));
    }

    private Collection<SortRowsRecursiveAction> createSubtasks() {
        List<SortRowsRecursiveAction> subtasks = new ArrayList<>(2);
        subtasks.add(new SortRowsRecursiveAction(new Matrix(matrix.getRows().subList(0, m/2))));
        subtasks.add(new SortRowsRecursiveAction(new Matrix(matrix.getRows().subList(m/2, m))));
        return subtasks;
    }
}
