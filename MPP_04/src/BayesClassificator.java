import java.util.ArrayList;
import java.util.Arrays;

public class BayesClassificator {
    ArrayList<String[]> trainSet;
    int columnCount;    // attributes + labels(last column)
    int[] columnsDistinctOptionsCount;

    public BayesClassificator(ArrayList<String[]> trainSet) {
        this.trainSet = trainSet;
        columnCount = trainSet.get(0).length;
        columnsDistinctOptionsCount = new int[columnCount];
        countData();
    }

    public void countData(){
        for (int i = 0; i < columnCount; i++) {
            final int j = i;
            columnsDistinctOptionsCount[i] = (int) trainSet.stream().map(row -> row[j]).distinct().count();
        }
//        System.out.println(Arrays.toString(columnsDistinctOptionsCount));
    }
}
