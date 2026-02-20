package com.quantityApp;
public class App {

    // Base Unit: FEET
    public enum LengthUnit {

        FEET(1.0),                    // Base unit
        INCHES(1.0 / 12.0),           // 1 inch = 1/12 feet
        YARDS(3.0),                   // 1 yard = 3 feet
        CENTIMETERS(0.393701 / 12.0); // 1 cm = 0.393701 inches
                                        // Convert inches to feet → divide by 12

        private final double conversionFactorToFeet;

        LengthUnit(double conversionFactorToFeet) {
            this.conversionFactorToFeet = conversionFactorToFeet;
        }

        public double toFeet(double value) {
            return value * conversionFactorToFeet;
        }
    }

    // Generic QuantityLength Class (UNCHANGED)
    public static class QuantityLength {

        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;

            if (obj == null || getClass() != obj.getClass())
                return false;

            QuantityLength other = (QuantityLength) obj;

            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // Main Method Demo (UC4)
    public static void main(String[] args) {

        QuantityLength q1 =  new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q2 =  new QuantityLength(3.0, LengthUnit.FEET);

        System.out.println("Input: " + q1 + " and " + q2);
        System.out.println("Output: Equal (" + q1.equals(q2) + ")");

        QuantityLength q3 =  new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q4 =new QuantityLength(36.0, LengthUnit.INCHES);

        System.out.println("Input: " + q3 + " and " + q4);
        System.out.println("Output: Equal (" + q3.equals(q4) + ")");

        QuantityLength q5 =  new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        QuantityLength q6 =  new QuantityLength(0.393701, LengthUnit.INCHES);

        System.out.println("Input: " + q5 + " and " + q6);
        System.out.println("Output: Equal (" + q5.equals(q6) + ")");
    }
}