package core;

import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

public class Brain {
    private static final float DEFAULT_LEARNING_RATE = 0.1f;
    private static final Scanner scanner = new Scanner(System.in);

    private float learningRate;
    private final List<Neuron> inputNeurons;
    private final List<Neuron> outputNeurons;
    private final List<Neuron> allNeurons = new ArrayList<>();
    private final List<Synapse> synapses;

    public Brain (List<Neuron> inputNeurons, List<Neuron> outputNeurons, List<Synapse> synapses) {
        this.learningRate = DEFAULT_LEARNING_RATE;
        this.inputNeurons = inputNeurons;
        this.outputNeurons = outputNeurons;
        this.synapses = synapses;

        this.allNeurons.addAll(inputNeurons);
        this.allNeurons.addAll(outputNeurons);
    }

    public Brain (List<Neuron> inputNeurons, List<Neuron> outputNeurons, List<Synapse> synapses, float learningRate) {
        this.inputNeurons = inputNeurons;
        this.outputNeurons = outputNeurons;
        this.synapses = synapses;

        if (learningRate <= 0) {
            throw new InvalidLearningRateValue("Learning rate value must be positive");
        }
        this.learningRate = learningRate;
    }

    public void simulateCycle () {
        resetNeurons();
        setupInputNeurons();
        propagateSignals();
        learn();
        printOutput();
        printSynapses();
    }

    private void resetNeurons () {
        for (Neuron neuron : allNeurons) {
            neuron.resetActivation();
        }
    }

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

    private void propagateSignals () {
        for (Synapse synapse : synapses) {
            synapse.sendSignal();
        }
    }

    private void learn() {
        for (Synapse synapse : synapses) {
            if (synapse.isCorrelated()) {
                synapse.addWeight(learningRate);
            }
        }
    }

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

    public void printSynapses () {
        for (Synapse synapse : synapses) {
            synapse.printSynapse();
        }
    }

    static void main() {
        Neuron Food = new Neuron("Food");
        Neuron Bell = new Neuron("Bell");
        Neuron Salivation = new Neuron("Salivation");

        Synapse food_salivation = new Synapse(Food, Salivation, 1);
        Synapse bell_salivation = new Synapse(Bell, Salivation, 0);
        Synapse food_bell = new Synapse(Food, Bell, 0);

        List<Neuron> inputNeurons = new ArrayList<>();
        List<Neuron> outputNeurons = new ArrayList<>();

        List<Synapse> synapses = new ArrayList<>();

        inputNeurons.add(Food);
        inputNeurons.add(Bell);
        outputNeurons.add(Salivation);

        synapses.add(food_salivation);
        synapses.add(bell_salivation);
        synapses.add(food_bell);

        Brain brain = new Brain(inputNeurons, outputNeurons, synapses);

        brain.printSynapses();

        for (int i = 0; i < 10; i++) {
            brain.simulateCycle();
        }
    }
}