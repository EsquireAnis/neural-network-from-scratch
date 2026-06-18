package v2_matrix;

import math.Matrix;
import math.Vector;

public class PavlovExperiment {
    public void main() {
        // Set up the weights:
        // 1. Maximum association between food and salivation
        // 2. No association between bell and salivation
        float[] weights_array = {1.0f, 0.0f};
        int numRows = 1;
        int numColumns = 2;
        Matrix weights_matrix = new Matrix(weights_array, numRows, numColumns);

        // Simulate the scenario when both food and bell are triggered
        float[] input_array = {1.0f, 1.0f};
        Vector input_vector = new Vector(input_array);

        Brain brain = new Brain(weights_matrix);

        // Set up the number of repetitions of the conditioning
        int CYCLES = 50;
        for (int i = 0; i < CYCLES; i++) {
            brain.simulateCycle(input_vector);
        }

        // Print the weights after conditioning
        brain.getWeights().printMatrix();
    }
}