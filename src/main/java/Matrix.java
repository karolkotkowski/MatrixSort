import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class Matrix implements Iterable<List<Integer>> {
    private List<List<Integer>> rows;
    private final int m;
    private final int n;

    public Matrix(List<List<Integer>> rows) {
        m = rows.size();
        if (m < 1)
            throw new IllegalMatrixDimensionsException("Initializing matrix with no rows.");

        n = rows.get(0).size();
        if (n < 1)
            throw new IllegalMatrixDimensionsException("Initializing matrix with no columns.");
        for (int i = 1; i < m; i++) {
            if (rows.get(i).size() != n)
                throw new IllegalMatrixDimensionsException("Matrix dimensions must agree.");
        }

        this.rows = rows;
    }

    public void sort(ProcessingMethod method) {
        sort(method, SortingAlgorithm.MERGE);
    }

    public void sort(SortingAlgorithm algorithm) {
        sort(ProcessingMethod.STREAM, algorithm);
    }

    public void sort(ProcessingMethod method, SortingAlgorithm algorithm) {
        long time1 = System.nanoTime();
        Iterator<List<Integer>> rowIterator;
        List<Integer> row;

        switch (method) {
            case ITERATOR:
                rowIterator = this.iterator();
                while (rowIterator.hasNext()) {
                    row = rowIterator.next();
                    sortRow(row, algorithm);
                }
                break;
            case FORKJOIN:
                ForkJoinPool pool = new ForkJoinPool(8);
                pool.invoke(new SortRowsRecursiveAction(this, algorithm));
                break;
            case STREAM:
                rows
                        .stream()
                        .forEach(e -> sortRow(e, algorithm));
                break;
            case PARALLELSTREAM:
                rows
                        .parallelStream()
                        .forEach(e -> sortRow(e, algorithm));
                break;
        }

        long time2 = System.nanoTime();
        System.out.println("Rows sorting execution time: " + (time2 - time1)/1000/1000 + " ms");

        sortBetweenRows();

        long time3 = System.nanoTime();
        System.out.println("Sorting between rows execution time: " + (time3 - time2)/1000/1000 + " ms");
    }

    protected static void sortRow(List<Integer> row, SortingAlgorithm algorithm) {
        switch (algorithm) {
            case BUBBLE:
                bubbleSortRow(row);
                break;
            case MERGE:
                mergeSortRow(row);
                break;
        }
    }

    private static void mergeSortRow(List<Integer> row) {
        Collections.sort(row);
    }

    private static void bubbleSortRow(List<Integer> row) {
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

    private void sortBetweenRows() {
        Map<Integer, Integer> colCountersMap = getColCountersMap();
        Matrix sortedMatrix = MatrixFactory.emptyMatrix(m, n);
        Iterator<List<Integer>> rowIterator = sortedMatrix.iterator();

        List<Integer> row;
        Iterator<Integer> colIterator;
        Integer col;
        Integer thisCol;
        int thisColVal;
        Integer lowestRow;
        Integer lowestVal;
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            colIterator = row.iterator();
            while (colIterator.hasNext()) {
                col = colIterator.next();
                lowestRow = null;
                lowestVal = null;
                for (int i = 0; i < m; i++) {
                    thisCol = colCountersMap.get(i);
                    if (thisCol < n) {
                        thisColVal = rows.get(i).get(thisCol);
                        if (lowestRow == null) {
                            lowestRow = i;
                            lowestVal = thisColVal;
                        }
                        if (thisColVal < lowestVal) {
                            lowestVal = thisColVal;
                            lowestRow = i;
                        }
                    }
                }
                colCountersMap.put(lowestRow, colCountersMap.get(lowestRow) + 1);
                row.set(row.indexOf(col), lowestVal);
            }
        }

        this.rows = sortedMatrix.getRows();
    }

    private Map<Integer, Integer> getColCountersMap() {
        Map<Integer, Integer> colCountersMap = new HashMap<>(m*2);
        for (int i = 0; i < m; i ++) {
            colCountersMap.put(i, 0);
        }
        return colCountersMap;
    }

    public List<List<Integer>> getRows() {
        return rows;
    }

    public int mSize() {
        return m;
    }

    public int nSize() {
        return n;
    }

    public Iterator<List<Integer>> iterator() {
        return rows.iterator();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<List<Integer>> rowIterator = this.iterator();
        List<Integer> row;
        Iterator<Integer> colIterator;
        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            colIterator = row.iterator();
            while (colIterator.hasNext()) {
                stringBuilder.append(colIterator.next()).append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;

        if (m != matrix.m && n != matrix.n)
            return false;

        List<Integer> row1;
        List<Integer> row2;
        for (int i = 0; i < m; i++) {
            row1 = this.rows.get(i);
            row2 = matrix.rows.get(i);
            if (!row1.equals(row2))
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rows, m, n);
    }
}
