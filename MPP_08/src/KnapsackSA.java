import java.util.ArrayList;
import java.util.Arrays;

public class KnapsackSA {
    int capacity, numberOfItems, initTemp, tempDelta, maxValue;
    int[] weights, values;

    // config
    int maxStagnationCount = 10000, minTemp = 0, maxIterations = 10000;
    double limitExceededMultiplier = 4, probabilityMultiplier = 0.2;

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

    public int solve() {
        int curr = (int) (Math.random() * Math.pow(2, numberOfItems));   // random starting solution
        int next = 0, iterCounter = 0, stagnationCounter = 0;
        double temp = initTemp;

        while (!stopCondition(iterCounter++, stagnationCounter)) {
            boolean stagnated = true;
            next = randomNeighbour(curr);
            boolean o1 = overTheLimit(curr);
            boolean o2 = overTheLimit(next);
            double v1 = calculateValue(curr);
            double v2 = calculateValue(next);

            if (compareSolutions(curr, next) < 0) {
                curr = next;
                stagnated = false;
            } else {
                double P = calculateProbability(curr, next, temp);
                double r = Math.random();
                if (r < P) {
                    curr = next;
                    stagnated = false;
                }
            }

            temp = decreaseTemp(temp);
            if (stagnated)
                stagnationCounter++;
            else
                stagnationCounter = 0;
        }

        return curr;
    }

    private double decreaseTemp(double temp) {
        if (tempDelta != 0)
            return temp - tempDelta;
        else
            return temp - 1;
    }

    // returns true if algorithm should be stopped
    private boolean stopCondition(int iteration, int stagnationCounter) {
        return iteration > maxIterations || stagnationCounter > maxStagnationCount;
    }

    private int randomNeighbour(int current) {
        int bitToChange = (int) (Math.random() * numberOfItems);
        if ((current & (1 << bitToChange)) != 0)      // if current has 1 on "bitToChange" index
            return current & ~(1 << bitToChange);     // then change it to 0
        else
            return current | (1 << bitToChange);      // else change it to 1
    }

    private int compareSolutions(int s1, int s2) {
        if (overTheLimit(s1) && !overTheLimit(s2))
            return -1;
        if (!overTheLimit(s1) && overTheLimit(s2))
            return 1;

        double s1v = calculateValue(s1), s2v = calculateValue(s2);
        if (s1v == s2v)
            return 0;
        else
            return (s1v < s2v) ? -1 : 1;
    }

    public boolean overTheLimit(int solution) {
        int size = 0;
        for (int i = 0; i < numberOfItems; i++) {
            if ((solution & (1 << i)) != 0)
                size += weights[i];
        }
        return size >= capacity;
    }

    public double calculateValue(int solution) {
        int value = 0;
        for (int i = 0; i < numberOfItems; i++) {
            if ((solution & (1 << i)) != 0)
                value += values[i];
        }

        if (overTheLimit(solution)){
            double capacityOverhead = getSolutionSize(solution) - capacity;
            return value - (capacityOverhead * limitExceededMultiplier);
        }
        return value;
    }
    public int getSolutionSize(int solution) {
        int size = 0;
        for (int i = 0; i < numberOfItems; i++) {
            if ((solution & (1 << i)) != 0)
                size += weights[i];
        }
        return size;
    }

    private double calculateProbability(int curr, int next, double temp) {
//        double P = Math.exp(-((Math.abs(calculateValue(curr) - calculateValue(next))) / (temp)));
        double v1 = calculateValue(curr);
        double v2 = calculateValue(next);
        double mabs = -(Math.abs(v1 - v2));
        double P = Math.exp(mabs/temp) * probabilityMultiplier;

        return P;
    }
}
