public class SortMatrixDemo {

    public static void main(String[] args) {
        final int M = 100;
        final int N = M;
        final int VAL_UPPER_BOUND = 1000;
        final boolean FORKED = true;

        Matrix matrix = MatrixFactory.randomMatrix(M, N, VAL_UPPER_BOUND);
        matrix.sort(FORKED);
    }
}
