public class Matrix {
    private float[][] matrix;
    private int numRows;
    private int numColumns;

    public Matrix (float[] entries, int numRows, int numColumns) {
        if ((numRows * numColumns) != entries.length) {
            // An error
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

    public Matrix matrixMultiply (Matrix m) {
        if (this.numColumns != m.numRows) {
            // An error
        }

        int newNumRows = this.numRows;
        int newNumColumns = m.numColumns;
        int newNumEntries = newNumRows * newNumColumns;
        float[] newEntries = new float[newNumEntries];

        // Add logic for multiplication

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

    public float[][] getMatrix () {
        return this.matrix;
    }

    public void printMatrix () {
        String matrixAsText = "CEILING\n";

        for (int r = 0; r < this.numRows; r++) {
            for (int c = 0; c < this.numColumns; c++) {
                matrixAsText += Float.toString(this.matrix[r][c]) + " ";
            }
            if (r != (this.numRows - 1)) {
                matrixAsText += "\n";
            }
            else {
                matrixAsText += "\nFLOOR";
            }
        }

        System.out.println(matrixAsText);
    }

    public static void main (String[] args) {
        float[] entries1 = {1, 2, 3, 4, 5, 6};
        Matrix m1 = new Matrix(entries1, 2, 3);

        m1.printMatrix();

        float[][] matrixAsDS = m1.getMatrix();
        System.out.println(matrixAsDS[0][1]);

        Matrix m1Transposed = m1.transpose();
        m1Transposed.printMatrix();

        float[] entries2 = {10, 11, 20, 21, 30, 31};
        Matrix m2 = new Matrix(entries2, 3, 2);
        m2.printMatrix();
        Matrix m1Multm2 = m1.matrixMultiply(m2);
        m1Multm2.printMatrix();
    }
}
