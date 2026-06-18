package v2_matrix;

import Exceptions.InvalidLearningRateException;
import math.Matrix;
import math.Vector;

public class Brain {
    // A matrix is used as a storage for weights
    private Matrix weights;
    // LEARNING_RATE is the speed of learning
    // LEARNING_RATE must be between 0 (no learning) and 1 (immediate learning)
    // Empirically, 0.03 matches the real experiment results the best
    private float LEARNING_RATE = 0.03f;
    // Activation threshold determines when a neuron is considered to be active
    // Given that the activation is between 0 and 1, 0.5 is the logical threshold value
    private float ACTIVATION_THRESHOLD = 0.5f;

    // Constructor when a learning rate is not provided (the default value is used)
    public Brain (Matrix weights) {
        this.weights = weights;
    }

    // Constructor when a learning rate is provided
    public Brain (Matrix weights, float LEARNING_RATE) {
        this.weights = weights;
        // Ensure that the learning rate value is in the correct range
        if (LEARNING_RATE >= 0.0f && LEARNING_RATE <= 1.0f) {
            this.LEARNING_RATE = LEARNING_RATE;
        }
        else {
            throw new InvalidLearningRateException("Learning rate must be between 0 and 1");
        }
    }

    // Getter for the weights
    public Matrix getWeights () {
        return this.weights.copy();
    }

    // Calculate the output neurons' values
    // Mathematically, W * I^T, where:
    // W is weights,
    // I^T is input neurons' values transposed
    private Vector calculateOutputNeurons (Vector inputNeurons) {
        return weights.multiply(inputNeurons);
    }

    // Check which neurons' activation values surpass the threshold
    // If the activation is below the threshold, treat the neuron as inactive
    private Vector applyThreshold (Vector outputNeurons) {
        float[] entries = outputNeurons.getVector();
        for (int i = 0; i < entries.length; i++) {
            if (entries[i] < this.ACTIVATION_THRESHOLD) {
                entries[i] = 0;
            }
        }

        return new Vector(entries);
    }

    // Calculate the values to be added to each weight
    // Mathematically, L * (O x I), where:
    // L is the learning rate,
    // O x I is the outer product between output and input vectors
    private Matrix calculateDeltaWeights (Vector inputNeurons, Vector outputNeurons) {
        Matrix deltaWeights = (outputNeurons.outerProduct(inputNeurons)).multiply(LEARNING_RATE);
        return deltaWeights;
    }

    // Normalize the values to be added to each weight
    // Mathematically, (1 - W) (*) dW, where:
    // 1 - W = -W + 1, negated weights matrix shifted by one,
    // dW is the values to be added to each weight,
    // (*) is the Hadamard product
    private Matrix normalizeDeltaWeights (Matrix deltaWeights) {
        Matrix availableCapacity = (this.weights.multiply(-1)).scalarShift(1);
        Matrix normalizedDeltaWeights = deltaWeights.HadamardProduct(availableCapacity);

        return normalizedDeltaWeights;
    }

    // Add normalized values to be added to each weight to the matrix of weights
    private void updateWeights (Matrix normalizedDeltaWeights) {
        this.weights = this.weights.add(normalizedDeltaWeights);
    }

    // Simulate one full cycle
    public void simulateCycle(Vector inputNeurons) {
        Vector outputNeurons = this.calculateOutputNeurons(inputNeurons);
        Vector activatedOutputNeurons = this.applyThreshold(outputNeurons);
        Matrix deltaWeights = this.calculateDeltaWeights(inputNeurons, activatedOutputNeurons);
        Matrix normalizedDeltaWeights = this.normalizeDeltaWeights(deltaWeights);
        this.updateWeights(normalizedDeltaWeights);
    }
}