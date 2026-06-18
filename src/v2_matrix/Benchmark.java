package v2_matrix;

import math.Matrix;
import math.Vector;

public class Benchmark {
    public static void main(String[] args) {
        // Benchmark to measure the time required for running method "simulateCycle"

        // Choose the number of cycles
        int CYCLES = 100;
        // Choose the dimensions of the square matrix
        int size = 3000;

        // Fill the matrix with random numbers
        float[] weights_array = new float[size * size];
        for (int i = 0; i < weights_array.length; i++) {
            weights_array[i] = (float) Math.random();
        }

        // Create the matrix and the brain
        Matrix weights_matrix = new Matrix(weights_array, size, size);
        Brain brain = new Brain(weights_matrix);

        // Fill input vector with random values
        float[] input_array = new float[size];
        for (int i = 0; i < size; i++) {
            input_array[i] = (float) Math.random();
        }

        // Create input vector
        Vector input_vector = new Vector(input_array);

        // Warm-up to invoke JIT compiler, so the true speed can be measured afterwards
        for (int i = 0; i < 50; i++) {
            brain.simulateCycle(input_vector);
        }

        // Start the timer
        long start = System.nanoTime();
        // Run simulations
        for (int i = 0; i < CYCLES; i++) {
            brain.simulateCycle(input_vector);
        }
        // Stop the timer
        long end = System.nanoTime();

        // Calculate the time taken to run simulations
        long time = (end - start) / 1_000_000;
        // Print the time taken to run simulations
        System.out.println("RUNNING TIME (ms): " + time);
    }
}