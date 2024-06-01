import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var x = Util.readFile("huffmanLab.txt");

        HuffmanCoding coding = new HuffmanCoding(x);
        coding.encode().print();
    }
}
