package com.quantityApp.validation;

import com.quantityApp.dto.QuantityDTO;
import com.quantityApp.units.LengthUnit;
import com.quantityApp.units.TemperatureUnit;
import com.quantityApp.units.VolumeUnit;
import com.quantityApp.units.WeightUnit;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class QuantityUnitValidator implements ConstraintValidator<ValidQuantityUnit, QuantityDTO> {

    @Override
    public boolean isValid(QuantityDTO value, ConstraintValidatorContext context) {
        if (value == null || value.getMeasurementType() == null || value.getUnit() == null) {
            return true;
        }

        String measurementType = value.getMeasurementType().trim().toUpperCase();
        String unit = value.getUnit().trim().toUpperCase();
        boolean valid = switch (measurementType) {
            case "LENGTHUNIT", "LENGTH" -> isEnumConstant(unit, LengthUnit.class);
            case "VOLUMEUNIT", "VOLUME" -> isEnumConstant(unit, VolumeUnit.class);
            case "WEIGHTUNIT", "WEIGHT" -> isEnumConstant(unit, WeightUnit.class);
            case "TEMPERATUREUNIT", "TEMPERATURE" -> isEnumConstant(unit, TemperatureUnit.class);
            default -> false;
        };

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Unit must be valid for the specified measurement type")
                    .addConstraintViolation();
        }
        return valid;
    }

    private <E extends Enum<E>> boolean isEnumConstant(String value, Class<E> enumType) {
        try {
            Enum.valueOf(enumType, value);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }
}
