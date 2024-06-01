import java.util.ArrayList;
import java.util.Arrays;

public class CodingTable {
    Element[] table;

    public CodingTable(ArrayList<int[]> list) {
        table = new Element[list.size()];
        for (int i = 0; i < list.size(); i++) {
            table[i] = new Element((char) list.get(i)[0]);
        }
        Arrays.sort(table);
    }

    public void prepend(char toChar, String value) {
        for (Element element : table) {
            if (element.character > toChar)
                return;
            if (element.character == toChar) {
                element.coding = value + element.coding;
                return;
            }
        }
    }

    public void print() {
        for (Element element : table) {
            System.out.print(element.character + "\t -> \t " + element.coding + "\n");
        }
    }

    private static class Element implements Comparable<Element> {
        char character;
        String coding;

        public Element(char character) {
            this.character = character;
            this.coding = "";
        }

        @Override
        public int compareTo(Element o) {
            return this.character - o.character;
        }
    }
}
