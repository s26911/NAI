import javax.swing.*;
import java.util.Arrays;
import java.util.Scanner;

public class UI {
    static boolean MORE_INFO = false;
    static Scanner scanner = new Scanner(System.in);

    public static void start(Trainer trainer) {
        System.out.println("Training...");
        trainer.train();
        System.out.println("Trained perceptron: \n\tWeights: " + Arrays.toString(trainer.perceptron.weights) +
                "\n\tBias: " + trainer.perceptron.bias);
        while (true) {
            int option = pickOption(new String[]{"Test accuracy", "Input vectors", "Train once again", "Set new perceptron", "Change a", "Toggle \"more info\"", "Quit"});
            switch (option) {
                case 1 -> {
                    double[] res = trainer.test(MORE_INFO);
                    System.out.println("Overall accuracy: " + res[0] + "%");
                    System.out.println("Class 1 (" + trainer.activationClassLabel + ") accuracy: " + res[1] + "%");
                    System.out.println("Class 2 accuracy: " + res[2] + "%");
                }
                case 2 -> manualInput(trainer.perceptron.weights.length, trainer.perceptron);
                case 3 -> {
                    System.out.println("Training...");
                    trainer.train();
                    System.out.println("Trained perceptron: \n\tWeights: " + Arrays.toString(trainer.perceptron.weights) +
                            "\n\tBias: " + trainer.perceptron.bias);
                }
                case 4 -> {
                    trainer.perceptron = new Perceptron(trainer.perceptron.weights.length);
                    System.out.println("New perceptron: \n\tWeights: " + Arrays.toString(trainer.perceptron.weights) +
                            "\n\tBias: " + trainer.perceptron.bias);
                }
                case 5 -> changeA(trainer);
                case 6 -> MORE_INFO = !MORE_INFO;
                case 7 -> {
                    scanner.close();
                    return;
                }
            }
        }
    }

    private static void changeA(Trainer trainer) {
        System.out.println("Current a value: " + trainer.a);
        System.out.print("Please provide new value between 0 and 1 (exclusive): ");
        scanner.nextLine();
        while (true) {
            String input = scanner.nextLine();
            try {
                trainer.a = Double.parseDouble(input);
                return;
            } catch (NumberFormatException e) {
                System.out.println("Wrong format! Try again...");
            }
        }
    }

    private static void manualInput(int vecDim, Perceptron perceptron) {
        System.out.println("Vector should have dimension of " + vecDim);
        System.out.println("Please insert values separated with whitespaces or q to quit inserting vectors manually:");
        scanner.nextLine();
        while (true) {
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("q"))
                return;

            String[] data = input.split("\\s+");
            try {
                if (data.length != vecDim)
                    throw new NumberFormatException();
                MyVector vec = new MyVector(data, null);
                double net = perceptron.compute(vec.getAttributes());
                System.out.println("Net(-bias): " + net + (net >= 0 ? "\t\tFIRED" : "\t\tDIDN'T FIRE"));
            } catch (NumberFormatException e) {
                System.out.println("Wrong format! Try again...");
            }
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
