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
}
