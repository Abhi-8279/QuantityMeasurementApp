package com.quantityApp;

import java.util.Objects;

public final class QuantityWeight<U extends IMeasurable> {

    private final double value;
    private final U unit;

    private static final double EPSILON = 1e-6;

    public QuantityWeight(double value, U unit) {

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
    private double toBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    public QuantityWeight<U> convertTo(U targetUnit) {

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double base = toBaseUnit();
        double converted = targetUnit.convertFromBaseUnit(base);

        // Round to 2 decimals
        converted = Math.round(converted * 100.0) / 100.0;

        return new QuantityWeight<>(converted, targetUnit);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (!(obj instanceof QuantityWeight<?> other)) return false;

        // Cross-category prevention
        if (this.unit.getClass() != other.unit.getClass())
            return false;

        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
    }
    public QuantityWeight<U> add(QuantityWeight<U> other) {
        return add(other, this.unit);
    }

    public QuantityWeight<U> add(QuantityWeight<U> other, U targetUnit) {

        if (other == null)
            throw new IllegalArgumentException("Other quantity cannot be null");

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double sumBase = this.toBaseUnit() + other.toBaseUnit();
        double finalValue = targetUnit.convertFromBaseUnit(sumBase);

        finalValue = Math.round(finalValue * 100.0) / 100.0;

        return new QuantityWeight<>(finalValue, targetUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toBaseUnit(), unit.getClass());
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }
}