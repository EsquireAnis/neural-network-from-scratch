package math;

import math.tasks.HadamardProductTask;
import math.tasks.MatrixMatrixMultiplicationTask;
import math.tasks.ScalarMultiplicationTask;
import math.tasks.ScalarShiftTask;

public class Matrix {
    private final float[][] matrix;
    private final int numRows;
    private final int numColumns;

    public Matrix (float[] entries, int numRows, int numColumns) {
        if ((numRows * numColumns) != entries.length) {
            throw new InvalidMatrixDimensionsException("Matrix dimensions don't match the entries");
        }

        this.numRows = numRows;
        this.numColumns = numColumns;
        this.matrix = new float[numRows][numColumns];

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numColumns; c++) {
                int arrayIndex = r * numColumns + c;
                this.matrix[r][c] = entries[arrayIndex];
            }
        }
    }

    Matrix (float[][] matrix) {
        this.matrix = matrix;
        this.numRows = matrix.length;
        this.numColumns = matrix[0].length;
    }

    public float[][] getMatrix () {
        return this.copy().matrix;
    }

    public int getNumRows () {return this.numRows; }

    public int getNumColumns () {return this.numColumns; }

    public Matrix add (Matrix m) {
        if (this.numRows != m.numRows || this.numColumns != m.numColumns) {
            throw new InvalidMatrixDimensionsException("Addition is undefined as matrix dimensions do not match");
        }

        int newNumRows = this.numRows;
        int newNumColumns = this.numColumns;
        int newNumEntries = newNumRows * newNumColumns;
        float[] newEntries = new float[newNumEntries];

        for (int r = 0; r < newNumRows; r++) {
            for (int c = 0; c < newNumColumns; c++) {
                int newIndex = r * newNumColumns + c;
                newEntries[newIndex] = this.matrix[r][c] + m.matrix[r][c];
            }
        }

        return new Matrix (newEntries, newNumRows, newNumColumns);
    }

    public Matrix subtract(Matrix m) {
        if (this.numRows != m.numRows || this.numColumns != m.numColumns) {
            throw new InvalidMatrixDimensionsException("Subtraction is impossible as matrix dimensions do not match");
        }

        Matrix mNegated = m.multiply(-1);

        return this.add(mNegated);
    }

    public Matrix multiply (float n) {
        float[][] m = this.getMatrix();
        int numRows = m.length;

        int numThreads = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[numThreads];

        int chunk = numRows / numThreads;

        for (int t = 0; t < numThreads; t++) {
            int startRow = t * chunk;
            int endRow = (t == numThreads - 1) ? numRows : startRow + chunk;

            threads[t] = new Thread(new ScalarMultiplicationTask(m, n, startRow, endRow));
            threads[t].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new Matrix (m);
    }

    public Vector multiply (Vector v) {
        return new Vector(this.multiply(v.vectorAsMatrix()));
    }

    public Matrix multiply (Matrix m) {
        if (this.numColumns != m.numRows) {
            throw new InvalidMatrixDimensionsException("Matrices dimensions are invalid for multiplication");
        }

        float[][] leftMatrix = this.getMatrix();
        float[][] rightMatrix = m.getMatrix();
        int leftRows = leftMatrix.length;
        int rightColumns = rightMatrix[0].length;
        float[][] resultMatrix = new float[leftRows][rightColumns];

        int numThreads = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[numThreads];

        int chunk = leftRows / numThreads;

        for (int t = 0; t < numThreads; t++) {
            int startRow = t * chunk;
            int endRow = (t == numThreads - 1) ? numRows : startRow + chunk;

            threads[t] = new Thread(new MatrixMatrixMultiplicationTask(leftMatrix, rightMatrix, resultMatrix, startRow, endRow));
            threads[t].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new Matrix(resultMatrix);
    }

    public Matrix transpose () {
        int newNumRows = this.numColumns;
        int newNumColumns = this.numRows;
        int newNumEntries = newNumRows * newNumColumns;
        float[] newEntries = new float[newNumEntries];

        for (int r = 0; r < this.numRows; r++) {
            for (int c = 0; c < this.numColumns; c++) {
                int newArrayIndex = c * numRows + r;
                newEntries[newArrayIndex] = this.matrix[r][c];
            }
        }

        return new Matrix(newEntries, newNumRows, newNumColumns);
    }

    public Matrix HadamardProduct (Matrix m) {
        if (this.numRows != m.numRows || this.numColumns != m.numColumns) {
            throw new InvalidMatrixDimensionsException("Addition is undefined as matrix dimensions do not match");
        }

        float[][] m1 = this.getMatrix();
        float[][] m2 = m.getMatrix();
        int numRows = m1.length;

        int numThreads = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[numThreads];

        int chunk = numRows / numThreads;

        for (int t = 0; t < numThreads; t++) {
            int startRow = t * chunk;
            int endRow = (t == numThreads - 1) ? numRows : startRow + chunk;

            threads[t] = new Thread(new HadamardProductTask(m1, m2, startRow, endRow));
            threads[t].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new Matrix(m1);
    }

    public Matrix scalarShift (float n) {
        float[][] m = this.getMatrix();
        int numRows = m.length;

        int numThreads = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[numThreads];

        int chunk = numRows / numThreads;

        for (int t = 0; t < numThreads; t++) {
            int startRow = t * chunk;
            int endRow = (t == numThreads - 1) ? numRows : startRow + chunk;

            threads[t] = new Thread(new ScalarShiftTask(m, n, startRow, endRow));
            threads[t].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new Matrix (m);
    }

    public void printMatrix () {
        StringBuilder matrixAsText = new StringBuilder("CEILING\n");

        for (int r = 0; r < this.numRows; r++) {
            for (int c = 0; c < this.numColumns; c++) {
                matrixAsText.append(Float.toString(this.matrix[r][c])).append(" ");
            }
            if (r != (this.numRows - 1)) {
                matrixAsText.append("\n");
            }
            else {
                matrixAsText.append("\nFLOOR");
            }
        }

        System.out.println(matrixAsText);
    }

    public Matrix copy () {
        int newNumRows = this.numRows;
        int newNumColumns = this.numColumns;
        int newNumEntries = newNumRows * newNumColumns;
        float[] newEntries = new float[newNumEntries];

        for (int r = 0; r < this.numRows; r++) {
            for (int c = 0; c < this.numColumns; c++) {
                int newIndex = r * this.numColumns + c;
                newEntries[newIndex] = this.matrix[r][c];
            }
        }

        return new Matrix (newEntries, newNumRows, newNumColumns);
    }

    public static void main(String[] args) {
        float[] entries1 = {1, 2, 3, 4, 5, 6};
        Matrix m1 = new Matrix(entries1, 3, 2);
        m1.printMatrix();

        float[] entries2 = {7, 8, 9, 10, 11, 12};
        Matrix m2 = new Matrix(entries2, 2, 3);
        m2.printMatrix();

        m1.multiply(m2).printMatrix();
    }
}