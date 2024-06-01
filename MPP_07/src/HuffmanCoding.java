import java.util.ArrayList;

public class HuffmanCoding {
    MyPrioQueue queue;
    CodingTable codingTable;

    public HuffmanCoding(ArrayList<int[]> values) {
        queue = new MyPrioQueue();
        codingTable = new CodingTable(values);

        for (var elem : values)
            queue.enqueue(elem[1], String.valueOf((char) elem[0]));
    }

    public CodingTable encode() {
        // single element edge case
        if (queue.count() == 1) {
            codingTable.prepend(queue.dequeue().data.charAt(0), "0");
            return codingTable;
        }

        while (queue.count() > 1) {
            String[] leftRight = mergeAndEnqueue(queue.dequeue(), queue.dequeue());
            for (char c : leftRight[0].toCharArray())
                codingTable.prepend(c, "0");
            for (char c : leftRight[1].toCharArray())
                codingTable.prepend(c, "1");
        }

        return codingTable;
    }

    // merges two nodes into one and enqueues the result to the queue
    // returns: [0] data of the node on the left side, [1] data of the node on the right side
    public String[] mergeAndEnqueue(MyPrioQueue.Node n1, MyPrioQueue.Node n2) {
        String[] result = new String[2];
        if (n1.priority != n2.priority) {
            result[0] = n1.data;
            result[1] = n2.data;
        } else {
            int i = Character.compare(n1.data.charAt(0), n2.data.charAt(0));
            if (i < 0) {
                result[0] = n1.data;
                result[1] = n2.data;
            } else {
                result[0] = n2.data;
                result[1] = n1.data;
            }
        }

        queue.enqueue(n1.priority + n2.priority, result[0] + result[1]);
        return result;
    }
}
