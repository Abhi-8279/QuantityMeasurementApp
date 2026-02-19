package com.quantityApp;

public class QuantityMeasurementApp {
	// Inner class representing Feet measurement
    public static class Feet {
        private final double value;

        // Constructor
        public Feet(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        // Override equals method
        @Override
        public boolean equals(Object obj) {

            // Same reference check
            if (this == obj) {
                return true;
            }

            // Null or different class check
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            // Type casting
            Feet other = (Feet) obj;

            // Compare values safely
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);

        boolean result = f1.equals(f2);

        System.out.println("Input: 1.0 ft and 1.0 ft");
        System.out.println("Output: Equal (" + result + ")");
    }
}
// Inches class
public static class Inches {
    private final double value;

    public Inches(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Inches other = (Inches) obj;
        return Double.compare(this.value, other.value) == 0;
    }
}

// method to compare feet
public static boolean compareFeet(double v1, double v2) {
    Feet f1 = new Feet(v1);
    Feet f2 = new Feet(v2);
    return f1.equals(f2);
}

// method to compare inches
public static boolean compareInches(double v1, double v2) {
    Inches i1 = new Inches(v1);
    Inches i2 = new Inches(v2);
    return i1.equals(i2);
}

// Main method
public static void main(String[] args) {

    boolean inchResult = compareInches(1.0, 1.0);
    System.out.println("Input: 1.0 inch and 1.0 inch");
    System.out.println("Output: Equal (" + inchResult + ")");

    boolean feetResult = compareFeet(1.0, 1.0);
    System.out.println("Input: 1.0 ft and 1.0 ft");
    System.out.println("Output: Equal (" + feetResult + ")");
}
}
