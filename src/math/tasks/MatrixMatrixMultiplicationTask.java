package math.tasks;

public class MatrixMatrixMultiplicationTask implements Runnable {
    // A task to be assigned to a thread to perform matrix-matrix multiplication

    // Set up all the necessary for the calculation fields
    private final float[][] leftMatrix;
    private final float[][] rightMatrix;
    // The result matrix is shared across each thread
    // Then, each thread writes into its part of the matrix
    private final float[][] resultMatrix;
    private final int leftColumns;
    private final int rightColumns;
    private final int startRow;
    private final int endRow;

    // Constructor
    public MatrixMatrixMultiplicationTask (float[][] leftMatrix, float[][] rightMatrix, float[][] resultMatrix, int startRow, int endRow) {
        this.leftMatrix = leftMatrix;
        this.rightMatrix = rightMatrix;
        this.startRow = startRow;
        this.endRow = endRow;
        this.leftColumns = leftMatrix[0].length;
        this.rightColumns = rightMatrix[0].length;
        this.resultMatrix = resultMatrix;
    }

    // Perform matrix-matrix multiplication for a given range of rows
    @Override
    public void run () {
        for (int r = startRow; r < endRow; r++) {
            for (int c = 0; c < rightColumns; c++) {
                float value = 0;

                for (int k = 0; k < leftColumns; k++) {
                    value += leftMatrix[r][k] * rightMatrix[k][c];
                }

                resultMatrix[r][c] = value;
            }
        }
    }
}