import java.util.ArrayList;
import java.util.Arrays;

public class KnapsackSA {
    int capacity, numberOfItems, initTemp, tempDelta, maxValue;
    int[] weights, values;

    // config
    int maxStagnationCount = 100, minTemp = 0, maxIterations = 1000;

    public KnapsackSA(ArrayList<int[]> data) {
        int[] firstRow = data.get(0);
        capacity = firstRow[0];
        numberOfItems = firstRow[1];
        initTemp = firstRow[2];
        if (firstRow.length == 4)
            tempDelta = firstRow[3];
        weights = data.get(1);
        values = data.get(2);
        maxValue = Arrays.stream(values).sum();
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

        // random starting solution
        int curr = (int) (Math.random() * Math.pow(2,numberOfItems));
        int next = 0;
        int iterCounter = 0;
        while(!stopCondition(iterCounter++)){
            next = randomNeighbour(curr);
            //TODO
        }

        return 1;
    }

    // returns true if algorithm should be stopped
    private boolean stopCondition(int iteration){
        return iteration > maxStagnationCount;
    }

    private int randomNeighbour(int current){
        int bitToChange = (int) (Math.random() * numberOfItems);
        if ((current & (1<<bitToChange)) != 0)      // if current has 1 on "bitToChange" index
            return current & ~(1<<bitToChange);     // then change it to 0
        else
            return current | (1<<bitToChange);      // else change it to 1
    }

    // funkcja celu
    private double calculateScore(int solution){
        // 0-0.5 over the limit     + lesser value has lesser score if both are over the limit
        // 0.5-1 less than the limit    + the solution compelling with the space limitation is always better than exceeding the limit

        //TODO
        return 1.;
    }

    private boolean overTheLimit(int solution){
        int size = 0;
        for (int i = 0; i < numberOfItems; i++) {
            if ((solution & (1 << i)) != 0)
                size += weights[i];
        }
        return size >= capacity;
    }
}
