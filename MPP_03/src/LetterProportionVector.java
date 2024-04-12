import java.util.Arrays;

public class LetterProportionVector {
    double[] proportions;        // english alphabet ASCII [a-zA-Z]
    String lang;               // actual language of the input text

    public LetterProportionVector(TrainingText trainingText) {
        this.lang = trainingText.getLang();
        proportions = new double[26];

        // count
        for (char c : trainingText.getText().toCharArray()) {
            proportions["abcdefghijklmnopqrstuvwxyz".indexOf(c)]++;
        }
        double max = Arrays.stream(proportions).max().orElseThrow();
        proportions = Arrays.stream(proportions).map(x -> x/=max).toArray();
    }

    public String getLang() {
        return lang;
    }

    public double[] getProportions() {
        return proportions;
    }

    @Override
    public String toString() {
        return Arrays.toString(proportions) + " " + lang;
    }
}
