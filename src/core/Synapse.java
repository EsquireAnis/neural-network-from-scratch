package core;

public class Synapse {
    private Neuron from;
    private Neuron to;
    private float weight;

    public Synapse (Neuron from, Neuron to, float weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public void sendSignal () {
        if (from.isFiring()) {
            to.addActivation(weight);
        }
    }

    public void alterWeight (float n) {
        this.weight += n;
    }
}
