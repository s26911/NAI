import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<String[]> trainSet;
        ArrayList<String[]> testSet;

        try {
            trainSet = readCsvFile(args[0]);
            testSet = readCsvFile(args[1]);
        } catch (FileNotFoundException e) {
            System.out.println("File was not found! Invalid pathname of at least one file");
            return;
        }

        BayesClassificator classificator = new BayesClassificator(trainSet);
        String[] test1 = new String[]{"mały","umiarkowana","słabe","mała"};
        String[] test2 = new String[]{"miejski","niska","dobre","duża"};
        String[] test3 = new String[]{"kompakt","umiarkowana","dobre","duża"};
        String[] test4 = new String[]{"duży","umiarkowana","słabe","przeciętna"};

        System.out.println(classificator.classify(test1));
        System.out.println(classificator.classify(test2));
        System.out.println(classificator.classify(test3));
        System.out.println(classificator.classify(test4));
        System.out.println("\n\n");
        System.out.println(classificator.classifyOnTheGo(test1));
        System.out.println(classificator.classifyOnTheGo(test2));
        System.out.println(classificator.classifyOnTheGo(test3));
        System.out.println(classificator.classifyOnTheGo(test4));

        //UI.start();
    }

    public static ArrayList<String[]> readCsvFile(String path) throws FileNotFoundException {
        ArrayList<String[]> result = new ArrayList<>();
        int numberOfColumns = 0;

        try (Scanner s = new Scanner(new File(path))) {
            for (int i = 0; s.hasNextLine(); i++) {
                var row = s.nextLine().split(",");
                if (i == 0) {
                    numberOfColumns = row.length;            // set length of first row as numberOfColumns
                    result.add(row);
                } else if (row.length == numberOfColumns) {   // add only rows having the same size as the first one
                    result.add(row);
                }
            }
        } catch (FileNotFoundException e) {
            throw e;
        }

        return result;
    }
}
