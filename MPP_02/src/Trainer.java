import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Trainer {
    List<Vector> trainSet, testSet;
    Perceptron perceptron;
    String activationClassLabel;

    public Trainer(ArrayList<String[]> trainSet, ArrayList<String[]> testSet) {
        this.trainSet = trainSet.stream().map(Vector::new).collect(Collectors.toList());
        this.testSet = testSet.stream().map(Vector::new).collect(Collectors.toList());
        perceptron = new Perceptron(trainSet.get(0).length - 1);
        activationClassLabel = this.trainSet.get(0).getLabel();
    }

    public void train() {

    }
}
