package com.quantityApp;
import org.junit.jupiter.api.Test;

import com.quantityApp.App.QuantityLength;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    // Yard to Yard

    @Test
    void testEquality_YardToYard_SameValue() {
        QuantityLength q1 = new App.QuantityLength(1.0,App.LengthUnit.YARDS);
        var q2 = new App.QuantityLength(1.0,
        		App.LengthUnit.YARDS);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_YardToYard_DifferentValue() {
        var q1 = new App.QuantityLength(1.0,
        		App.LengthUnit.YARDS);
        var q2 = new App.QuantityLength(2.0,
        		App.LengthUnit.YARDS);

        assertFalse(q1.equals(q2));
    }

    // Yard to Feet

    @Test
    void testEquality_YardToFeet_EquivalentValue() {
        var q1 = new App.QuantityLength(1.0,
        		App.LengthUnit.YARDS);
        var q2 = new App.QuantityLength(3.0,
        		App.LengthUnit.FEET);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_FeetToYard_EquivalentValue() {
        var q1 = new App.QuantityLength(3.0,
        		App.LengthUnit.FEET);
        var q2 = new App.QuantityLength(1.0,
        		App.LengthUnit.YARDS);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_YardToFeet_NonEquivalentValue() {
        var q1 = new App.QuantityLength(1.0,
        		App.LengthUnit.YARDS);
        var q2 = new App.QuantityLength(2.0,
        		App.LengthUnit.FEET);

        assertFalse(q1.equals(q2));
    }

    // Yard to Inches

    @Test
    void testEquality_YardToInches_EquivalentValue() {
        var q1 = new App.QuantityLength(1.0,
        		App.LengthUnit.YARDS);
        var q2 = new App.QuantityLength(36.0,
        		App.LengthUnit.INCHES);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_InchesToYard_EquivalentValue() {
        var q1 = new App.QuantityLength(36.0,
        		App.LengthUnit.INCHES);
        var q2 = new App.QuantityLength(1.0,
        		App.LengthUnit.YARDS);

        assertTrue(q1.equals(q2));
    }

    // Centimeters Tests

    @Test
    void testEquality_centimetersToInches_EquivalentValue() {
        var q1 = new App.QuantityLength(1.0,
        		App.LengthUnit.CENTIMETERS);
        var q2 = new App.QuantityLength(0.393701,
        		App.LengthUnit.INCHES);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_centimetersToFeet_NonEquivalentValue() {
        var q1 = new App.QuantityLength(1.0,
        		App.LengthUnit.CENTIMETERS);
        var q2 = new App.QuantityLength(1.0,
        		App.LengthUnit.FEET);

        assertFalse(q1.equals(q2));
    }

    // Transitive Property

    @Test
    void testEquality_MultiUnit_TransitiveProperty() {

        var yard = new App.QuantityLength(1.0,
        		App.LengthUnit.YARDS);

        var feet = new App.QuantityLength(3.0,
        		App.LengthUnit.FEET);

        var inches = new App.QuantityLength(36.0,
        		App.LengthUnit.INCHES);

        assertTrue(yard.equals(feet));
        assertTrue(feet.equals(inches));
        assertTrue(yard.equals(inches)); // Transitive
    }

    // Null & Safety Tests (Yard)

    @Test
    void testEquality_YardWithNullUnit() {
        assertThrows(IllegalArgumentException.class, () ->
                new App.QuantityLength(1.0, null));
    }

    @Test
    void testEquality_YardSameReference() {
        var yard = new App.QuantityLength(1.0,
        		App.LengthUnit.YARDS);

        assertTrue(yard.equals(yard));
    }

    @Test
    void testEquality_YardNullComparison() {
        var yard = new App.QuantityLength(1.0,
        		App.LengthUnit.YARDS);

        assertFalse(yard.equals(null));
    }

    // Null & Safety Tests (Centimeters)

    @Test
    void testEquality_CentimetersWithNullUnit() {
        assertThrows(IllegalArgumentException.class, () ->
                new App.QuantityLength(1.0, null));
    }

    @Test
    void testEquality_CentimetersSameReference() {
        var cm = new App.QuantityLength(2.0,
        		App.LengthUnit.CENTIMETERS);

        assertTrue(cm.equals(cm));
    }

    @Test
    void testEquality_CentimetersNullComparison() {
        var cm = new App.QuantityLength(2.0,
        		App.LengthUnit.CENTIMETERS);

        assertFalse(cm.equals(null));
    }

    // Complex Scenario (All Units)

    @Test
    void testEquality_AllUnits_ComplexScenario() {

        var yard = new App.QuantityLength(2.0,
        		App.LengthUnit.YARDS);

        var feet = new App.QuantityLength(6.0,
        		App.LengthUnit.FEET);

        var inches = new App.QuantityLength(72.0,
        		App.LengthUnit.INCHES);

        assertTrue(yard.equals(feet));
        assertTrue(feet.equals(inches));
        assertTrue(yard.equals(inches));
    }
}