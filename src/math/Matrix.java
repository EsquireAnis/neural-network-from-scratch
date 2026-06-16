package math;

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
        Matrix m = this.copy();
        for (int r = 0; r < m.numRows; r++) {
            for (int c = 0; c < m.numColumns; c++) {
                m.matrix[r][c] *= n;
            }
        }

        return m;
    }

    public Vector multiply (Vector v) {
        return new Vector(this.multiply(v.vectorAsMatrix()));
    }

    public Matrix multiply (Matrix m) {
        if (this.numColumns != m.numRows) {
            throw new InvalidMatrixDimensionsException("Matrices dimensions are invalid for multiplication");
        }

        int newNumRows = this.numRows;
        int newNumColumns = m.numColumns;
        int newNumEntries = newNumRows * newNumColumns;
        float[] newEntries = new float[newNumEntries];

        for (int rLeft = 0; rLeft < this.numRows; rLeft++) {
            for (int cRight = 0; cRight < m.numColumns; cRight++) {
                int newIndex = rLeft * newNumColumns + cRight;
                float newEntry = 0;

                for (int k = 0; k < this.numColumns; k++) {
                    newEntry += (this.matrix[rLeft][k] * m.matrix[k][cRight]);
                }

                newEntries[newIndex] = newEntry;
            }
        }

        return new Matrix(newEntries, newNumRows, newNumColumns);
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

        int newNumRows = this.numRows;
        int newNumColumns = this.numColumns;
        int newNumEntries = newNumRows * newNumColumns;
        float[] newEntries = new float[newNumEntries];

        for (int r = 0; r < newNumRows; r++) {
            for (int c = 0; c < newNumColumns; c++) {
                int newIndex = r * newNumColumns + c;
                newEntries[newIndex] = this.matrix[r][c] * m.matrix[r][c];
            }
        }

        return new Matrix (newEntries, newNumRows, newNumColumns);
    }

    public Matrix HadamardAddition (float n) {
        int newNumRows = this.numRows;
        int newNumColumns = this.numColumns;
        int newNumEntries = newNumRows * newNumColumns;
        float[] newEntries = new float[newNumEntries];

        for (int r = 0; r < newNumRows; r++) {
            for (int c = 0; c < newNumColumns; c++) {
                int newIndex = r * newNumColumns + c;
                newEntries[newIndex] = this.matrix[r][c] + n;
            }
        }

        return new Matrix (newEntries, newNumRows, newNumColumns);
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
}