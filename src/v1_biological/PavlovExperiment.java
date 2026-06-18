package v1_biological;

import java.util.ArrayList;
import java.util.List;

public class PavlovExperiment {
    public void main () {
        // We set up the input neurons for the "dog": food and bell
        // The single output neuron is salivation: whether dog anticipates food or not
        // We simulate the following scenarios:
        // 1. Give food and ring the bell (build association)
        // 2. Ring the bell and don't give the food (if the association is built, the dog should salivate)

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

        // Set up the number of repetitions for the scenario 1
        int CYCLES = 10;
        brain.printSynapses();
        for (int i = 0; i < CYCLES; i++) {
            brain.simulateCycle();
        }
        System.out.println("\nAfter conditioning, the weights are:");
        brain.printSynapses();

        // Now, check the scenario 2
        brain.simulateCycle();
    }
}