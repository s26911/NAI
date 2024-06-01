import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class HuffmanCoding {
    TreeMap<String, Integer> workMap;
    HashMap<Character, String> finalCoding;

    public HuffmanCoding(ArrayList<int[]> values) {
        workMap = new TreeMap<>();
        finalCoding = new HashMap<>();

        for (var elem : values){
            workMap.put(String.valueOf((char)elem[0]), elem[1]);
            finalCoding.put((char)elem[0], "");
        }
    }

//    public HashMap<Character, String> encode() {
//          1. Sort
//          2. check if 2 smallest have different values
//          3. merge (maintain correct order), save left and right value
//          4. add last code digits to left and right
//          5. check if workMap has more than 1 elem
//              - yes back to 1.
//              - no end
//    }
}
