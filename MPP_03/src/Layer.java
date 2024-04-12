import java.util.Arrays;

public class Layer {
    Perceptron[] perceptrons;
    double a = 0.5;

    public Layer(Perceptron[] perceptrons) {
        this.perceptrons = perceptrons;
    }

    public Layer(int size) {
        perceptrons = new Perceptron[size];
    }

    public void train(TrainingText[] trainingTexts) {
        for (TrainingText trainingText : trainingTexts) {
            LetterProportionVector vector = new LetterProportionVector(trainingText);
            for (Perceptron perceptron : perceptrons) {
                int y = perceptron.compute(vector.getProportions()) >= 0 ? 1 : 0;
                int d = vector.getLang().equals(perceptron.label) ? 1 : 0;

                perceptron.learn(d, y, a, vector.getProportions());
            }
        }
    }
}
