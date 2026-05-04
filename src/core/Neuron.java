package core;

public class Neuron {
    private float activation = 0;
    private float threshold;

    private final float DEFAULT_THRESHOLD = 0.5f;

    public Neuron () {
        this.threshold = DEFAULT_THRESHOLD;
    }

    public Neuron (float threshold) {
        if (threshold < 0 || threshold > 1) {
            throw new InvalidThresholdValue("The threshold must be between 0 and 1");
        }

        this.threshold = threshold;
    }

    public void addActivation (float n) {
        this.activation += n;
    }

    public boolean isFiring () {
        return (this.activation >= this.threshold);
    }

    void resetActivation () {
        this.activation = 0f;
    }
}
