import java.util.*;
import java.util.stream.Collectors;

public class Trainer {
    List<MyVector> trainSet, testSet;
    Perceptron perceptron;
    String activationClassLabel;
    double a;

    public Trainer(ArrayList<String[]> trainSet, ArrayList<String[]> testSet, double a) {
        this.trainSet = trainSet.stream().map(MyVector::new).collect(Collectors.toList());
        this.testSet = testSet.stream().map(MyVector::new).collect(Collectors.toList());
        perceptron = new Perceptron(trainSet.get(0).length - 1);
        activationClassLabel = this.trainSet.get(0).getLabel();
        this.a = a;
        Collections.shuffle(this.trainSet);
        Collections.shuffle(this.testSet);
    }

    public void train() {
        for (MyVector v : trainSet) {
            double net = perceptron.compute(v.getAttributes());
            if (net >= 0 && !activationClassLabel.equals(v.getLabel()))
                perceptron.learn(-1, a, v.getAttributes());
            else if (net < 0 && activationClassLabel.equals(v.getLabel()))
                perceptron.learn(1, a, v.getAttributes());
        }
    }
    public double test(boolean printMoreInfo){
        double success = 0;
        for (MyVector vector : testSet) {
            boolean correct = perceptron.ifCorrect(vector, activationClassLabel);
            if (correct)
                success++;
            if(printMoreInfo){
                System.out.print(vector + " \t\tnet(-bias): " + perceptron.compute(vector.getAttributes()) +
                        (correct ? "\t\tPASS\n" : "\t\tFAIL\n"));
            }
        }
        return (success/ testSet.size())*100;
    }
}
