import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var x = Util.readFile("huffman.txt");
        for (var elem : x){
            System.out.println(Arrays.toString(elem));
        }
    }
}
