import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

public class KMeans {
    ArrayList<double[]> data;
    int rowSize;
    int k;

    public KMeans(ArrayList<double[]> data, int k) {
        this.data = data;
        rowSize = data.get(0).length;
        this.k = k;
    }

    public HashMap<Integer, ArrayList<double[]>> kMeans() {
        // 1 init
        // 2 assign groups
        // 3 calculate E
        // if Eprev == Enow: end
        // 4 calculate centroids
        // 5 back to 2

        ArrayList<double[]> centroids = initCentroids();
        while (true) {
            HashMap<Integer, ArrayList<double[]>> groups = assignGroups(centroids);
            // ...
        }
    }

    private ArrayList<double[]> initCentroids() {
        ArrayList<double[]> centroids = new ArrayList<>();
        ArrayList<double[]> dataCopy = new ArrayList<>(data);
        while(centroids.size() < k) {
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

    public double calculateE() {
        // ...
    }

    public void calculateCentroids() {
        // ...
    }

}
