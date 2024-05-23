import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var res = Util.readCsvFile(args[0]);
        for (var elem : res){
            System.out.println(Arrays.toString(elem));
        }
    }
}
