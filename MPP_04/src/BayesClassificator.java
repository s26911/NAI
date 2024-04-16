import java.util.ArrayList;
import java.util.Arrays;

public class BayesClassificator {
    ArrayList<String[]> trainSet;
    ArrayList<double[]> probabilities;
    ArrayList<String[]> columnsDistinctOptions;
    String[] decisiveAttributesOptions;
    int columnCount;

    public BayesClassificator(ArrayList<String[]> trainSet) {
        this.trainSet = trainSet;
        this.columnCount = trainSet.get(0).length;
        this.columnsDistinctOptions = new ArrayList<>();
        this.probabilities = new ArrayList<>();
        calculateDistinctOptions();
        decisiveAttributesOptions = columnsDistinctOptions.getLast();
        calculateProbabilities();
    }

    private void calculateProbabilities() {
        double[] decisiveAttributesCounts = new double[decisiveAttributesOptions.length];
        for (int i = 0; i < decisiveAttributesOptions.length; i++) {
            int finalI = i;
            decisiveAttributesCounts[i] = trainSet.stream().filter(x -> x[columnCount - 1].equals(decisiveAttributesOptions[finalI])).count();
        }

        // for each column of distinct values ...
        for (int i = 0; i < columnsDistinctOptions.size() - 1; i++) {
            double[] columnProbabilities = new double[columnsDistinctOptions.get(i).length * decisiveAttributesOptions.length];
            int counter = 0;

            String[] columnOptions = columnsDistinctOptions.get(i);
            // ... and for each OPTION from those distinct values ...
            for (String option : columnOptions) {
                int finalI = i;
                int finalCounter = counter;
                // ... and for each of DECISIVE attributes option
                for (int j = 0; j < decisiveAttributesOptions.length; j++) {
                    int finalJ = j;

                    // OPTION + DECISIVE combinations count / DECISIVE count
                    double combinationCount = trainSet.stream().
                            filter(x -> x[finalI].equals(option) &&
                                    x[columnCount - 1].equals(decisiveAttributesOptions[finalJ]))
                            .count();

                    // wygładzanie
                    double probability = combinationCount == 0 ? 1 / (decisiveAttributesCounts[finalJ] + columnOptions.length) : combinationCount / decisiveAttributesCounts[finalJ];
                    columnProbabilities[counter++] = probability;
                }

            }

            probabilities.add(columnProbabilities);
        }
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
