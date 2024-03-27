import java.util.*;

public class DataSet {
    ArrayList<MyVector> data;
    int columnNumber;

    public DataSet(ArrayList<String[]> inputData) {
        columnNumber = inputData.get(0).length;
        data = new ArrayList<>();

        for (String[] row : inputData) {
            if (columnNumber != row.length)
                System.out.println("Row: " + Arrays.toString(row) + "has invalid number of columns." +
                        " This line will be skipped!");
            else {
                try {
                    data.add(new MyVector(row));
                } catch (NumberFormatException e) {
                    System.out.println("Row: " + Arrays.toString(row) + "has invalid formatting." +
                            " This line will be skipped!");
                }
            }
        }
    }

    // compute distances and return k nearest vectors from this data set to Vector v
    public TreeMap<Double, String> computeKNN(MyVector v, int k) {
        TreeMap<Double, String> kNearest = new TreeMap<>(); // sorted key value pairs
        for (MyVector trainSetVector : data) {
            if (kNearest.size() < k) // we fill kNearest until there are k values
                kNearest.put(v.calculateDistance(trainSetVector), trainSetVector.answer);
            else {   // then we check whether the current one should be in kNearest, add it if it should
                double d = v.calculateDistance(trainSetVector);
                if (d < kNearest.lastKey()) {
                    kNearest.put(d, trainSetVector.answer);
                    kNearest.remove(kNearest.lastKey());
                }
            }
        }

        return kNearest;
    }

    // classify Vectors from passed DataSet, check+return accuracy and print info on console
    public double checkTestSet(DataSet testSet, int k) {
        if (columnNumber != testSet.columnNumber) {
            System.out.println("Difference in column numbers between sets!");
            return -1;
        }

        int success = 0, fail = 0;
        for (MyVector testSetMyVector : testSet.data) {
            String result = calculateAnswer(testSetMyVector, k);

            if (result.equals(testSetMyVector.answer)) {
                if (!Main.SILENT)
                    System.out.print("+++ Calculated: " + result + "\t\t Answer: " + testSetMyVector.answer + "\n");
                success++;
                if (Main.DEBUG) System.out.println();
            } else {
                if (!Main.SILENT)
                    System.out.print("--- Calculated: " + result + "\t\t Answer: " + testSetMyVector.answer + "\n");
                fail++;
                if (Main.DEBUG) System.out.println();
            }
        }

        double accuracy = (double) success / (success + fail);
        if (!Main.SILENT)
            System.out.println("\n\nOverall accuracy: " + accuracy);
        return accuracy;
    }

    public String calculateAnswer(MyVector vector, int k) {
        // compute distances and generate kNearest vectors
        TreeMap<Double, String> kNearest = computeKNN(vector, k);

        // print kNearest in DEBUG mode
        if (Main.DEBUG) {
            var a = kNearest.firstEntry();
            for (int i = 0; i < kNearest.size(); i++) {
                System.out.println(i + 1 + ". " + a.getValue() + "\t\t" + a.getKey());
                a = kNearest.higherEntry(a.getKey());
            }
        }

        // count answers
        ArrayList<String> answers = new ArrayList<>();
        ArrayList<Integer> answersCount = new ArrayList<>();
        for (String answer : kNearest.values()) {
            int i = answers.indexOf(answer);
            if (i == -1) {
                answers.add(answer);
                answersCount.add(1);
            } else {
                answersCount.set(i, answersCount.get(i) + 1);
            }
        }

        // what is the max count and how many answers does it correspond to
        int max = answersCount.stream().max(Integer::compareTo).orElse(0);
        int countOfMax = (int) answersCount.stream().filter(x -> x == max).count();
        String result = "";
        if (countOfMax == 1) {
            result = answers.get(answersCount.indexOf(max));
        } else {
            // (rare case)
            // if more than one has maximum number of occurrences in kNearest then pick overall closer answer
            ArrayList<String> listOfAnswersWhichHaveMaximum = new ArrayList<>();
            double[] sumOFDistances = new double[countOfMax];
            for (int i = 0; i < answersCount.size(); i++) {
                if (answersCount.get(i) == max)
                    listOfAnswersWhichHaveMaximum.add(answers.get(i));
            }
            // calculate sum of distances for each candidate answer
            for (Map.Entry<Double, String> entry : kNearest.entrySet()) {
                int i;
                if ((i = listOfAnswersWhichHaveMaximum.indexOf(entry.getValue())) != -1) {
                    sumOFDistances[i] += entry.getKey();
                }
            }
            // get the answer with the closest corresponding sum of distances
            int minIndex = 0;
            double minVal = sumOFDistances[0];
            for (int i = 1; i < sumOFDistances.length; i++) {
                if (sumOFDistances[i] < minVal)
                    minIndex = i;
            }

            result = listOfAnswersWhichHaveMaximum.get(minIndex);
        }
        return result;
    }
}
