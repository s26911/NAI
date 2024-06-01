import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Util {

    public static ArrayList<int[]> readFile(String path) throws FileNotFoundException {
        ArrayList<int[]> result = new ArrayList<>();

        try (Scanner s = new Scanner(new File(path))) {
            int numberOfRows = Integer.parseInt(s.nextLine());

            for (int i = 0; i < numberOfRows; i++) {
                var read = s.nextLine().split("\\s+");
                int[] row = new int[2];
                row[0] = read[0].charAt(0);
                row[1] = Integer.parseInt(read[1]);
                result.add(row);
            }
        }

        return result;
    }
}
