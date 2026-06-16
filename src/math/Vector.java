package math;

public class Vector {
    private final Matrix m;

    public Vector (float[] entries) {
        this.m = new Matrix(entries, entries.length, 1);
    }

    private Vector (Matrix m) {
        float[][] matrix = m.getMatrix();

        if (matrix[0].length != 1) {
            throw new InvalidMatrixDimensionsException("This matrix cannot be treated as a vector");
        }

        float[] entries = new float[matrix.length];
        for (int r = 0; r < matrix.length; r++) {
            entries[r] = matrix[r][0];
        }

        this.m = new Matrix(entries, entries.length, 1);
    }

    public float[] getVector () {
        float[][] matrix = this.m.getMatrix();
        float[] entries = new float[matrix.length];

        for (int r = 0; r < matrix.length; r++) {
            entries[r] = matrix[r][0];
        }

        return entries;
    }

    public Vector add (Vector v) {
        float[] thisEntries = this.getVector();
        float[] vEntries = v.getVector();
        int length = thisEntries.length;

        if (!this.isSameLength(v)) {
            throw new InvalidMatrixDimensionsException("Addition is undefined as these vectors have different dimensions");
        }

        float[] newEntries = new float[length];
        for (int i = 0; i < length; i++) {
            newEntries[i] = thisEntries[i] + vEntries[i];
        }

        return new Vector (newEntries);
    }

    public Vector subtract(Vector v) {
        if (!this.isSameLength(v)) {
            throw new InvalidMatrixDimensionsException("Subtraction is undefined as these vectors have different dimensions");
        }

        return this.add(v.scale(-1));
    }

    public Vector scale (float n) {
        float[] entries = this.getVector();

        for (int i = 0; i < entries.length; i++) {
            entries[i] *= n;
        }

        return new Vector(entries);
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
        StringBuilder vectorAsText = new StringBuilder("VECTOR CEILING\n");

        for (float entry : this.getVector()) {
            vectorAsText.append(Float.toString(entry)).append("\n");
        }
        vectorAsText.append("VECTOR FLOOR");

        System.out.println(vectorAsText);
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