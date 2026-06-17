package v3_parallel;

import math.Matrix;
import math.Vector;

public class Benchmark {
    public static void main(String[] args) {

        int CYCLES = 500;
        int size = 1000;

        float[] weights_array = new float[size * size];
        for (int i = 0; i < weights_array.length; i++) {
            weights_array[i] = (float) Math.random();
        }

        Matrix weights_matrix = new Matrix(weights_array, size, size);
        Brain brain = new Brain(weights_matrix);

        float[] input_array = new float[size];
        for (int i = 0; i < size; i++) {
            input_array[i] = (float) Math.random();
        }

        Vector input_vector = new Vector(input_array);

        // Warm-up
        for (int i = 0; i < 50; i++) {
            brain.simulateCycle(input_vector);
        }

        long start = System.nanoTime();
        for (int i = 0; i < CYCLES; i++) {
            brain.simulateCycle(input_vector);
        }
        long end = System.nanoTime();

        long time = (end - start) / 1_000_000;
        System.out.println("RUNNING TIME (ms): " + time);
    }
}