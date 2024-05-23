import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Util {
    public static ArrayList<int[]> readCsvFile(String path) throws FileNotFoundException {
        ArrayList<int[]> result = new ArrayList<>();

        try (Scanner s = new Scanner(new File(path))) {
            var first = Arrays.stream(s.nextLine().split(" ")).mapToInt(Integer::valueOf).toArray();
            result.add(first);
            for (int i = 0; i < 2; i++) {
                var row = s.nextLine().split(",");
                if (row.length != first[1])
                    throw new IllegalArgumentException("Wrong number of columns in data file");
                result.add(Arrays.stream(row).mapToInt(Integer::valueOf).toArray());
            }
        }

        return result;
    }
}
