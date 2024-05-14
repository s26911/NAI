import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class KMeans {
    ArrayList<double[]> data;
    int rowSize;
    int k;
    double bestE;

    public KMeans(ArrayList<double[]> data, int k) {
        this.data = data;
        rowSize = data.get(0).length;
        this.k = k;
        this.bestE = -1.0;
    }

    public HashMap<Integer, ArrayList<double[]>> start(int numberOfTimes) {
        double bestE = Double.MAX_VALUE;
        HashMap<Integer, ArrayList<double[]>> bestGroups = new HashMap<>();

        // repeat procedure {numberOfTimes}
        for (int i = 0; i < numberOfTimes; i++) {
            System.out.println("Loop " + (i + 1));
            ArrayList<double[]> centroids = initCentroids();
            double E = -1;
            int iterationCounter = 1;

            // repeat grouping and calculating centroids while E is changing
            while (true) {
                HashMap<Integer, ArrayList<double[]>> groups = assignGroups(centroids);
                centroids = calculateCentroids(groups);
                double currE = calculateE(groups, centroids);
                System.out.println("\t" + iterationCounter++ + " iteration\tE = " + currE);

                // loop end condition
                if (currE == E) {
                    if (currE < bestE) {
                        bestE = currE;
                        bestGroups = groups;
                    }
                    break;
                }

                E = currE;
            }
        }

        System.out.println("Best E: " + bestE + "\n");
        return bestGroups;
    }

    public static void printGroups(HashMap<Integer, ArrayList<double[]>> groups) {
        for (int i = 0; i < groups.size(); i++) {
            System.out.println("Group " + i + ":");
            ArrayList<double[]> members = groups.get(i);
            for (double[] member : members) {
                System.out.println("\t" + Arrays.toString(member));
            }
        }
    }

    // randomly chooses k of the vectors as starting centroids
    private ArrayList<double[]> initCentroids() {
        ArrayList<double[]> centroids = new ArrayList<>();
        ArrayList<double[]> dataCopy = new ArrayList<>(data);
        while (centroids.size() < k) {
            int i = (int) (Math.random() * dataCopy.size());
            centroids.add(dataCopy.get(i));
            dataCopy.remove(i);
        }
        return centroids;
    }

    public HashMap<Integer, ArrayList<double[]>> assignGroups(ArrayList<double[]> centroids) {
        HashMap<Integer, ArrayList<double[]>> groups = new HashMap<>();

        for (double[] row : data) {
            int centroidIndex = 0;
            double distance = calculateDistance(row, centroids.get(0));

            for (int i = 1; i < k; i++) {
                double currDistance = calculateDistance(row, centroids.get(i));
                if (currDistance < distance) {
                    distance = currDistance;
                    centroidIndex = i;
                }
            }

            groups.putIfAbsent(centroidIndex, new ArrayList<>());
            groups.get(centroidIndex).add(row);
        }

        return groups;
    }

    public double calculateDistance(double[] v1, double[] v2) {
        double sum = 0;
        for (int i = 0; i < v1.length; i++) {
            sum += Math.pow(v1[i] - v2[i], 2);
        }
        return Math.sqrt(sum);
    }

    public double calculateE(HashMap<Integer, ArrayList<double[]>> groups, ArrayList<double[]> centroids) {
        double sum = 0;
        for (int i = 0; i < groups.size(); i++) {
            for (int j = 0; j < groups.get(i).size(); j++) {
                sum += Math.pow(calculateDistance(groups.get(i).get(j), centroids.get(i)), 2);
            }
        }
        return sum;
    }

    public ArrayList<double[]> calculateCentroids(HashMap<Integer, ArrayList<double[]>> groups) {
        ArrayList<double[]> centroids = new ArrayList<>();
        for (int i = 0; i < groups.size(); i++) {
            ArrayList<double[]> groupMembers = groups.get(i);
            double[] centroid = new double[rowSize];
            for (int j = 0; j < centroid.length; j++) {
                int finalJ = j;
                centroid[j] = groupMembers.stream().map(x -> x[finalJ]).reduce(0., Double::sum) / groupMembers.size();
            }
            centroids.add(centroid);
        }
        return centroids;
    }

}
