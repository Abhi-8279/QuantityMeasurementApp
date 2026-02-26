package com.quantityApp;

import java.util.Objects;
import java.util.Scanner;

public class App {

    public enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.0328084);

        private final double conversionFactorToFeet;

        LengthUnit(double conversionFactorToFeet) {
            this.conversionFactorToFeet = conversionFactorToFeet;
        }

        public double getConversionFactor() {
            return conversionFactorToFeet;
        }
    }

    public static class QuantityLength {

        private final double value;
        private final LengthUnit unit;
        private static final double EPSILON = 1e-6;

        public QuantityLength(double value, LengthUnit unit) {
            if (!Double.isFinite(value))
                throw new IllegalArgumentException("Value must be finite");

            if (unit == null)
                throw new IllegalArgumentException("Unit cannot be null");

            this.value = value;
            this.unit = unit;
        }

        public double getValue() {
            return value;
        }

        public LengthUnit getUnit() {
            return unit;
        }

        // UC5 Conversion Logic

        public static double convert(double value,
                                     LengthUnit source,
                                     LengthUnit target) {

            if (!Double.isFinite(value))
                throw new IllegalArgumentException("Value must be finite");

            if (source == null || target == null)
                throw new IllegalArgumentException("Units cannot be null");

            double valueInFeet = value * source.getConversionFactor();
            return valueInFeet / target.getConversionFactor();
        }

        public QuantityLength convertTo(LengthUnit targetUnit) {
            double convertedValue = convert(this.value, this.unit, targetUnit);
            return new QuantityLength(convertedValue, targetUnit);
        }

        // UC6 Addition Logic

        public QuantityLength add(QuantityLength other) {

            if (other == null)throw new IllegalArgumentException("Cannot add null quantity");

            // Convert both to base unit (feet)
            double thisInFeet = this.value * this.unit.getConversionFactor();
            double otherInFeet = other.value * other.unit.getConversionFactor();

            // Add in base unit
            double sumInFeet = thisInFeet + otherInFeet;

            // Convert back to this object's unit
            double resultValue = sumInFeet / this.unit.getConversionFactor();

            return new QuantityLength(resultValue, this.unit);
        }

        // Equality Logic

        private double toBaseUnit() {
            return value * unit.getConversionFactor();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof QuantityLength)) return false;

            QuantityLength other = (QuantityLength) obj;
            return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
        }

        @Override
        public int hashCode() {
            return Objects.hash(toBaseUnit());
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }


    public static void main(String[] args) {
    	Scanner in = new Scanner(System.in);
    	System.out.println("Enter the Units ");
    	double val1=in.nextDouble();
    	double val2=in.nextDouble();
        QuantityLength q1 = new QuantityLength(val1, LengthUnit.YARDS);
        QuantityLength q2 = new QuantityLength(val2, LengthUnit.INCHES);

        QuantityLength result = q1.add(q2);

        System.out.println("Addition Result: " + result); 
    }
}