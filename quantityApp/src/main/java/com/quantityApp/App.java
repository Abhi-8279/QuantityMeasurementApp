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

	    public double toFeet(double value) {
	        return value * conversionFactorToFeet;
	    }

	    public double fromFeet(double feetValue) {
	        return feetValue / conversionFactorToFeet;
	    }
	}
	public static final class QuantityLength {

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

        private static double addInBase(QuantityLength q1, QuantityLength q2) {

            double q1Feet = q1.unit.toFeet(q1.value);
            double q2Feet = q2.unit.toFeet(q2.value);

            return q1Feet + q2Feet;
        }

        // 🔹 UC6 
        public QuantityLength add(QuantityLength other) {
            return add(other, this.unit);
        }

        // 🔹 UC7 (Explicit Target Unit)
        public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {

            if (other == null)
                throw new IllegalArgumentException("Other quantity cannot be null");

            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double sumInFeet = addInBase(this, other);

            double finalValue = targetUnit.fromFeet(sumInFeet);

            return new QuantityLength(finalValue, targetUnit);
        }

        // Equality (Base Comparison)
        private double toFeet() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof QuantityLength)) return false;

            QuantityLength other = (QuantityLength) obj;
            return Math.abs(this.toFeet() - other.toFeet()) < EPSILON;
        }

        @Override
        public int hashCode() {
            long bits = Double.doubleToLongBits(toFeet());
            return (int) (bits ^ (bits >>> 32));
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
	}
	public static void main(String[] args) {

	    QuantityLength q1=new QuantityLength(1.23 ,LengthUnit.FEET);
	    QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

	    System.out.println(q1.add(q2, LengthUnit.FEET));
	    System.out.println(q1.add(q2, LengthUnit.INCHES));
	    System.out.println(q1.add(q2, LengthUnit.YARDS));
	}
	
}