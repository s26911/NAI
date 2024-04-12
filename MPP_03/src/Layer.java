import java.util.*;

public class Layer {
    Perceptron[] perceptrons;
    double a = 0.5;

    public Layer(Perceptron[] perceptrons) {
        this.perceptrons = perceptrons;
    }

    public Layer(int size, String[] labels) {
        perceptrons = new Perceptron[size];
        for (int i = 0; i < perceptrons.length; i++) {
            perceptrons[i] = new Perceptron(26, labels[i]);
        }
    }

    public void train(TrainingText[] trainingTexts) {
        ArrayList<TrainingText> data = new ArrayList<>(Arrays.asList(trainingTexts));
        Collections.shuffle(data);
        for (TrainingText trainingText : data) {
            LetterProportionVector vector = new LetterProportionVector(trainingText);
            for (Perceptron perceptron : perceptrons) {
                int y = perceptron.compute(vector.getProportions()) >= 0 ? 1 : 0;
                int d = vector.getLang().equals(perceptron.label) ? 1 : 0;

                perceptron.learn(d, y, a, vector.getProportions());
            }
        }
    }

    public String classify(TrainingText trainingText){
        double[] rawOutput = compute(trainingText);
        double[] normalizedOutput = maximumSelector(rawOutput);

        for (int i = 0; i < perceptrons.length; i++) {
            if(normalizedOutput[i] == 0)
                return perceptrons[i].label;
        }

        return "FAIL";
    }

    public double[] compute(TrainingText trainingText){
        double[] output = new double[perceptrons.length];
        LetterProportionVector vector = new LetterProportionVector(trainingText);

        for (int i = 0; i < perceptrons.length; i++) {
            output[i] = perceptrons[i].compute(vector.getProportions());
        }

        return output;
    }

    public double[] maximumSelector(double[] input){
        double[] output = new double[input.length];
        double max = Arrays.stream(input).max().orElse(0);

        for (int i = 0; i < input.length; i++) {
            if(input[i] == max){
                output[i] = 1;
                break;
            }
        }

        return output;
    }


}
