public class MyVector {
    double[] values;
    String answer;

    public MyVector(String[] row, boolean hasAnswer) throws NumberFormatException {
        if (hasAnswer) {
            // all values should be parsable to double apart from the last one which is an answer
            values = new double[row.length - 1];
            for (int i = 0; i < row.length - 1; i++)
                values[i] = Double.parseDouble(row[i]); // exception might be thrown!
            answer = row[row.length - 1];
        } else {
            values = new double[row.length];
            for (int i = 0; i < row.length; i++)
                values[i] = Double.parseDouble(row[i]);
        }
    }

    public MyVector(String[] row) throws NumberFormatException {
        this(row, true);
    }

    public double calculateDistance(MyVector other) {
        // vectors must be the same size (or DataSets of same column number)
        if (this.values.length != other.values.length)
            return -1;

        double sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += Math.pow(values[i] - other.values[i], 2);
        }

        return Math.sqrt(sum);
    }

    @Override
    public String toString() {
        String result = "[";
        for (int i = 0; i < values.length; i++) {
            result += values[i] + "  ";
        }
        return result + "] -> " + answer;
    }
}
