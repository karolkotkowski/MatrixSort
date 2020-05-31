public class SortMatrixDemo {

    public static void main(String[] args) {
        final int M = 10;
        final int N = M;
        final int VALUE_UPPER_BOUND = 1000;
        final SortingAlgorithm algorithm = SortingAlgorithm.MERGE;
        final ProcessingMethod method = ProcessingMethod.PARALLELSTREAM;

        Matrix matrix = MatrixFactory.randomMatrix(M, N, VALUE_UPPER_BOUND);
        matrix.sort(method, algorithm);
        System.out.println(matrix);
    }
}
