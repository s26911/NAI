import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileOperations {
    public static ArrayList<String[]> readCsvFile(String path) throws FileNotFoundException {
        ArrayList<String[]> result = new ArrayList<>();

        try (Scanner s = new Scanner(new File(path))) {
            while (s.hasNextLine())
                result.add(s.nextLine().split(";"));

        } catch (FileNotFoundException e) {
            throw e;
        }

        return result;
    }

    public static boolean validateData(ArrayList<String[]> data) {
        int size = data.get(0).length;
        for (String[] row : data) {
            if (row.length != size)
                return false;
        }

        return true;
    }
}
