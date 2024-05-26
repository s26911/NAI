import java.util.ArrayList;

public class BruteForce {
    int capacity, numberOfItems;
    int[] weights, values;

    public BruteForce(ArrayList<int[]> data) {
        capacity = data.get(0)[0];
        numberOfItems = data.get(0)[1];
        weights = data.get(1);
        values = data.get(2);
    }

    public int exec() {
        int counter = 0, maxCounter = (int) Math.pow(2, numberOfItems) - 1;
        int bestSolution = 0, bestWeight= 0, bestValue = 0;
        while (counter++ < maxCounter) {
            int mask = 1, weight = 0, value = 0;
            for (int i = 0; i < numberOfItems; i++, mask <<= 1) {
                if ((counter & mask) != 0) {
                    weight += weights[i];
                    value += values[i];
                }
            }

            if(weight <= capacity && value > bestValue){
                bestSolution = counter - 1;
                bestWeight = weight;
                bestValue = value;
                System.out.println("Iter: " + counter + " solution: " + Util.NBytesAsString(bestSolution, numberOfItems));
                System.out.println("Weight: " + bestWeight + " Value: " + bestValue + "\n");
            }
        }

        System.out.println("\nFinal solution: " + Util.NBytesAsString(bestSolution, numberOfItems));
        System.out.println("Weight: " + bestWeight + " Value: " + bestValue);

        return bestSolution;
    }
}
