package core;

import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

public class Brain {
    private List<Neuron> inputNeurons;
    private List<Neuron> outputNeurons;
    private List<Synapse> synapses;

    public Brain (List<Neuron> inputNeurons, List<Neuron> outputNeurons, List<Synapse> synapses) {
        this.inputNeurons = inputNeurons;
        this.outputNeurons = outputNeurons;
        this.synapses = synapses;
    }

    public void setupInputNeurons () {
        Scanner scanner = new Scanner(System.in);
        for (Neuron inputNeuron : inputNeurons) {
            String message = "Activate neuron " + inputNeuron.getName() + "?\n1 or 0: ";
            System.out.print(message);
            int isActive = scanner.nextInt();
            if (isActive == 1) {
                inputNeuron.activate();
            }
        }
    }

    public void simulateCycle () {
        for (Synapse synapse : synapses) {
            synapse.sendSignal();
        }

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

    static void main() {
        Neuron Food = new Neuron("Food");
        Neuron Bell = new Neuron("Bell");
        Neuron Salivation = new Neuron("Salivation");

        Synapse food_salivation = new Synapse(Food, Salivation, 1);
        Synapse bell_salivation = new Synapse(Food, Salivation, 0);
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

        brain.setupInputNeurons();
        brain.simulateCycle();
    }
}
