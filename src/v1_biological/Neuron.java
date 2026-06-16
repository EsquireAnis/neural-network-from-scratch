package v1_biological;

public class Neuron {
    private static final float DEFAULT_THRESHOLD = 0.5f;
    private static final String DEFAULT_NAME = "Generic neuron";

    private float activation = 0;
    private float threshold = DEFAULT_THRESHOLD;
    private final String name;

    public Neuron () {
        this.name = DEFAULT_NAME;
    }

    public Neuron (String name) {
        this.name = name;
    }

    void activate () {
        this.activation = 1;
    }

    void addActivation (float n) {
        this.activation += n;
    }

    void resetActivation () {
        this.activation = 0;
    }

    public boolean isFiring () {
        return (this.activation >= this.threshold);
    }

    void setupThreshold (float threshold) {
        if (threshold < 0 || threshold > 1) {
            throw new InvalidThresholdValueException("The threshold must be between 0 and 1");
        }

        this.threshold = threshold;
    }

    public String getName () {
        return this.name;
    }
}