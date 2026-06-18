package v1_biological;

import v1_biological.Exceptions.InvalidThresholdValueException;

public class Neuron {
    // Default values
    private static final float DEFAULT_THRESHOLD = 0.5f;
    private static final String DEFAULT_NAME = "Generic neuron";

    // The neuron has an activation level (initially zero), activation threshold, and name
    private float activation = 0;
    private float threshold = DEFAULT_THRESHOLD;
    private final String name;

    // Constructor when no name is provided (set the default one)
    public Neuron () {
        this.name = DEFAULT_NAME;
    }

    // Constructor with a name provided
    public Neuron (String name) {
        this.name = name;
    }

    // Set neuron to fully active
    void activate () {
        this.activation = 1;
    }

    // Increase the activation of the neuron
    void addActivation (float n) {
        this.activation += n;
    }

    // Set neuron to fully inactive
    void resetActivation () {
        this.activation = 0;
    }

    // Determine whether the neuron is currently firing (current activation > threshold?)
    public boolean isFiring () {
        return (this.activation >= this.threshold);
    }

    // Change the threshold value
    void setupThreshold (float threshold) {
        if (threshold < 0 || threshold > 1) {
            throw new InvalidThresholdValueException("The threshold must be between 0 and 1");
        }

        this.threshold = threshold;
    }

    // Getter for the name
    public String getName () {
        return this.name;
    }
}