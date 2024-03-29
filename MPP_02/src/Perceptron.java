
public class Perceptron {
    double[] weights;
    double bias;

    public double compute(double[] input) {  // compute net - bias  value
        double net = -bias;
        for (int i = 0; i < weights.length; i++) {
            net += weights[i] * input[i];
        }
        return net;
    }

    public void learn(int multiplier, double a, double[] inputVector) {
        double[] newWeights = new double[weights.length];
        for (int i = 0; i < weights.length; i++) {
            newWeights[i] = weights[i] + (multiplier * a * inputVector[i]);
        }
        weights = newWeights;
        bias = bias - (multiplier * a);
    }
    public boolean ifCorrect(MyVector input, String activationLabel){
        double net = compute(input.getAttributes());
        if(net >= 0 && input.getLabel().equals(activationLabel))
            return true;
        if(net < 0 && !input.getLabel().equals(activationLabel))
            return true;
        return false;
    }

    public Perceptron(int numberOfWeights) {
        weights = new double[numberOfWeights];
        bias = Math.random() + 0.1;
    }
}
