import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String[]> trainSet;
        ArrayList<String[]> testSet;

        try {
            trainSet = Util.readCsvFile(args[0]);
            testSet = Util.readCsvFile(args[1]);
        } catch (FileNotFoundException e) {
            System.out.println("File was not found! Invalid pathname of at least one file");
            return;
        }

        UI.start(trainSet, testSet);
    }
}
