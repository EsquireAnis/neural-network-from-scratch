package v2_matrix;

import math.Matrix;
import math.Vector;

public class Brain {
    private Matrix weights;

    // LEARNING_RATE must be between 0 (no learning) and 1 (immediate learning)
    private float LEARNING_RATE = 0.2f;

    public Brain (Matrix weights) {
        this.weights = weights;
    }

    public Brain (Matrix weights, float LEARNING_RATE) {
        this.weights = weights;
        if (LEARNING_RATE >= 0.0f && LEARNING_RATE <= 1.0f) {
            this.LEARNING_RATE = LEARNING_RATE;
        }
        else {
            throw new InvalidLearningRateException("Learning rate must be between 0 and 1");
        }
    }

    private Vector calculateOutputNeurons (Vector inputNeurons) {
        return weights.multiply(inputNeurons);
    }

    private Matrix calculateDeltaWeights (Vector inputNeurons, Vector outputNeurons) {
        Matrix deltaWeights = (outputNeurons.outerProduct(inputNeurons)).multiply(LEARNING_RATE);
        return deltaWeights;
    }

    private Matrix normalizeDeltaWeights (Matrix deltaWeights) {
        Matrix availableCapacity = (this.weights.multiply(-1)).HadamardAddition(1);
        Matrix normalizedDeltaWeights = deltaWeights.HadamardProduct(availableCapacity);

        return normalizedDeltaWeights;
    }

    private void updateWeights (Matrix normalizedDeltaWeights) {
        this.weights = this.weights.add(normalizedDeltaWeights);
    }

    private void simulateCycle(Vector inputNeurons) {
        Vector outputNeurons = this.calculateOutputNeurons(inputNeurons);
        Matrix deltaWeights = this.calculateDeltaWeights(inputNeurons, outputNeurons);
        Matrix normalizedDeltaWeights = this.normalizeDeltaWeights(deltaWeights);
        this.updateWeights(normalizedDeltaWeights);
    }

    public static void main(String[] args) {
        float[] weights_array = {1.0f, 0.0f};
        Matrix weights = new Matrix(weights_array, 1 , 2);
        Brain brain = new Brain(weights);

        float[] input_array = {1.0f, 1.0f};
        Vector inputNeurons = new Vector(input_array);

        brain.simulateCycle(inputNeurons);
        brain.simulateCycle(inputNeurons);
        brain.simulateCycle(inputNeurons);
        brain.simulateCycle(inputNeurons);
        brain.simulateCycle(inputNeurons);
        brain.simulateCycle(inputNeurons);
        brain.weights.printMatrix();
    }
}