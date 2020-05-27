import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MatrixFactory {

    public static Matrix randomMatrix(int m, int n, int upperBound) {
        List<List<Integer>> rows = new ArrayList<>(m);
        Random random = new Random();

        List<Integer> row;
        for (int i = 0; i < m; i++) {
            row = new ArrayList<>(n);
            for (int j = 0; j < n; j++) {
                row.add(random.nextInt(upperBound));
            }
            rows.add(row);
        }

        return new Matrix(rows);
    }

    public static Matrix emptyMatrix(int m, int n) {
        List<List<Integer>> rows = new ArrayList<>(m);

        List<Integer> row;
        for (int i = 0; i < m; i++) {
            row = new ArrayList<>(n);
            for (int j = 0; j < n; j++) {
                row.add(null);
            }
            rows.add(row);
        }

        return new Matrix(rows);
    }
}
