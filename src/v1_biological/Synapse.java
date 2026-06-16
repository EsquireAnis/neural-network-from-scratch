package v1_biological;

public class Synapse {
    private final Neuron from;
    private final Neuron to;
    private float weight;

    public Synapse (Neuron from, Neuron to, float weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    void sendSignal () {
        if (from.isFiring()) {
            to.addActivation(weight);
        }
    }

    public boolean isCorrelated () {
        return (from.isFiring() && to.isFiring()) ;
    }

    void addWeight(float n) {
        this.weight += n;
    }

    public void printSynapse () {
        String message = "";
        message += ("Weight from " + from.getName() + " to " + to.getName() + ": " + Float.toString(this.weight));
        System.out.println(message);
    }
}