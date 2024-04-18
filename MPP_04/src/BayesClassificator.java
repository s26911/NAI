import java.util.ArrayList;
import java.util.Arrays;

public class BayesClassificator {
    ArrayList<String[]> trainSet;
    ArrayList<double[]> probabilities;
    ArrayList<String[]> columnsDistinctOptions;
    String[] labelDistinctOptions;
    int columnCount;

    public BayesClassificator(ArrayList<String[]> trainSet) {
        this.trainSet = trainSet;
        this.columnCount = trainSet.get(0).length;
        this.columnsDistinctOptions = new ArrayList<>();
        this.probabilities = new ArrayList<>();
        calculateDistinctOptions();
        labelDistinctOptions = columnsDistinctOptions.getLast();
        calculateProbabilities();
    }

    private void calculateProbabilities() {
        double[] decisiveAttributesCounts = new double[labelDistinctOptions.length];
        for (int i = 0; i < labelDistinctOptions.length; i++) {
            int finalI = i;
            decisiveAttributesCounts[i] = trainSet.stream().filter(x -> x[columnCount - 1].equals(labelDistinctOptions[finalI])).count();
        }

        // for each column of distinct values ...
        for (int i = 0; i < columnsDistinctOptions.size() - 1; i++) {
            double[] columnProbabilities = new double[(columnsDistinctOptions.get(i).length) * labelDistinctOptions.length];
            int counter = 0;

            String[] columnOptions = columnsDistinctOptions.get(i);
            // ... and for each OPTION from those distinct values ...
            for (String option : columnOptions) {
                int finalI = i;
                int finalCounter = counter;
                // ... and for each of DECISIVE attributes option
                for (int j = 0; j < labelDistinctOptions.length; j++) {
                    int finalJ = j;

                    // OPTION + DECISIVE combinations count / DECISIVE count
                    double combinationCount = trainSet.stream().
                            filter(x -> x[finalI].equals(option) &&
                                    x[columnCount - 1].equals(labelDistinctOptions[finalJ]))
                            .count();

                    // wygładzanie
                    double probability = combinationCount == 0 ? 1 / (decisiveAttributesCounts[finalJ] + columnOptions.length) : combinationCount / decisiveAttributesCounts[finalJ];
                    columnProbabilities[counter++] = probability;
                }

            }


            probabilities.add(columnProbabilities);
        }

        double[] labelProbabilities = new double[labelDistinctOptions.length];
        for (int i = 0; i < labelDistinctOptions.length; i++)
            labelProbabilities[i] = decisiveAttributesCounts[i] / trainSet.size();
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

    public String classify(String[] row) {
        double maxProbability = 0;
        String maxProbabilityDecisiveValue = "";

        // for each DECISIVE attribute
        for (int i = 0; i < labelDistinctOptions.length; i++) {
            System.out.println("PROB: " + 0);
            double result = probabilities.getLast()[i];
            System.out.println("PROB: " + result);

            // check each row + DECISIVE probability
            for (int j = 0; j < row.length; j++) {
                String value = row[j];
                int index = getIndexOf(columnsDistinctOptions.get(j), value);
                double valueProbability = probabilities.get(j)[index * labelDistinctOptions.length + i];
                result *= valueProbability;
                System.out.println("PROB: " + result);
            }

            System.out.println("Probability of  " + labelDistinctOptions[i] + ": " + result);

            if (result > maxProbability) {
                maxProbability = result;
                maxProbabilityDecisiveValue = labelDistinctOptions[i];
            }
        }

        System.out.println("RESULT: " + maxProbabilityDecisiveValue + ": " + maxProbability);
        return maxProbabilityDecisiveValue;
    }

    public void classifyOnTheGo(String[] row) {
        int numberOfRows = trainSet.size();
        String maxProbLabel = "";
        double maxProbability = -1;

        // how many times each of the possible label values occurs in the trainSet, e.g. yes 56 times, no 12 times
        int[] labelCounts = new int[labelDistinctOptions.length];
        for (int i = 0; i < labelCounts.length; i++) {
            int finalI = i;
            labelCounts[i] = (int) trainSet.stream()
                    .filter(x -> x[columnCount - 1].equals(labelDistinctOptions[finalI]))
                    .count();
        }

        for (int i = 0; i < labelDistinctOptions.length; i++) {
            System.out.println("PROB: " + 0);
            double thisProbability = (double) labelCounts[i] / numberOfRows;
            System.out.println("PROB: " + thisProbability);

            for (int j = 0; j < columnCount - 1; j++) {
                int finalI = i;
                int finalJ = j;
                double thisColumnProbability = trainSet.stream()
                        .filter(x -> x[finalJ].equals(row[finalJ]) && x[columnCount - 1].equals(labelDistinctOptions[finalI]))
                        .count();

                if (thisColumnProbability == 0) {
                    thisColumnProbability = 1. / (labelCounts[i] + columnsDistinctOptions.get(j).length);
                } else
                    thisColumnProbability = thisColumnProbability / labelCounts[i];

                thisProbability *= thisColumnProbability;
                System.out.println("PROB: " + thisProbability);
            }
            System.out.println("Probability of  " + labelDistinctOptions[i] + ": " + thisProbability);

            if (thisProbability > maxProbability) {
                maxProbability = thisProbability;
                maxProbLabel = labelDistinctOptions[i];
            }
        }

        System.out.println("RESULT: " + maxProbLabel + ": " + maxProbability);
    }

    private void calculateDistinctOptions() {
        for (int i = 0; i < columnCount; i++) {
            final int j = i;
            columnsDistinctOptions.add(trainSet.stream().map(row -> row[j]).distinct().toArray(String[]::new));
        }
//        for (var entry : columnsDistinctOptions) {
//            System.out.println(Arrays.toString(entry));
//        }
    }
}
