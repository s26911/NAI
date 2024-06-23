import java.util.ArrayList;

public class KnapsackSA {
    int capacity, numberOfItems, initTemp, tempDelta;
    int[] weights, values;

    public KnapsackSA(ArrayList<int[]> data) {
        int[] firstRow = data.get(0);
        capacity = firstRow[0];
        numberOfItems = firstRow[1];
        initTemp = firstRow[2];
        if (firstRow.length == 4)
            tempDelta = firstRow[3];
        weights = data.get(1);
        values = data.get(2);
    }

    public int solve(){
        // 1. start from random point (solution) X
        // 2. check end condition (end or continue)
        // 3. select random neighbour of X
        // 4. if neighbour:
            // a. has higher value: set as new X
            // b. has lower or equal value:
                // b1. calculate probability P [0,1]
                // b2. compare P with random number [0,1] and set neighbour as X or continue
        // 5. lower temp
        // 6. go to 2

        return 1;
    }
}
