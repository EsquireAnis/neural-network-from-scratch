package math.tasks;

public class HadamardProductTask implements Runnable {
    // Convention: The first matrix will be written into and used as a return matrix
    private final float[][] firstMatrix;
    private final float[][] secondMatrix;
    private final int startRow;
    private final int endRow;
    private final int numColumns;

    public HadamardProductTask (float[][] firstMatrix, float[][] secondMatrix, int startRow, int endRow, int numColumns) {
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.startRow = startRow;
        this.endRow = endRow;
        this.numColumns = numColumns;
    }

    @Override
    public void run () {
        for (int r = startRow; r < endRow; r++) {
            for (int c = 0; c < numColumns; c++) {
                firstMatrix[r][c] *= secondMatrix[r][c];
            }
        }
    }
}