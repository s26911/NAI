import java.io.*;
import java.util.*;

public class Main {
    static boolean DEBUG = false;
    static boolean SILENT = false;

    public static void main(String[] args) {    // args: k, train-set path, test-set path
        // validate input
        int k = 0;
        String trainPath, testPath;

        try {
            k = Integer.parseInt(args[0]);
            if (k <= 0) throw new Exception();
            trainPath = args[1];
            testPath = args[2];
        } catch (Exception e) {
            System.out.println("Program executed with illegal arguments!\n" +
                    "Args should be: k (> 0), train-set path, test-set path\n");
            return;
        }

        // read from csv files
        ArrayList<String[]> trainSet;
        ArrayList<String[]> testSet;
        try {
            trainSet = FileOperations.readCsvFile(trainPath);
            testSet = FileOperations.readCsvFile(testPath);
        } catch (FileNotFoundException e) {
            System.out.println("File was not found! Invalid pathname of at least one file");
            return;
        }

        // setting DEBUG state
        System.out.print("\nPrint additional info? (Y/n): "); // y or anything else for no
        Scanner scanner = new Scanner(System.in);
        if (scanner.nextLine().equalsIgnoreCase("y"))
            DEBUG = true;

        // perform check
        DataSet dataSet = new DataSet(trainSet);
        long start = System.currentTimeMillis();
        dataSet.checkTestSet(new DataSet(testSet), k);
        long stop = System.currentTimeMillis();
        System.out.println("Time: " + (stop - start));

        // manual input loop
        System.out.print("\nWhat do you want to do? Enter a number\n1. Enter vectors manually" +
                "\n2. Generate .csv file on how accuracy changes depending on k value" +
                "\n3. Quit\n");
        boolean reading = true;
        while (reading) {
            switch (scanner.nextLine().toLowerCase()) {
                case "1" -> {
                    manualInput(dataSet, k);
                    reading = false;
                }
                case "2" -> {
                    generateCsv(dataSet, new DataSet(testSet));
                    reading = false;
                }
                case "3" -> reading = false;
            }
        }
        scanner.close();
    }

    // interacts with a user. User can input a vector, get help or quit at any time
    public static void manualInput(DataSet dataSet, int k) {
        System.out.println("Vector should have dimension of " + (dataSet.columnNumber - 1)
                + "\nPlease enter values separated by whitespaces, help for an example " +
                "or q to quit");

        Scanner scanner = new Scanner(System.in);
        boolean reading = true;
        String input = scanner.nextLine().toLowerCase();
        while (reading) {
            switch (input) {
                case "help" -> {
                    System.out.println("Example line for dimension = 3:\n" +
                            "3.2 0.1 20.132");
                    input = scanner.nextLine().toLowerCase();
                }
                case "q" -> reading = false;
                default -> {
                    // validating input from user and showing result of a classification
                    String[] data = input.split("\\s+");
                    try {
                        if (data.length != dataSet.columnNumber - 1)
                            throw new NumberFormatException();
                        MyVector vec = new MyVector(data, false);
                        System.out.println(dataSet.calculateAnswer(vec, k));
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong format! Try again...");
                    }
                    input = scanner.nextLine().toLowerCase();
                }


            }
        }
        scanner.close();
    }

    public static void generateCsv(DataSet dataSet, DataSet testSet) {
        DEBUG = false;
        SILENT = true;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("plotData.csv", false))) {
            writer.write("k;Accuracy\n");
            for (int k = 1; k < dataSet.data.size() + 1; k++) {
                writer.write(k + ";" + dataSet.checkTestSet(testSet, k) + "\n");
            }
        } catch (IOException e) {
            System.out.println("There was a problem creating the file!!!");
        }
    }
}

