package com.quantityApp;

public interface IMeasurable {

    double getConversionFactor();  

    default double convertToBaseUnit(double value) {
        return value * getConversionFactor();
    }

    default double convertFromBaseUnit(double baseValue) {
        return baseValue / getConversionFactor();
    }
    double toBaseUnit(double value);

    double fromBaseUnit(double value);

    SupportsArithmetic supportsArithmetic = () -> true;

    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    String getUnitName();

	void validateOperationSupport(String operation);
}