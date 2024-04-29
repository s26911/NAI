import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // program arguments check, reading data
        if (args.length != 2) {
            System.out.println("Run a program with the following arguments: {pathToFile} {k}");
            return;
        }

        ArrayList<double[]> test;
        int k;
        try {
            test = Util.readCsvFile(args[0]);
            k = Integer.parseInt(args[1]);

            if (k < 0)
                throw new NumberFormatException();
            if (k > test.size()) {
                System.out.println("Value of k is greater than the size of test. Assuming maximum possible value");
                k = test.size();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File was not found! Provide a valid path to the file and try again");
            return;
        } catch (NumberFormatException e) {
            System.out.println("Invalid k value! It's value should be an integer >= 1");
            return;
        }

        // execution
        KMeans km = new KMeans(test, k);
        var result = km.start(5);
        KMeans.printGroups(result);
    }
}
