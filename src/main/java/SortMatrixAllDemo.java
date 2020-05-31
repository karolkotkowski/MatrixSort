public class SortMatrixAllDemo {
    public static void main(String[] args) {
        final int M = 100;
        final int N = M;
        final int VALUE_UPPER_BOUND = 1000;

        Matrix matrix = MatrixFactory.randomMatrix(M, N, VALUE_UPPER_BOUND);

        System.out.println("\nStream bubble:");
        Matrix matrix1 = new Matrix(matrix.getRows());
        matrix1.sort(ProcessingMethod.STREAM, SortingAlgorithm.BUBBLE);

        System.out.println("\nStream merge:");
        Matrix matrix2 = new Matrix(matrix.getRows());
        matrix2.sort(ProcessingMethod.STREAM, SortingAlgorithm.MERGE);

        System.out.println("\nParallel bubble:");
        Matrix matrix3 = new Matrix(matrix.getRows());
        matrix3.sort(ProcessingMethod.PARALLELSTREAM, SortingAlgorithm.BUBBLE);

        System.out.println("\nParallel merge:");
        Matrix matrix4 = new Matrix(matrix.getRows());
        matrix4.sort(ProcessingMethod.PARALLELSTREAM, SortingAlgorithm.MERGE);
    }
}
