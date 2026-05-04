package core;

public class Brain {
    private GenericList<Neuron> inputNeurons;
    private GenericList<Neuron> outputNeurons;
    private GenericList<Synapse> synapses;

    public Brain (GenericList<Neuron> inputNeurons, GenericList<Neuron> outputNeurons, GenericList<Synapse> synapses) {
        this.inputNeurons = inputNeurons;
        this.outputNeurons = outputNeurons;
        this.synapses = synapses;
    }

    public void setupInputNeurons () {

    }

}
