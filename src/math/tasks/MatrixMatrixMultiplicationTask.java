package math.tasks;

public class MatrixMatrixMultiplicationTask implements Runnable {
    private final float[][] leftMatrix;
    private final float[][] rightMatrix;
    private final float[][] resultMatrix;
    private final int leftColumns;
    private final int rightColumns;
    private final int startRow;
    private final int endRow;

    public MatrixMatrixMultiplicationTask (float[][] leftMatrix, float[][] rightMatrix, float[][] resultMatrix, int startRow, int endRow) {
        this.leftMatrix = leftMatrix;
        this.rightMatrix = rightMatrix;
        this.startRow = startRow;
        this.endRow = endRow;
        this.leftColumns = leftMatrix[0].length;
        this.rightColumns = rightMatrix[0].length;
        this.resultMatrix = resultMatrix;
    }

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