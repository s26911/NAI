import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var res = Util.readCsvFile(args[0]);
        for (var elem : res) {
            System.out.println(Arrays.toString(elem));
        }

        if (res.get(0)[1] > 32) {
            System.out.println("n should have value 32 or less");
            return;
        }

        BruteForce bruteForce = new BruteForce(res);
        bruteForce.exec();
    }
}
