package com.qma.measurement.service;

import com.qma.measurement.dto.CalculationResultDTO;
import com.qma.measurement.dto.QuantityDTO;
import com.qma.measurement.dto.QuantityInputDTO;
import org.springframework.stereotype.Service;

@Service
public class MeasurementCalculatorService {

    public CalculationResultDTO calculate(String operation, QuantityInputDTO input) {
        String normalizedOperation = operation == null ? "" : operation.trim().toUpperCase();
        return switch (normalizedOperation) {
            case "COMPARE" -> compare(input);
            case "CONVERT" -> convert(input);
            case "ADD" -> add(input);
            case "SUBTRACT" -> subtract(input);
            case "MULTIPLY" -> multiply(input);
            case "DIVIDE" -> divide(input);
            default -> throw new IllegalArgumentException("Unsupported operation: " + operation);
        };
    }

    private CalculationResultDTO compare(QuantityInputDTO input) {
        QuantityDTO left = require(input.getThisQuantityDTO(), "thisQuantityDTO is required");
        QuantityDTO right = require(input.getThatQuantityDTO(), "thatQuantityDTO is required");

        String type = requireText(left.getMeasurementType(), "measurementType is required");
        double a = requireValue(left.getValue(), "thisQuantityDTO.value is required");
        double b = requireValue(right.getValue(), "thatQuantityDTO.value is required");

        boolean isEqual;

        if ("LENGTH".equalsIgnoreCase(type)) {
            isEqual = Math.abs(toMillimeter(a, left.getUnit()) - toMillimeter(b, right.getUnit())) < 0.0001;
        } else if ("TEMPERATURE".equalsIgnoreCase(type)) {
            isEqual = Math.abs(toKelvin(a, left.getUnit()) - toKelvin(b, right.getUnit())) < 0.0001;
        } else if ("VOLUME".equalsIgnoreCase(type)) {
            isEqual = Math.abs(toMilliliter(a, left.getUnit()) - toMilliliter(b, right.getUnit())) < 0.0001;
        } else if ("WEIGHT".equalsIgnoreCase(type)) {
            isEqual = Math.abs(toGrams(a, left.getUnit()) - toGrams(b, right.getUnit())) < 0.0001;
        } else {
            throw new IllegalArgumentException("Unsupported measurement type: " + type);
        }

        return new CalculationResultDTO(isEqual ? 1.0 : 0.0, isEqual ? "TRUE" : "FALSE");
    }

    private CalculationResultDTO convert(QuantityInputDTO input) {
        QuantityDTO from = require(input.getThisQuantityDTO(), "thisQuantityDTO is required");
        QuantityDTO to = require(input.getThatQuantityDTO(), "thatQuantityDTO is required");

        double value = requireValue(from.getValue(), "thisQuantityDTO.value is required");
        String fromUnit = requireText(from.getUnit(), "thisQuantityDTO.unit is required");
        String toUnit = requireText(to.getUnit(), "thatQuantityDTO.unit is required");
        String type = requireText(from.getMeasurementType(), "thisQuantityDTO.measurementType is required");

        double result;

        if ("TEMPERATURE".equalsIgnoreCase(type)) {
            result = fromKelvin(toKelvin(value, fromUnit), toUnit);
        } else if ("LENGTH".equalsIgnoreCase(type)) {
            result = fromMillimeter(toMillimeter(value, fromUnit), toUnit);
        } else if ("VOLUME".equalsIgnoreCase(type)) {
            result = fromMilliliter(toMilliliter(value, fromUnit), toUnit);
        } else if ("WEIGHT".equalsIgnoreCase(type)) {
            result = fromGrams(toGrams(value, fromUnit), toUnit);
        } else {
            throw new IllegalArgumentException("Unsupported measurement type: " + type);
        }

        return new CalculationResultDTO(round(result), toUnit);
    }

