import java.util.Arrays;

public class MyVector {
    double[] attributes;
    String label;

    public MyVector(String[] row) {
        attributes = new double[row.length - 1];
        for (int i = 0; i < attributes.length; i++) {
            attributes[i] = Double.parseDouble(row[i]);
        }

        label = row[row.length - 1];
    }
    public MyVector(String[] row, String label) {
        attributes = new double[row.length];
        for (int i = 0; i < attributes.length; i++) {
            attributes[i] = Double.parseDouble(row[i]);
        }

        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public double[] getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return Arrays.toString(attributes) + " " + label;
    }
}
