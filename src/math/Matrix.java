package math;

import Exceptions.InvalidMatrixDimensionsException;
import math.tasks.MatrixMatrixMultiplicationTask;

public class Matrix {
    // A matrix is stored as a 2D array with row-major ordering
    private final float[][] matrix;
    // The number of rows and columns are stored for convenience
    private final int numRows;
    private final int numColumns;

    // Constructor
    public Matrix (float[] entries, int numRows, int numColumns) {
        // Check whether the number of rows and columns match the entries array
        if ((numRows * numColumns) != entries.length) {
            throw new InvalidMatrixDimensionsException("Matrix dimensions don't match the entries");
        }

        // Set up the fields
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.matrix = new float[numRows][numColumns];

        // Fill the 2D array based on 1D entries array
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numColumns; c++) {
                int arrayIndex = r * numColumns + c;
                this.matrix[r][c] = entries[arrayIndex];
            }
        }
    }

    // Package-private constructor to create a matrix from 2D array for internal usage
    Matrix (float[][] matrix) {
        this.matrix = matrix;
        this.numRows = matrix.length;
        this.numColumns = matrix[0].length;
    }

    // Getter for the 2D array of the matrix
    // The inner 2D array is given as a copy to avoid shared mutability errors
    public float[][] getMatrix () {
        return this.copy().matrix;
    }

    // Getter for the number of rows
    public int getNumRows () {return this.numRows; }

    // Getter for the number of columns
    public int getNumColumns () {return this.numColumns; }

    // Add another matrix to this matrix
    // The resulting matrix is returned as a separate new matrix
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

    // Subtract another matrix from this matrix
    // Substraction is implemented as addition with another matrix multiplied by -1
    public Matrix subtract(Matrix m) {
        if (this.numRows != m.numRows || this.numColumns != m.numColumns) {
            throw new InvalidMatrixDimensionsException("Subtraction is impossible as matrix dimensions do not match");
        }

        Matrix mNegated = m.multiply(-1);

        return this.add(mNegated);
    }

    // Multiply this matrix by a constant
    // The multiplication is performed on the copy of the inner 2D array to avoid shared mutability errors
    public Matrix multiply (float n) {
        Matrix m = this.copy();
        for (int r = 0; r < m.numRows; r++) {
            for (int c = 0; c < m.numColumns; c++) {
                m.matrix[r][c] *= n;
            }
        }

        return m;
    }

    // Multiply this matrix by a vector
    // Since a vector is a wrapper around matrix, just perform matrix-matrix multiplication
    public Vector multiply (Vector v) {
        return new Vector(this.multiply(v.vectorAsMatrix()));
    }

    // Multiply this matrix by another matrix (multithreaded version)
    // The resulting matrix is returned as a separate new matrix
    public Matrix multiply (Matrix m) {
        // Check whether the dimensions are correct for the multiplication
        if (this.numColumns != m.numRows) {
            throw new InvalidMatrixDimensionsException("Matrices dimensions are invalid for multiplication");
        }

        // Set up the fields for the resulting matrix
        float[][] leftMatrix = this.getMatrix();
        float[][] rightMatrix = m.getMatrix();
        int leftRows = leftMatrix.length;
        int rightColumns = rightMatrix[0].length;
        float[][] resultMatrix = new float[leftRows][rightColumns];

        // Get the number of available threads
        int numThreads = Math.min(Runtime.getRuntime().availableProcessors(), leftRows);
        // Create an array for available threads
        Thread[] threads = new Thread[numThreads];

        // Calculate the size of chunk to be processed by each thread
        int chunk = leftRows / numThreads;

        // Give a chunk to each thread
        for (int t = 0; t < numThreads; t++) {
            int startRow = t * chunk;
            // If it is the last thread, give all the remaining data to it
            int endRow = (t == numThreads - 1) ? numRows : startRow + chunk;

            // Assign a job to each thread and start it
            threads[t] = new Thread(new MatrixMatrixMultiplicationTask(leftMatrix, rightMatrix, resultMatrix, startRow, endRow));
            threads[t].start();
        }

        // Join each thread
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

    // Transpose this matrix
    // The resulting matrix is returned as a separate new matrix
    public Matrix transpose () {
        // Set up the fields for the resulting matrix
        int newNumRows = this.numColumns;
        int newNumColumns = this.numRows;
        int newNumEntries = newNumRows * newNumColumns;
        float[] newEntries = new float[newNumEntries];

        // Perform transposition
        for (int r = 0; r < this.numRows; r++) {
            for (int c = 0; c < this.numColumns; c++) {
                int newArrayIndex = c * numRows + r;
                newEntries[newArrayIndex] = this.matrix[r][c];
            }
        }

        return new Matrix(newEntries, newNumRows, newNumColumns);
    }

    // Perform Hadamard (element-wise) product of this matrix with another matrix
    // The resulting matrix is returned as a separate new matrix
    public Matrix HadamardProduct (Matrix m) {
        // Check whether the dimensions match
        if (this.numRows != m.numRows || this.numColumns != m.numColumns) {
            throw new InvalidMatrixDimensionsException("Addition is undefined as matrix dimensions do not match");
        }

        // Set up the fields for the resulting matrix
        int newNumRows = this.numRows;
        int newNumColumns = this.numColumns;
        int newNumEntries = newNumRows * newNumColumns;
        float[] newEntries = new float[newNumEntries];

        // Perform the Hadamard product
        for (int r = 0; r < newNumRows; r++) {
            for (int c = 0; c < newNumColumns; c++) {
                int newIndex = r * newNumColumns + c;
                newEntries[newIndex] = this.matrix[r][c] * m.matrix[r][c];
            }
        }

        return new Matrix (newEntries, newNumRows, newNumColumns);
    }

    // Perform the scalar shift (element-wise addition) of this matrix with a constant
    // The resulting matrix is returned as a separate new matrix
    public Matrix scalarShift (float n) {
        // Set up the fields for the resulting matrix
        int newNumRows = this.numRows;
        int newNumColumns = this.numColumns;
        int newNumEntries = newNumRows * newNumColumns;
        float[] newEntries = new float[newNumEntries];

        // Perform the scalar shift
        for (int r = 0; r < newNumRows; r++) {
            for (int c = 0; c < newNumColumns; c++) {
                int newIndex = r * newNumColumns + c;
                newEntries[newIndex] = this.matrix[r][c] + n;
            }
        }

        return new Matrix (newEntries, newNumRows, newNumColumns);
    }

    // Print this matrix
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

    // Make a copy of this matrix
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
}