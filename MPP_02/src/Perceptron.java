public class Perceptron {
    double[] weights;
    double bias;

    public double compute(double[] input) {  // compute net value
        double net = -bias;
        for (int i = 0; i < weights.length; i++) {
            net += weights[i] * input[i];
        }
        return net;
    }

    public void learn() {
    }

    public Perceptron(int numberOfWeights) {
        weights = new double[numberOfWeights];
        bias = Math.random() + 0.1;
    }
}
