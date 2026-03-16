package com.quantityApp;

public enum VolumeUnit implements IMeasurable {

    LITRE(1.0),
    MILLILITRE(0.001),
    GALLON(3.78541);

    private final double conversionFactor;

    VolumeUnit(double conversionFactor) {
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

    // Convert unit → base unit (LITRE)
    @Override
    public double toBaseUnit(double value) {
        return value * conversionFactor;
    }

    // Convert base unit → target unit
    @Override
    public double fromBaseUnit(double value) {
        return value / conversionFactor;
    }

    @Override
    public void validateOperationSupport(String operation) {
        // Volume supports arithmetic operations
    }
}