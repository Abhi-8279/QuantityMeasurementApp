package com.quantityApp.units;

public interface IMeasurable {

    double getConversionFactor();

    double toBaseUnit(double value);

    double fromBaseUnit(double value);

    default boolean supportsArithmetic() {
        return true;
    }

    String getUnitName();
}
