package com.quantityApp;

import java.util.Objects;

/**
 * Represents a length quantity with a numeric value and a unit.
 * 
 * This is an immutable value object.
 * All conversions are normalized through a base unit (FEET).
 */
public class App {

    /**
     * Enum representing supported length units.
     * Each unit stores its conversion factor relative to FEET (base unit).
     */
    public enum LengthUnit {

        FEET(1.0),                 // Base unit
        INCHES(1.0 / 12.0),        // 12 inches = 1 foot
        YARDS(3.0),                // 1 yard = 3 feet
        CENTIMETERS(0.0328084);    // 1 cm = 0.0328084 feet

        private final double conversionFactorToFeet;

        LengthUnit(double conversionFactorToFeet) {
            this.conversionFactorToFeet = conversionFactorToFeet;
        }

        public double getConversionFactor() {
            return conversionFactorToFeet;
        }
    }

    /**
     * Immutable value object representing a length quantity.
     */
    public static class QuantityLength {

        private final double value;
        private final LengthUnit unit;

        private static final double EPSILON = 1e-6;

        /**
         * Constructor with validation.
         */
        public QuantityLength(double value, LengthUnit unit) {

            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Value must be finite.");
            }

            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null.");
            }

            this.value = value;
            this.unit = unit;
        }

        public double getValue() {
            return value;
        }

        public LengthUnit getUnit() {
            return unit;
        }

        /**
         * Converts this QuantityLength to a target unit.
         * Returns a new immutable QuantityLength instance.
         */
        public QuantityLength convertTo(LengthUnit targetUnit) {

            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null.");
            }

            double convertedValue = convert(this.value, this.unit, targetUnit);
            return new QuantityLength(convertedValue, targetUnit);
        }

        /**
         * Static conversion API.
         * Converts a value from source unit to target unit.
         */
        public static double convert(double value,
                                     LengthUnit source,
                                     LengthUnit target) {

            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Value must be finite.");
            }

            if (source == null || target == null) {
                throw new IllegalArgumentException("Units cannot be null.");
            }

            // Normalize to base unit (feet)
            double valueInFeet = value * source.getConversionFactor();

            // Convert from base to target
            return valueInFeet / target.getConversionFactor();
        }

        /**
         * Converts current value to base unit (feet).
         * Private helper for comparison.
         */
        private double toBaseUnit() {
            return value * unit.getConversionFactor();
        }

        /**
         * Equality based on physical length equivalence.
         * Uses base unit normalization and epsilon tolerance.
         */
        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;

            if (!(obj instanceof QuantityLength)) return false;

            QuantityLength other = (QuantityLength) obj;

            return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
        }

        /**
         * HashCode consistent with equals.
         */
        @Override
        public int hashCode() {
            return Objects.hash(toBaseUnit());
        }

        /**
         * Human-readable representation.
         */
        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    /**
     * Demonstration main method (for standalone testing).
     */
    public static void main(String[] args) {

        // Example conversions
        System.out.println(QuantityLength.convert(1.0,LengthUnit.FEET,LengthUnit.INCHES)); // 12.0

        System.out.println(QuantityLength.convert(3.0,LengthUnit.YARDS,LengthUnit.FEET));   // 9.0

        // Instance method conversion
        QuantityLength length = new QuantityLength(36.0, LengthUnit.INCHES);
        System.out.println(length.convertTo(LengthUnit.YARDS)); // 1.0 YARDS

        // Equality check
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println(q1.equals(q2)); // true
    }
}