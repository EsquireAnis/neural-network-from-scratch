package math;

public class Vector {
    private final Matrix m;

    public Vector (float[] entries) {
        this.m = new Matrix(entries, entries.length, 1);
    }

    Vector (Matrix m) {
        float[][] matrix = m.getMatrix();

        if (matrix[0].length != 1) {
            throw new InvalidMatrixDimensionsException("This matrix cannot be treated as a vector");
        }

        this.m = m;
    }

    public float[] getVector () {
        float[][] matrix = this.m.getMatrix();
        float[] entries = new float[matrix.length];

        for (int r = 0; r < matrix.length; r++) {
            entries[r] = matrix[r][0];
        }

        return entries;
    }

    Matrix vectorAsMatrix () {
        return this.m.copy();
    }

    public Vector add (Vector v) {
        return new Vector(this.m.add(v.m));
    }

    public Vector subtract(Vector v) {
        return new Vector(this.m.subtract(v.m));
    }

    public Vector scale (float n) {
        return new Vector(this.m.multiply(n));
    }

    public float dotProduct (Vector v) {
        float[] thisEntries = this.getVector();
        float[] vEntries = v.getVector();
        int length = thisEntries.length;

        if (!this.isSameLength(v)) {
            throw new InvalidMatrixDimensionsException("Dot product is undefined as these vectors have different dimensions");
        }

        float result = 0;
        for (int i = 0; i < length; i++) {
            result += (thisEntries[i] * vEntries[i]);
        }

        return result;
    }

    public Matrix outerProduct (Vector v) {
        return this.m.multiply(v.m.transpose());
    }

    public void printVector () {
        this.m.printMatrix();
    }

    private boolean isSameLength (Vector v) {
        float[] thisEntries = this.getVector();
        float[] vEntries = v.getVector();

        return (vEntries.length == thisEntries.length);
    }

    public Vector copy () {
        return new Vector(this.m.copy());
    }
}