    private CalculationResultDTO add(QuantityInputDTO input) {
        QuantityDTO left = require(input.getThisQuantityDTO(), "thisQuantityDTO is required");
        QuantityDTO right = require(input.getThatQuantityDTO(), "thatQuantityDTO is required");

        String type = requireText(left.getMeasurementType(), "measurementType is required");
        double a = requireValue(left.getValue(), "thisQuantityDTO.value is required");
        double b = requireValue(right.getValue(), "thatQuantityDTO.value is required");
        String unit = requireText(left.getUnit(), "thisQuantityDTO.unit is required");

        double result;

        if ("LENGTH".equalsIgnoreCase(type)) {
            result = fromMillimeter(toMillimeter(a, unit) + toMillimeter(b, right.getUnit()), unit);
        } else if ("TEMPERATURE".equalsIgnoreCase(type)) {
            result = fromKelvin(toKelvin(a, unit) + toKelvin(b, right.getUnit()), unit);
        } else if ("VOLUME".equalsIgnoreCase(type)) {
            result = fromMilliliter(toMilliliter(a, unit) + toMilliliter(b, right.getUnit()), unit);
        } else if ("WEIGHT".equalsIgnoreCase(type)) {
            result = fromGrams(toGrams(a, unit) + toGrams(b, right.getUnit()), unit);
        } else {
            throw new IllegalArgumentException("Unsupported measurement type: " + type);
        }

        return new CalculationResultDTO(round(result), unit);
    }

    private CalculationResultDTO subtract(QuantityInputDTO input) {
        QuantityDTO left = require(input.getThisQuantityDTO(), "thisQuantityDTO is required");
        QuantityDTO right = require(input.getThatQuantityDTO(), "thatQuantityDTO is required");

        String type = requireText(left.getMeasurementType(), "measurementType is required");
        double a = requireValue(left.getValue(), "thisQuantityDTO.value is required");
        double b = requireValue(right.getValue(), "thatQuantityDTO.value is required");
        String unit = requireText(left.getUnit(), "thisQuantityDTO.unit is required");

        double result;

        if ("LENGTH".equalsIgnoreCase(type)) {
            result = fromMillimeter(toMillimeter(a, unit) - toMillimeter(b, right.getUnit()), unit);
        } else if ("TEMPERATURE".equalsIgnoreCase(type)) {
            result = fromKelvin(toKelvin(a, unit) - toKelvin(b, right.getUnit()), unit);
        } else if ("VOLUME".equalsIgnoreCase(type)) {
            result = fromMilliliter(toMilliliter(a, unit) - toMilliliter(b, right.getUnit()), unit);
        } else if ("WEIGHT".equalsIgnoreCase(type)) {
            result = fromGrams(toGrams(a, unit) - toGrams(b, right.getUnit()), unit);
        } else {
            throw new IllegalArgumentException("Unsupported measurement type: " + type);
        }

        return new CalculationResultDTO(round(result), unit);
    }

    private CalculationResultDTO multiply(QuantityInputDTO input) {
        QuantityDTO left = require(input.getThisQuantityDTO(), "thisQuantityDTO is required");
        QuantityDTO right = require(input.getThatQuantityDTO(), "thatQuantityDTO is required");

        double a = requireValue(left.getValue(), "thisQuantityDTO.value is required");
        double b = requireValue(right.getValue(), "thatQuantityDTO.value is required");
        String unit = requireText(left.getUnit(), "thisQuantityDTO.unit is required");

        return new CalculationResultDTO(round(a * b), unit + "^2");
    }

    private CalculationResultDTO divide(QuantityInputDTO input) {
        QuantityDTO left = require(input.getThisQuantityDTO(), "thisQuantityDTO is required");
        QuantityDTO right = require(input.getThatQuantityDTO(), "thatQuantityDTO is required");

        double a = requireValue(left.getValue(), "thisQuantityDTO.value is required");
        double b = requireValue(right.getValue(), "thatQuantityDTO.value is required");

        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }

