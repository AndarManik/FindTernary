import java.util.ArrayList;

/*
    BiasManager stores biases to be used in multi task settings.
    It's able to extract biases from the network
    set the bias in the network
    and update stored biases using the bias of network
 */
public class BiasManager {
    public NeuralNetwork nn;
    private ArrayList<double[]> biases;
    private int currentIndex;


    //construct a neural network along with a list of biases
    public BiasManager(int[] dim, double scale, int biasCount) {
        this.nn = new NeuralNetwork(dim, scale);
        biases = new ArrayList<>();
        double[] curBias = getBias();
        for(int i = 0; i < biasCount; i++)
            biases.add(curBias.clone());
    }

    //returns a copy of the current bias
    public double[] getBias()
    {
        int biasCount = 0;
        double[] bias;

        for (int i = 1; i < nn.network.size() - 1; i++)
            biasCount += nn.network.get(i).length;

        bias = new double[biasCount];

        int biasIndex = 0;
        for(int i = 1; i < nn.network.size() - 1; i++)
            for(NeuralNetwork.Node n : nn.network.get(i))
                bias[biasIndex++] = n.weight[n.weight.length - 1];

        return bias;
    }

    public void setBias(double[] bias)
    {
        int biasIndex = 0;
        for(int i = 1; i < nn.network.size() - 1; i++)
            for(NeuralNetwork.Node n : nn.network.get(i))
                n.weight[n.weight.length - 1] = bias[biasIndex++];
    }

    public void setBias(int biasIndex)
    {
        currentIndex = biasIndex;
        setBias(biases.get(biasIndex));
    }

    public void saveBias() { biases.set(currentIndex, getBias()); }
    public double[] calc(double[] input) {
        return nn.calc(input);
    }
    public double backProp(double[] input, double[] expected)
    {
        return nn.backProp(input,expected);
    }

    public void updateBias(double rate) {
        for(int i = 1; i < nn.network.size() - 1; i++)
            for(NeuralNetwork.Node n : nn.network.get(i))
                n.weight[n.weight.length -1] -= n.grad[n.grad.length - 1] * rate;
        clear();
    }

    public void update(double rate) {
        nn.update(rate);
    }

    public void clear()
    {
        nn.clear();
    }

    public double[] getBiasPOINTER(int index) {
        return biases.get(index);
    }

    public void randomizeBias(int index) {
        double[] curr = biases.get(index);
        for(int i = 0; i < curr.length; i++)
            curr[i] = Math.random() - 0.5;
    }
}
