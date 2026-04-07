package com.quantityApp;

import com.quantityApp.dto.QuantityDTO;
import com.quantityApp.dto.QuantityInputDTO;

final class TestData {

    private TestData() {
    }

    static QuantityInputDTO compareInput() {
        return input(1.0, "FEET", "LengthUnit", 12.0, "INCHES", "LengthUnit");
    }

    static QuantityInputDTO convertInput() {
        return input(1.0, "FEET", "LengthUnit", 0.0, "INCHES", "LengthUnit");
    }

    static QuantityInputDTO invalidUnitInput() {
        return input(1.0, "FOOT", "LengthUnit", 12.0, "INCHE", "LengthUnit");
    }

    static QuantityInputDTO incompatibleInput() {
        return input(1.0, "FEET", "LengthUnit", 1.0, "KILOGRAM", "WeightUnit");
    }

    static QuantityInputDTO divideByZeroInput() {
        return input(1.0, "FEET", "LengthUnit", 0.0, "INCHES", "LengthUnit");
    }

    private static QuantityInputDTO input(
            double thisValue,
            String thisUnit,
            String thisType,
            double thatValue,
            String thatUnit,
            String thatType) {
        QuantityInputDTO input = new QuantityInputDTO();
        input.setThisQuantityDTO(quantity(thisValue, thisUnit, thisType));
        input.setThatQuantityDTO(quantity(thatValue, thatUnit, thatType));
        return input;
    }

    private static QuantityDTO quantity(double value, String unit, String measurementType) {
        QuantityDTO quantityDTO = new QuantityDTO();
        quantityDTO.setValue(value);
        quantityDTO.setUnit(unit);
        quantityDTO.setMeasurementType(measurementType);
        return quantityDTO;
    }
}
