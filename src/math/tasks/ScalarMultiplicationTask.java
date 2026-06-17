package math.tasks;

public class ScalarMultiplicationTask implements Runnable {
    private final float[][] matrix;
    private final float scalar;
    private final int startRow;
    private final int endRow;
    private final int numColumns;

    public ScalarMultiplicationTask (float[][] matrix, float scalar, int startRow, int endRow, int numColumns) {
        this.matrix = matrix;
        this.scalar = scalar;
        this.startRow = startRow;
        this.endRow = endRow;
        this.numColumns = numColumns;
    }

    @Override
    public void run () {
        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < numColumns; j++) {
                matrix[i][j] *= scalar;
            }
        }
    }
}