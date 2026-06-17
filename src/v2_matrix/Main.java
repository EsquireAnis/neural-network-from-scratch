package v2_matrix;

import math.Matrix;
import math.Vector;

public class Main {
    public static void main(String[] args) {
        float[] weights_array = {1.0f, 0.0f};
        int numRows = 1;
        int numColumns = 2;
        Matrix weights_matrix = new Matrix(weights_array, numRows, numColumns);
        Brain brain = new Brain(weights_matrix);

        float[] input_array = {1.0f, 1.0f};
        Vector input_vector = new Vector(input_array);

        int CYCLES = 1000000;
        long start = System.nanoTime();
        for (int i = 0; i < CYCLES; i++) {
            brain.simulateCycle(input_vector);
        }
        long end = System.nanoTime();
        long time = (end - start) / 1_000_000;

        System.out.println("THE WEIGHTS AFTER " + CYCLES + " ITERATIONS:");
        brain.getWeights().printMatrix();
        System.out.println("RUNNING TIME: " + time);
    }
}