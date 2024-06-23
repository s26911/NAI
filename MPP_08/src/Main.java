import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var data = Util.readCsvFile("plecak_wyzarzanie.txt");
        for (var line : data){
            System.out.println(Arrays.toString(line));
        }

        KnapsackSA knapsack = new KnapsackSA(data);
        knapsack.solve();
    }
}
