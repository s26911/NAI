import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var data = Util.readCsvFile("plecak_wyzarzanie.txt");
        for (var line : data){
            System.out.println(Arrays.toString(line));
        }

        double best = 0;
        KnapsackSA knapsack = new KnapsackSA(data);
        for (int i = 0; i < 100; i++) {
            int sol = knapsack.solve();
            int size = 0;
            double val = knapsack.calculateValue(sol);
            if (val > best)
                best = val;
            System.out.println(Util.NBytesAsString(sol, data.get(0)[1]) + "\t" + knapsack.overTheLimit(sol) +"\t" + knapsack.getSolutionSize(sol) + "\t" + val);
        }
        System.out.println("\n\nBest: " + best);

    }
}
