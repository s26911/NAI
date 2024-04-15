import java.util.*;

public class Layer {
    Perceptron[] perceptrons;
    double a = 0.5;

    public Layer(String[] labels) {
        perceptrons = new Perceptron[labels.length];
        for (int i = 0; i < perceptrons.length; i++) {
            perceptrons[i] = new Perceptron(26, labels[i]);
        }
    }

    // train the layer (using provided texts) until no progress is made or accuracy worsens
    public double train(TrainingText[] trainingTexts) {
        ArrayList<TrainingText> data = new ArrayList<>(Arrays.asList(trainingTexts));
        Collections.shuffle(data);

        int counter = 0;
        double previousAccuracy = 0.0;
        int accuracyStagnationCounter = 0;
        while (true) {
            int correct = 0;
            for (TrainingText trainingText : data) {
                LetterProportionVector vector = new LetterProportionVector(trainingText);
                for (Perceptron perceptron : perceptrons) {
                    int y = perceptron.compute(vector.getProportions()) >= 0 ? 1 : 0;
                    int d = vector.getLang().equals(perceptron.label) ? 1 : 0;
                    if (y == d) correct++;

                    perceptron.learn(d, y, a, vector.getProportions());
                }
            }

            counter++;
            double currentAccuracy = correct * 100. / (data.size() * perceptrons.length);
            if ((currentAccuracy < previousAccuracy && previousAccuracy > 0.85) || accuracyStagnationCounter++ > 5) {
                System.out.println("Counter:\t" + counter);
                return currentAccuracy;
            }
            previousAccuracy = currentAccuracy;
        }
    }

    // classifies text into one of the available languages
    public String classify(TrainingText trainingText) {
        double[] rawOutput = compute(trainingText);
        int[] normalizedOutput = maximumSelector(rawOutput);

        for (int i = 0; i < perceptrons.length; i++) {
            if (normalizedOutput[i] == 1)
                return perceptrons[i].label;
        }

        return "FAIL";
    }

    // computes output vector
    public double[] compute(TrainingText trainingText) {
        double[] output = new double[perceptrons.length];
        LetterProportionVector vector = new LetterProportionVector(trainingText);

        for (int i = 0; i < perceptrons.length; i++) {
            output[i] = perceptrons[i].compute(vector.getProportions());
        }

        return output;
    }

    // selects maximum value from vector, returns new vector filled with 0 and single 1
    public int[] maximumSelector(double[] input) {
        int[] output = new int[input.length];
        double max = Arrays.stream(input).max().orElseThrow();

        for (int i = 0; i < input.length; i++) {
            if (input[i] == max) {
                output[i] = 1;
                break;
            }
        }

        return output;
    }

    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < perceptrons.length; i++) {
            out += perceptrons[i].label + "\n\tWeights: " +
                    Arrays.toString(perceptrons[i].weights) + "\n\tBias: " + perceptrons[i].bias + "\n";
        }
        return out;
    }
}
