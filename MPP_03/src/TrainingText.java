import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class TrainingText {
    private final String text;
    private final String lang;

    public TrainingText(String text, String lang) {
        this.text = filter(text);
        this.lang = lang;
    }

    private String filter(String text) {
        return text.replaceAll("[^a-zA-Z]", "").toLowerCase();
    }

    public static TrainingText[] fetchTrainingTexts(String trainingDataDir){
        ArrayList<TrainingText> trainingTexts = new ArrayList<>();

        try {
            Files.walkFileTree(Path.of(trainingDataDir), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.getFileName().toString().endsWith(".txt")) {
                        String text = Files.readString(file);
                        trainingTexts.add(new TrainingText(text, file.getParent().toString()));
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return trainingTexts.toArray(new TrainingText[0]);
    }

    public String getText() {
        return text;
    }

    public String getLang() {
        return lang;
    }

    @Override
    public String toString() {
        return "LANG: " + lang + "\nTEXT: " + text.substring(0,50);
    }
}
