import java.util.Arrays;
import java.util.Scanner;

public class UI {
    static boolean MORE_INFO = false;
    static Scanner scanner = new Scanner(System.in);

    public static void start(Layer layer, TrainingText[] texts) {
        System.out.println("Training...");
        String trainingResut = layer.train(texts);

        while (true) {
            int option = pickOption(new String[]{"Print train accuracy", "Input text", "Train once again", "Set new perceptron", "Change a", "Toggle \"more info\"", "Quit"});
            switch (option) {
                case 1 -> System.out.println(trainingResut);
                case 2 -> manualInput(layer);
//                case 3 -> {
//                    System.out.println("Training...");
//                    layer.train();
//                    System.out.println("Trained perceptron: \n\tWeights: " + Arrays.toString(layer.perceptron.weights) +
//                            "\n\tBias: " + layer.perceptron.bias);
//                }
//                case 4 -> {
//                    layer.perceptron = new Perceptron(layer.perceptron.weights.length);
//                    System.out.println("New perceptron: \n\tWeights: " + Arrays.toString(layer.perceptron.weights) +
//                            "\n\tBias: " + layer.perceptron.bias);
//                }
//                case 5 -> changeA(layer);
//                case 6 -> MORE_INFO = !MORE_INFO;
//                case 7 -> {
//                    scanner.close();
//                    return;
//                }
            }
        }
    }

    //    private static void changeA(Trainer trainer) {
//        System.out.println("Current a value: " + trainer.a);
//        System.out.print("Please provide new value between 0 and 1 (exclusive): ");
//        scanner.nextLine();
//        while (true) {
//            String input = scanner.nextLine();
//            try {
//                trainer.a = Double.parseDouble(input);
//                return;
//            } catch (NumberFormatException e) {
//                System.out.println("Wrong format! Try again...");
//            }
//        }
//    }
//
    private static void manualInput(Layer layer) {
        System.out.println("Please insert text, confirm with single w, write q to quit: ");
        scanner.nextLine();
        String input = "";

        while (true) {
            String line = scanner.nextLine();
            if (line.equals("q"))
                return;
            if (line.equals("w")){
                System.out.println(layer.classify(new TrainingText(input, null)));
                input = "";
                continue;
            }
            input += line;
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
