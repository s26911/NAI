import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var x = Util.readFile("huffman.txt");

        HuffmanCoding coding = new HuffmanCoding(x);
        for (var elem : coding.encode().entrySet()){
            System.out.print(elem.getKey() + "\t -> \t " + elem.getValue() + "\n");
        }
    }
}
