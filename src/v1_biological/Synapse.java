package v1_biological;

public class Synapse {
    // A synapse consists of two neurons and the connection strength (weight) between them
    // A synapse is directional (from one neuron to another)
    private final Neuron from;
    private final Neuron to;
    private float weight;

    // Constructor
    public Synapse (Neuron from, Neuron to, float weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    // If the incoming neuron fires, then we add the synapse weight to the outgoing neuron's activation
    void sendSignal () {
        if (from.isFiring()) {
            to.addActivation(weight);
        }
    }

    // Determine whether both neurons fire simultaneously
    public boolean isCorrelated () {
        return (from.isFiring() && to.isFiring()) ;
    }

    // Change the connection strength (weight of synapse)
    void addWeight(float n) {
        this.weight += n;
    }

    // Print the synapse's details
    public void printSynapse () {
        String message = "";
        message += ("Weight from " + from.getName() + " to " + to.getName() + ": " + Float.toString(this.weight));
        System.out.println(message);
    }
}