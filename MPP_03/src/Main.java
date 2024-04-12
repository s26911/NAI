import java.util.Arrays;

public class Main {
    private final static String trainingDataDir = "TrainingData";

    public static void main(String[] args) {
        TrainingText[] texts = TrainingText.fetchTrainingTexts(trainingDataDir);
        int langCount = (int) Arrays.stream(texts).map(TrainingText::getLang).distinct().count();
        String[] langs = Arrays.stream(texts).map(TrainingText::getLang).distinct().toArray(String[]::new);

        Layer layer = new Layer(langCount, langs);
        layer.train(texts);
        System.out.println(texts[20]);
        System.out.println(layer.classify(texts[1]));
    }
}
