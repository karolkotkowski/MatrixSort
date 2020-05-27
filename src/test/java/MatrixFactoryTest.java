import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixFactoryTest {

    @Test
    void shouldCreateRandomMatrix() {
        int m = 5;
        int n = 10;
        int upperBound = 1000;

        Matrix matrix1 = MatrixFactory.randomMatrix(m, n, upperBound);
        Matrix matrix2 = MatrixFactory.randomMatrix(m, n, upperBound);

        assertEquals(m, matrix1.mSize());
        assertEquals(n, matrix1.nSize());
        assertNotEquals(matrix1, matrix2);
    }

}