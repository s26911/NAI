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
            int y = perceptron.compute(v.getAttributes()) >= 0 ? 1 : 0;
            int d = v.getLabel().equals(activationClassLabel) ? 1 : 0;

            perceptron.learn(d,y,a, v.getAttributes());
        }
    }

    public double[] test(boolean printMoreInfo){
        int success = 0, class1 = 0, class2 = 0, class1Count = 0, class2Count = 0;
        for (MyVector vector : testSet) {
            boolean correct = perceptron.ifCorrect(vector, activationClassLabel);
            if (correct){
                success++;
                int i = vector.getLabel().equals(activationClassLabel) ? class1++ : class2++;
            }
            if(printMoreInfo){
                System.out.print(vector + " \t\tnet(-bias): " + perceptron.compute(vector.getAttributes()) +
                        (correct ? "\t\tPASS\n" : "\t\tFAIL\n"));
            }

            int i = vector.getLabel().equals(activationClassLabel) ? class1Count++ : class2Count++;
        }
        return new double[]{((double)success/ testSet.size())*100,
                            (double)class1* 100/class1Count,
                            (double)class2* 100/class2Count};
    }
}
