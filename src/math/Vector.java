package math;

import Exceptions.InvalidMatrixDimensionsException;

public class Vector {
    // The vector is treated as a matrix under the hood
    private final Matrix m;

    // Public constructor (build a vector based on entries provided)
    public Vector (float[] entries) {
        this.m = new Matrix(entries, entries.length, 1);
    }

    // Package-private constructor for internal usage
    // (build a vector from matrix)
    Vector (Matrix m) {
        float[][] matrix = m.getMatrix();

        // Check whether the matrix is actually a vector
        if (matrix[0].length != 1) {
            throw new InvalidMatrixDimensionsException("This matrix cannot be treated as a vector");
        }

        this.m = m;
    }

    // Getter for the 1D array of this vector's entries
    public float[] getVector () {
        float[][] matrix = this.m.getMatrix();
        float[] entries = new float[matrix.length];

        for (int r = 0; r < matrix.length; r++) {
            entries[r] = matrix[r][0];
        }

        return entries;
    }

    // Package-private method to return the inner matrix of this vector for internal usage
    // The matrix is given as a copy to avoid shared mutability errors
    Matrix vectorAsMatrix () {
        return this.m.copy();
    }

    // Add another vector to this vector
    // Addition of vectors is treated as a matrix addition internally
    public Vector add (Vector v) {
        return new Vector(this.m.add(v.m));
    }

    // Subtract another vector from this vector
    // Subtraction of vectors is treated as a matrix addition internally
    public Vector subtract(Vector v) {
        return new Vector(this.m.subtract(v.m));
    }

    // Scale this vector by a constant
    // Scaling of a vector is treated as a matrix-constant multiplication internally
    public Vector scale (float n) {
        return new Vector(this.m.multiply(n));
    }

    // Perform the dot product of this vector with another vector
    public float dotProduct (Vector v) {
        float[] thisEntries = this.getVector();
        float[] vEntries = v.getVector();
        int length = thisEntries.length;

        // Check whether the vectors' dimensions match
        if (!this.isSameDimensions(v)) {
            throw new InvalidMatrixDimensionsException("Dot product is undefined as these vectors have different dimensions");
        }

        float result = 0;
        for (int i = 0; i < length; i++) {
            result += (thisEntries[i] * vEntries[i]);
        }

        return result;
    }

    // Perform the outer product of this vector with another vector
    // Outer product is treated as matrix-matrix multiplication with the second vector transposed internally
    public Matrix outerProduct (Vector v) {
        return this.m.multiply(v.m.transpose());
    }

    // Print this vector
    // Printing is done via printing method of the internal matrix
    public void printVector () {
        this.m.printMatrix();
    }

    // Check whether this vector has the same dimensions as another one
    private boolean isSameDimensions(Vector v) {
        float[] thisEntries = this.getVector();
        float[] vEntries = v.getVector();

        return (vEntries.length == thisEntries.length);
    }

    // Copy this vector
    // Copying is done via copying method of the internal matrix
    public Vector copy () {
        return new Vector(this.m.copy());
    }
}