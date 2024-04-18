import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class UI {
    static Scanner scanner = new Scanner(System.in);

    public static void start(ArrayList<String[]> trainSet, ArrayList<String[]> testSet) {
        BayesClassificator bayesClassificator = new BayesClassificator(trainSet);

        while (true) {
            int option = pickOption(new String[]{"Input attributes", "Quit"});
            switch (option) {
                case 1 -> manualInput(bayesClassificator);
                case 2 -> {
                    scanner.close();
                    return;
                }
            }
        }
    }

    private static void manualInput(BayesClassificator bayes) {

        System.out.println("Please enter " + (bayes.columnCount - 1) + "attributes separated with , or q to quit: ");
        scanner.nextLine();
        while (true) {
            String input = scanner.nextLine().toLowerCase().replaceAll("[^ęóąśłżźćńĘÓĄŚŁŻŹĆŃ\\w\\d\\s,;]", "");
            if (input.equals("q"))
                return;

            String[] data = input.split("[\\s,;]+");
            if (data.length != bayes.columnCount - 1)
                System.out.println("Wrong format! Try again: ");
            else
                System.out.println("Result: " + bayes.classifyOnTheGo(data));
        }
    }

    private static int pickOption(String[] descriptions) {
        System.out.println("\nPlease pick an option:");
        for (int i = 0; i < descriptions.length; i++) {
            System.out.println(i + 1 + ". " + descriptions[i]);
        }
        while (true) {
            try {
                int op = Integer.parseInt(scanner.next());
                if (op >= 1 && op <= descriptions.length) {
                    return op;
                }
            } catch (NumberFormatException ignored) {

            }
        }
    }
}
