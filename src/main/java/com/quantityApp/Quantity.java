package com.quantityApp;

import java.util.Objects;

public class Quantity<U extends Enum<U> & IMeasurable> {

    private final double value;
    private final U unit;

    private static final double EPSILON = 0.0001;

    public Quantity(double value, U unit) {

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

    public U getUnit() {
        return unit;
    }

    public Quantity<U> convertTo(U target) {

        double base = unit.toBaseUnit(value);
        double converted = target.fromBaseUnit(base);

        return new Quantity<>(converted, target);
    }

    private Quantity<U> performArithmetic(Quantity<U> other,
                                          ArithmeticOperation op,
                                          U targetUnit) {

        unit.validateOperationSupport(op.name());
        other.unit.validateOperationSupport(op.name());

        double base1 = unit.toBaseUnit(value);
        double base2 = other.unit.toBaseUnit(other.value);

        double result = op.apply(base1, base2);

        double finalValue = targetUnit.fromBaseUnit(result);

        return new Quantity<>(finalValue, targetUnit);
    }

    public Quantity<U> add(Quantity<U> other) {
        return performArithmetic(other, ArithmeticOperation.ADD, unit);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        return performArithmetic(other, ArithmeticOperation.SUBTRACT, unit);
    }

    public Quantity<U> divide(Quantity<U> other) {

        unit.validateOperationSupport("DIVIDE");

        double base1 = unit.toBaseUnit(value);
        double base2 = other.unit.toBaseUnit(other.value);

        return new Quantity<>(base1 / base2, unit);
    }

    private double toBase() {
        return unit.toBaseUnit(value);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (!(obj instanceof Quantity<?> other)) return false;

        if (!unit.getClass().equals(other.unit.getClass()))
            return false;

        return Math.abs(this.toBase() - other.toBase()) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(toBase());
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}