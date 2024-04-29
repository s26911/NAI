import java.util.ArrayList;
import java.util.HashMap;

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

        // ...
    }

    private ArrayList<double[]> initCentroids() {
        ArrayList<double[]> centroids = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            double[] centroid = new double[rowSize];
            for (int j = 0; j < rowSize; j++) {
                centroid[j] = Math.random() * 5;
            }
            centroids.add(centroid);
        }
        return centroids;
    }
    public HashMap<Integer, ArrayList<double[]>> assignGroups() {
        // ...
    }

    public double calculateE() {
        // ...
    }

    public void calculateCentroids() {
        // ...
    }

}
