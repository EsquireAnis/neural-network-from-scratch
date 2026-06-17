package v1_biological;

import java.util.ArrayList;
import java.util.List;

public class PavlovExperiment {
    public void main () {
        Neuron Food = new Neuron("Food");
        Neuron Bell = new Neuron("Bell");
        Neuron Salivation = new Neuron("Salivation");

        Synapse food_salivation = new Synapse(Food, Salivation, 1);
        Synapse bell_salivation = new Synapse(Bell, Salivation, 0);

        List<Neuron> inputNeurons = new ArrayList<>();
        List<Neuron> outputNeurons = new ArrayList<>();
        List<Synapse> synapses = new ArrayList<>();

        inputNeurons.add(Food);
        inputNeurons.add(Bell);
        outputNeurons.add(Salivation);

        synapses.add(food_salivation);
        synapses.add(bell_salivation);

        Brain brain = new Brain(inputNeurons, outputNeurons, synapses);

        int CYCLES = 10;
        brain.printSynapses();
        for (int i = 0; i < CYCLES; i++) {
            brain.simulateCycle();
        }
    }
}