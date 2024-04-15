import java.util.Arrays;

public class Main {
    private final static String trainingDataDir = "TrainingData";

    public static void main(String[] args) {
        TrainingText[] texts = TrainingText.fetchTrainingTexts(trainingDataDir);
        String[] langs = Arrays.stream(texts).map(TrainingText::getLang).distinct().toArray(String[]::new);

        LetterProportionVector vector = new LetterProportionVector(texts[0]);

        Layer layer = new Layer(langs);
        UI.start(layer, texts);
    }
}
