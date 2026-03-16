package com.quantityApp;

import java.util.function.DoubleBinaryOperator;

public  class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    private static final double EPSILON = 1e-6;

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


    private enum ArithmeticOperation {

        ADD((a, b) -> a + b),

        SUBTRACT((a, b) -> a - b),

        DIVIDE((a, b) -> {
            if (b == 0)
                throw new ArithmeticException("Division by zero");
            return a / b;
        });

        private final DoubleBinaryOperator operator;

        ArithmeticOperation(DoubleBinaryOperator operator) {
            this.operator = operator;
        }

        public double compute(double a, double b) {
            return operator.applyAsDouble(a, b);
        }
    }


    private void validateArithmeticOperands(
            Quantity<U> other,
            U targetUnit,
            boolean targetUnitRequired) {

        if (other == null)
            throw new IllegalArgumentException("Operand cannot be null");

        if (this.unit.getClass() != other.unit.getClass())
            throw new IllegalArgumentException("Incompatible measurement categories");

        if (!Double.isFinite(this.value) || !Double.isFinite(other.value))
            throw new IllegalArgumentException("Values must be finite");

        if (targetUnitRequired && targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");
    }


    private double performBaseArithmetic(
            Quantity<U> other,
            ArithmeticOperation operation) {

        double baseValue1 = unit.convertToBaseUnit(value);
        double baseValue2 = other.unit.convertToBaseUnit(other.value);

        return operation.compute(baseValue1, baseValue2);
    }


    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {

        validateArithmeticOperands(other, targetUnit, true);

        double baseResult =
                performBaseArithmetic(other, ArithmeticOperation.ADD);

        double converted =
                targetUnit.convertFromBaseUnit(baseResult);

        return new Quantity<>(
                roundToTwoDecimals(converted),
                targetUnit
        );
    }

  

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

        validateArithmeticOperands(other, targetUnit, true);

        double baseResult =
                performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);

        double converted =
                targetUnit.convertFromBaseUnit(baseResult);

        return new Quantity<>(
                roundToTwoDecimals(converted),
                targetUnit
        );
    }

  

    public double divide(Quantity<U> other) {

        validateArithmeticOperands(other, null, false);

        return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
    }

 

    private double roundToTwoDecimals(double val) {
        return Math.round(val * 100.0) / 100.0;
    }

 

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof Quantity<?> other))
            return false;

        if (this.unit.getClass() != other.unit.getClass())
            return false;

        double thisBase = unit.convertToBaseUnit(value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        return Math.abs(thisBase - otherBase) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(unit.convertToBaseUnit(value));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}