import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Function;

public class UI {
    static Scanner scanner = new Scanner(System.in);
    private static boolean DEBUG = false;

    public static void start(ArrayList<String[]> trainSet, ArrayList<String[]> testSet) {
        BayesClassificator bayesClassificator = new BayesClassificator(trainSet);
        BiFunction<String[], Boolean, String> method = bayesClassificator::classify;

        while (true) {
            int option = pickOption(new String[]{"Input attributes", "Test classificator", "Toggle DEBUG", "Toggle method", "Quit"});
            switch (option) {
                case 1 -> manualInput(bayesClassificator, method);
                case 2 -> bayesClassificator.test(testSet, method, DEBUG);
                case 3 -> DEBUG = !DEBUG;
                case 4 -> method = pickOption(new String[]{"Precalculated", "On the fly"}) == 0 ?
                        bayesClassificator::classify : bayesClassificator::classifyOnTheFly;
                case 5 -> {
                    scanner.close();
                    return;
                }
            }
        }
    }

    private static void manualInput(BayesClassificator bayes, BiFunction<String[], Boolean, String> method) {

        System.out.println("Please enter " + (bayes.columnCount - 1) + " attributes separated with , or q to quit: ");
        scanner.nextLine();
        while (true) {
            String input = scanner.nextLine().toLowerCase().replaceAll("[^ęóąśłżźćńĘÓĄŚŁŻŹĆŃ\\w\\d\\s,;]", "");
            if (input.equals("q"))
                return;

            String[] data = input.split("[\\s,;]+");
            if (data.length != bayes.columnCount - 1)
                System.out.println("Wrong format! Try again: ");
            else
                System.out.println("Result: " + method.apply(data, DEBUG));
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
