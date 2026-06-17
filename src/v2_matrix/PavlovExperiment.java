package v2_matrix;

import math.Matrix;
import math.Vector;

public class PavlovExperiment {
    public void main() {
        float[] weights_array = {1.0f, 0.0f};
        int numRows = 1;
        int numColumns = 2;
        Matrix weights_matrix = new Matrix(weights_array, numRows, numColumns);

        float[] input_array = {1.0f, 1.0f};
        Vector input_vector = new Vector(input_array);

        Brain brain = new Brain(weights_matrix);

        int CYCLES = 50;
        for (int i = 0; i < CYCLES; i++) {
            brain.simulateCycle(input_vector);
        }

        brain.getWeights().printMatrix();
    }
}