public class Vector {
    double[] attributes;
    String label;

    public Vector(String[] row) {
        attributes = new double[row.length - 1];
        for (int i = 0; i < attributes.length; i++) {
            attributes[i] = Double.parseDouble(row[i]);
        }

        label = row[row.length - 1];
    }

    public String getLabel() {
        return label;
    }
}
