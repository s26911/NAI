import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class UI {
    static Scanner scanner = new Scanner(System.in);

    public static void start(ArrayList<String[]> trainSet, ArrayList<String[]> testSet) {
        BayesClassificator bayesClassificator = new BayesClassificator(trainSet);

    }

    private static void manualInput(BayesClassificator bayes) {

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
