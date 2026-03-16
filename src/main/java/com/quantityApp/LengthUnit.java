package com.quantityApp;

public enum LengthUnit implements IMeasurable {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(0.0328084);

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    @Override
    public double getConversionFactor() {
        return conversionFactor;
    }

    @Override
    public String getUnitName() {
        return name();
    }

    // Convert unit → base unit (FEET)
    @Override
    public double toBaseUnit(double value) {
        return value * conversionFactor;
    }

    // Convert base unit → this unit
    @Override
    public double fromBaseUnit(double value) {
        return value / conversionFactor;
    }

    @Override
    public void validateOperationSupport(String operation) {
        // Length supports arithmetic operations
    }
}