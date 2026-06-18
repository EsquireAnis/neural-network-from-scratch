package v1_biological;

import v1_biological.Exceptions.InvalidLearningRateValue;

import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

public class Brain {
    // Default value for the learning rate (how quickly the learning happens)
    private static final float DEFAULT_LEARNING_RATE = 0.1f;
    // Setting up the scanner for the user input in the future
    private static final Scanner scanner = new Scanner(System.in);

    // A brain consists of input neurons, output neurons, and synapses
    private float learningRate;
    private final List<Neuron> inputNeurons;
    private final List<Neuron> outputNeurons;
    private final List<Neuron> allNeurons = new ArrayList<>();
    private final List<Synapse> synapses;

    // Constructor when no learning rate is provided (set the default one)
    public Brain (List<Neuron> inputNeurons, List<Neuron> outputNeurons, List<Synapse> synapses) {
        this.learningRate = DEFAULT_LEARNING_RATE;
        this.inputNeurons = inputNeurons;
        this.outputNeurons = outputNeurons;
        this.synapses = synapses;

        this.allNeurons.addAll(inputNeurons);
        this.allNeurons.addAll(outputNeurons);
    }

    // Constructor when a learning rate is provided
    public Brain (List<Neuron> inputNeurons, List<Neuron> outputNeurons, List<Synapse> synapses, float learningRate) {
        this.inputNeurons = inputNeurons;
        this.outputNeurons = outputNeurons;
        this.synapses = synapses;

        if (learningRate <= 0) {
            throw new InvalidLearningRateValue("Learning rate value must be positive");
        }
        this.learningRate = learningRate;
    }

    // One full brain cycle simulation
    public void simulateCycle () {
        resetNeurons();
        setupInputNeurons();
        propagateSignals();
        learn();
        printOutput();
        printSynapses();
    }

    // Reset all neurons to be fully inactive
    private void resetNeurons () {
        for (Neuron neuron : allNeurons) {
            neuron.resetActivation();
        }
    }

    // Set the activation of input neurons based on the user input
    private void setupInputNeurons () {
            for (Neuron inputNeuron : inputNeurons) {
            String message = "Activate neuron " + inputNeuron.getName() + "?\n1 or 0: ";
            System.out.print(message);
            int isActive = scanner.nextInt();
            if (isActive == 1) {
                inputNeuron.activate();
            }
        }
    }

    // Propagate signals from input neurons to output neurons
    private void propagateSignals () {
        for (Synapse synapse : synapses) {
            synapse.sendSignal();
        }
    }

    // Strengthen the connection of neurons that fired together (increase synapse's weight)
    private void learn() {
        for (Synapse synapse : synapses) {
            if (synapse.isCorrelated()) {
                synapse.addWeight(learningRate);
            }
        }
    }

    // Print the state of each output neuron
    public void printOutput () {
        for (Neuron outputNeuron : outputNeurons) {
            String message = "The neuron " + outputNeuron.getName() + " is: ";
            if (outputNeuron.isFiring()) {
                message += "active";
            }
            else {
                message += "inactive";
            }
            System.out.println(message);
        }
    }

    // Print all synapses
    public void printSynapses () {
        for (Synapse synapse : synapses) {
            synapse.printSynapse();
        }
    }
}