        return new CalculationResultDTO(round(a / b), "RATIO");
    }

    private <T> T require(T value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    private String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    private double requireValue(Double value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    private double toMillimeter(double value, String unit) {
        return switch (unit.toUpperCase()) {
            case "MILLIMETER", "MILLIMETERS" -> value;
            case "CENTIMETER", "CENTIMETERS" -> value * 10;
            case "METER", "METERS" -> value * 1000;
            case "KILOMETER", "KILOMETERS" -> value * 1_000_000;
            case "INCH", "INCHES" -> value * 25.4;
            case "FEET", "FOOT" -> value * 304.8;
            case "YARD", "YARDS" -> value * 914.4;
            default -> throw new IllegalArgumentException("Invalid length unit: " + unit);
        };
    }

    private double fromMillimeter(double value, String unit) {
        return switch (unit.toUpperCase()) {
            case "MILLIMETER", "MILLIMETERS" -> value;
            case "CENTIMETER", "CENTIMETERS" -> value / 10;
            case "METER", "METERS" -> value / 1000;
            case "KILOMETER", "KILOMETERS" -> value / 1_000_000;
            case "INCH", "INCHES" -> value / 25.4;
            case "FEET", "FOOT" -> value / 304.8;
            case "YARD", "YARDS" -> value / 914.4;
            default -> throw new IllegalArgumentException("Invalid length unit: " + unit);
        };
    }

    private double toKelvin(double value, String unit) {
        return switch (unit.toUpperCase()) {
            case "CELSIUS" -> value + 273.15;
            case "FAHRENHEIT" -> (value - 32) * 5 / 9 + 273.15;
            case "KELVIN" -> value;
            default -> throw new IllegalArgumentException("Invalid temperature unit: " + unit);
        };
    }

    private double fromKelvin(double value, String unit) {
        return switch (unit.toUpperCase()) {
            case "CELSIUS" -> value - 273.15;
            case "FAHRENHEIT" -> (value - 273.15) * 9 / 5 + 32;
            case "KELVIN" -> value;
            default -> throw new IllegalArgumentException("Invalid temperature unit: " + unit);
        };
    }

    private double toMilliliter(double value, String unit) {
        return switch (unit.toUpperCase()) {
            case "LITER" -> value * 1000;
            case "MILLILITER" -> value;
            case "CUBIC_METER" -> value * 1_000_000;
            case "GALLON" -> value * 3785.41;
            default -> throw new IllegalArgumentException("Invalid volume unit: " + unit);
        };
    }

    private double fromMilliliter(double value, String unit) {
        return switch (unit.toUpperCase()) {
            case "LITER" -> value / 1000;
            case "MILLILITER" -> value;
            case "CUBIC_METER" -> value / 1_000_000;
            case "GALLON" -> value / 3785.41;
            default -> throw new IllegalArgumentException("Invalid volume unit: " + unit);
        };
    }

    private double toGrams(double value, String unit) {
        return switch (unit.toUpperCase()) {
            case "MILLIGRAM" -> value / 1000;
            case "GRAM" -> value;
            case "KILOGRAM" -> value * 1000;
            case "TON" -> value * 1_000_000;
            case "OUNCE" -> value * 28.3495;
            case "POUND" -> value * 453.592;
            default -> throw new IllegalArgumentException("Invalid weight unit: " + unit);
        };
    }

    private double fromGrams(double value, String unit) {
        return switch (unit.toUpperCase()) {
            case "MILLIGRAM" -> value * 1000;
            case "GRAM" -> value;
            case "KILOGRAM" -> value / 1000;
            case "TON" -> value / 1_000_000;
            case "OUNCE" -> value / 28.3495;
            case "POUND" -> value / 453.592;
            default -> throw new IllegalArgumentException("Invalid weight unit: " + unit);
        };
    }

    private double round(double value) {
        return Math.round(value * 1_000_000d) / 1_000_000d;
    }
}