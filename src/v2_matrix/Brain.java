package v2_matrix;

import math.Matrix;
import math.Vector;

public class Brain {
    private Matrix weights;
    // LEARNING_RATE must be between 0 (no learning) and 1 (immediate learning)
    // Empirically, 0.03 matches the real experiment results the best
    private float LEARNING_RATE = 0.03f;
    // Given that the activation is between 0 and 1, 0.5 is the logical threshold value
    private float ACTIVATION_THRESHOLD = 0.5f;

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

    public Matrix getWeights () {
        return this.weights.copy();
    }

    private Vector calculateOutputNeurons (Vector inputNeurons) {
        return weights.multiply(inputNeurons);
    }

    private Vector applyThreshold (Vector outputNeurons) {
        float[] entries = outputNeurons.getVector();
        for (int i = 0; i < entries.length; i++) {
            if (entries[i] < this.ACTIVATION_THRESHOLD) {
                entries[i] = 0;
            }
        }

        return new Vector(entries);
    }

    private Matrix calculateDeltaWeights (Vector inputNeurons, Vector outputNeurons) {
        Matrix deltaWeights = (outputNeurons.outerProduct(inputNeurons)).multiply(LEARNING_RATE);
        return deltaWeights;
    }

    private Matrix normalizeDeltaWeights (Matrix deltaWeights) {
        Matrix availableCapacity = (this.weights.multiply(-1)).scalarShift(1);
        Matrix normalizedDeltaWeights = deltaWeights.HadamardProduct(availableCapacity);

        return normalizedDeltaWeights;
    }

    private void updateWeights (Matrix normalizedDeltaWeights) {
        this.weights = this.weights.add(normalizedDeltaWeights);
    }

    public void simulateCycle(Vector inputNeurons) {
        Vector outputNeurons = this.calculateOutputNeurons(inputNeurons);
        Vector activatedOutputNeurons = this.applyThreshold(outputNeurons);
        Matrix deltaWeights = this.calculateDeltaWeights(inputNeurons, activatedOutputNeurons);
        Matrix normalizedDeltaWeights = this.normalizeDeltaWeights(deltaWeights);
        this.updateWeights(normalizedDeltaWeights);
    }
}