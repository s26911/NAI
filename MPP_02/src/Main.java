import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) { // a, train-set, test-set
        // check and parse/read args
        double a;
        ArrayList<String[]> trainSet;
        ArrayList<String[]> testSet;

        try {
            a = Double.parseDouble(args[0]);
            if (a <= 0 || a >= 1)
                throw new NumberFormatException();
            trainSet = readCsvFile(args[1]);
            testSet = readCsvFile(args[2]);
            if (trainSet.get(0).length != testSet.get(0).length)
                throw new Exception("Train set and test set have different number of columns! Exiting...");
        } catch (FileNotFoundException e) {
            System.out.println("File was not found! Invalid pathname of at least one file");
            return;
        } catch (NumberFormatException e) {
            System.out.println("Invalid learning rate argument! Valid values are: 0 < a < 1");
            return;
        } catch (Exception e) {
            System.out.printf(e.getMessage());
            return;
        }

        Trainer trainer = new Trainer(trainSet, testSet);
    }

    public static ArrayList<String[]> readCsvFile(String path) throws FileNotFoundException {
        ArrayList<String[]> result = new ArrayList<>();
        int numberOfColumns = 0;

        try (Scanner s = new Scanner(new File(path))) {
            for (int i = 0; s.hasNextLine(); i++) {
                var row = s.nextLine().split(";");
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
