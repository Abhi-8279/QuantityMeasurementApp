package com.quantityApp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    private static final double EPSILON = 1e-6;

    // Basic Unit Conversions

    @Test
    void testConversion_FeetToInches() {
        double result = App.QuantityLength.convert(
                1.0,
                App.LengthUnit.FEET,
                App.LengthUnit.INCHES
        );
        assertEquals(12.0, result, EPSILON);
    }

    @Test
    void testConversion_InchesToFeet() {
        double result = App.QuantityLength.convert(
                24.0,
                App.LengthUnit.INCHES,
                App.LengthUnit.FEET
        );
        assertEquals(2.0, result, EPSILON);
    }

    @Test
    void testConversion_YardsToInches() {
        double result = App.QuantityLength.convert(
                1.0,
                App.LengthUnit.YARDS,
                App.LengthUnit.INCHES
        );
        assertEquals(36.0, result, EPSILON);
    }

    @Test
    void testConversion_InchesToYards() {
        double result = App.QuantityLength.convert(
                72.0,
                App.LengthUnit.INCHES,
                App.LengthUnit.YARDS
        );
        assertEquals(2.0, result, EPSILON);
    }

    @Test
    void testConversion_CentimetersToInches() {
        double result = App.QuantityLength.convert(
                2.54,
                App.LengthUnit.CENTIMETERS,
                App.LengthUnit.INCHES
        );
        assertEquals(1.0, result, EPSILON);
    }

    @Test
    void testConversion_FeetToYards() {
        double result = App.QuantityLength.convert(
                6.0,
                App.LengthUnit.FEET,
                App.LengthUnit.YARDS
        );
        assertEquals(2.0, result, EPSILON);
    }

    // Same Unit Conversion

    @Test
    void testConversion_SameUnit() {
        double result = App.QuantityLength.convert(
                5.0,
                App.LengthUnit.FEET,
                App.LengthUnit.FEET
        );
        assertEquals(5.0, result, EPSILON);
    }

    // Zero & Negative Values

    @Test
    void testConversion_ZeroValue() {
        double result = App.QuantityLength.convert(
                0.0,
                App.LengthUnit.FEET,
                App.LengthUnit.INCHES
        );
        assertEquals(0.0, result, EPSILON);
    }

    @Test
    void testConversion_NegativeValue() {
        double result = App.QuantityLength.convert(
                -1.0,
                App.LengthUnit.FEET,
                App.LengthUnit.INCHES
        );
        assertEquals(-12.0, result, EPSILON);
    }

    // Round Trip Conversion

    @Test
    void testConversion_RoundTrip_PreservesValue() {
        double original = 5.75;

        double converted = App.QuantityLength.convert(
                original,
                App.LengthUnit.FEET,
                App.LengthUnit.INCHES
        );

        double roundTrip = App.QuantityLength.convert(
                converted,
                App.LengthUnit.INCHES,
                App.LengthUnit.FEET
        );

        assertEquals(original, roundTrip, EPSILON);
    }

    // Instance Method Conversion

    @Test
    void testInstanceMethod_ConvertTo() {
        App.QuantityLength length =
                new App.QuantityLength(36.0, App.LengthUnit.INCHES);

        App.QuantityLength converted =
                length.convertTo(App.LengthUnit.YARDS);

        assertEquals(1.0, converted.getValue(), EPSILON);
        assertEquals(App.LengthUnit.YARDS, converted.getUnit());
    }

    // Invalid Input Handling

    @Test
    void testConversion_NullSourceUnit_Throws() {
        assertThrows(IllegalArgumentException.class, () ->
                App.QuantityLength.convert(
                        1.0,
                        null,
                        App.LengthUnit.FEET
                )
        );
    }

    @Test
    void testConversion_NullTargetUnit_Throws() {
        assertThrows(IllegalArgumentException.class, () ->
                App.QuantityLength.convert(
                        1.0,
                        App.LengthUnit.FEET,
                        null
                )
        );
    }

    @Test
    void testConversion_NaN_Throws() {
        assertThrows(IllegalArgumentException.class, () ->
                App.QuantityLength.convert(
                        Double.NaN,
                        App.LengthUnit.FEET,
                        App.LengthUnit.INCHES
                )
        );
    }

    @Test
    void testConversion_Infinite_Throws() {
        assertThrows(IllegalArgumentException.class, () ->
                App.QuantityLength.convert(
                        Double.POSITIVE_INFINITY,
                        App.LengthUnit.FEET,
                        App.LengthUnit.INCHES
                )
        );
    }
}