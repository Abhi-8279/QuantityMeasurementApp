package com.quantityApp;
public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

  

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

        validate(other);

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double baseResult = base1 - base2;

        double result =
                targetUnit.convertFromBaseUnit(baseResult);

        result = round(result);

        return new Quantity<>(result, targetUnit);
    }


    public double divide(Quantity<U> other) {

        validate(other);

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        if (base2 == 0) {
            throw new ArithmeticException("Division by zero");
        }

        return base1 / base2;
    }

    private void validate(Quantity<U> other) {

        if (other == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }

        if (!unit.getClass().equals(other.unit.getClass())) {
            throw new IllegalArgumentException("Different measurement categories");
        }

        if (Double.isNaN(value) || Double.isInfinite(value)
                || Double.isNaN(other.value) || Double.isInfinite(other.value)) {

            throw new IllegalArgumentException("Invalid numeric value");
        }
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }

    private double round(double val) {
        return Math.round(val * 100.0) / 100.0;
    }
}