import java.util.ArrayList;

public class BayesClassificator {
    ArrayList<String[]> trainSet;
    ArrayList<double[]> probabilities;
    ArrayList<String[]> columnsDistinctOptions;     // including label column
    String[] distinctLabels;
    int columnCount;                                // including label column

    public BayesClassificator(ArrayList<String[]> trainSet) {
        this.trainSet = trainSet;
        this.columnCount = trainSet.get(0).length;
        this.columnsDistinctOptions = new ArrayList<>();
        this.probabilities = new ArrayList<>();
        calculateDistinctOptions();
        distinctLabels = columnsDistinctOptions.getLast();
        calculateProbabilities();
    }


    // calculates probabilites for each VALUE (of each column) under the condition of each LABEL,
    // e.g. for column {"sunny", "rainy"} and labels {"yes", "no"} this.probabilities will contain
    // P("sunny" | "yes"), P("sunny" | "no"), P("rainy" | "yes"), P("rainy", "no")
    private void calculateProbabilities() {
        // for each label count number of occurrences
        double[] labelCounts = new double[distinctLabels.length];
        for (int i = 0; i < distinctLabels.length; i++) {
            int finalI = i;
            labelCounts[i] = trainSet.stream().filter(x -> x[columnCount - 1].equals(distinctLabels[finalI])).count();
        }

        // for each column of distinct values ... (without column of labels)
        for (int i = 0; i < columnsDistinctOptions.size() - 1; i++) {
            double[] columnProbabilities = new double[(columnsDistinctOptions.get(i).length) * distinctLabels.length];
            int counter = 0;

            String[] columnOptions = columnsDistinctOptions.get(i);
            // ... and for each OPTION from those distinct values ...
            for (String option : columnOptions) {
                int finalI = i;
                // ... and for each of DECISIVE attributes option
                for (int j = 0; j < distinctLabels.length; j++) {
                    int finalJ = j;

                    // OPTION + DECISIVE combinations count / DECISIVE count
                    double combinationCount = trainSet.stream().
                            filter(x -> x[finalI].equals(option) &&
                                    x[columnCount - 1].equals(distinctLabels[finalJ]))
                            .count();

                    // Laplace smoothing
                    double probability = combinationCount == 0 ? 1 / (labelCounts[finalJ] + columnOptions.length) : combinationCount / labelCounts[finalJ];
                    columnProbabilities[counter++] = probability;
                }
            }

            probabilities.add(columnProbabilities);
        }

        // add probabilities for labels (label x count / number of rows)
        double[] labelProbabilities = new double[distinctLabels.length];
        for (int i = 0; i < distinctLabels.length; i++)
            labelProbabilities[i] = labelCounts[i] / trainSet.size();
        probabilities.add(labelProbabilities);
    }

    private int getIndexOf(String[] array, String key) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }

    // classifies given row of data using already calculated probabilities
    public String classify(String[] row) {
        double maxProbability = 0;
        String maxProbabilityDecisiveValue = "";

        // for each DECISIVE attribute
        for (int i = 0; i < distinctLabels.length; i++) {
            double result = probabilities.getLast()[i];

            // check each row + DECISIVE probability
            for (int j = 0; j < row.length; j++) {
                String value = row[j];
                int index = getIndexOf(columnsDistinctOptions.get(j), value);
                double valueProbability = probabilities.get(j)[index * distinctLabels.length + i];
                result *= valueProbability;
            }

            if (result > maxProbability) {
                maxProbability = result;
                maxProbabilityDecisiveValue = distinctLabels[i];
            }
        }

        return maxProbabilityDecisiveValue;
    }

    // classifies given row of data, calculates (only necessary) values on the go
    public String classifyOnTheGo(String[] row) {
        int numberOfRows = trainSet.size();
        String maxProbLabel = "";
        double maxProbability = -1;

        // how many times each of the possible label values occurs in the trainSet, e.g. yes 56 times, no 12 times
        int[] labelCounts = new int[distinctLabels.length];
        for (int i = 0; i < labelCounts.length; i++) {
            int finalI = i;
            labelCounts[i] = (int) trainSet.stream()
                    .filter(x -> x[columnCount - 1].equals(distinctLabels[finalI]))
                    .count();
        }

        // calculates the probability for each possible label
        for (int i = 0; i < distinctLabels.length; i++) {
            double thisProbability = (double) labelCounts[i] / numberOfRows;

            // for each attribute from argument row of data
            for (int j = 0; j < columnCount - 1; j++) {
                int finalI = i;
                int finalJ = j;
                // count attribute x + label y combination occurrences
                double count = trainSet.stream()
                        .filter(x -> x[finalJ].equals(row[finalJ]) && x[columnCount - 1].equals(distinctLabels[finalI]))
                        .count();

                if (count == 0) {
                    // Laplace smoothing
                    thisProbability *= 1. / (labelCounts[i] + columnsDistinctOptions.get(j).length);
                } else
                    thisProbability *= count / labelCounts[i];
            }

            if (thisProbability > maxProbability) {
                maxProbability = thisProbability;
                maxProbLabel = distinctLabels[i];
            }
        }

        return maxProbLabel;
    }

    private void calculateDistinctOptions() {
        for (int i = 0; i < columnCount; i++) {
            final int j = i;
            columnsDistinctOptions.add(trainSet.stream().map(row -> row[j]).distinct().toArray(String[]::new));
        }
    }
}
