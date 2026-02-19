package com.quantityApp;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class QuantityMeasurementAppTest {

    @Test
    void testEquality_SameValue() {
    	QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(1.0);
    	QuantityMeasurementApp.Feet f2 = new QuantityMeasurementApp.Feet(1.0);

        assertTrue(f1.equals(f2), "1.0 ft should equal 1.0 ft");
    }

    @Test
    void testEquality_DifferentValue() {
    	QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(1.0);
    	QuantityMeasurementApp.Feet f2 = new QuantityMeasurementApp.Feet(2.0);

        assertFalse(f1.equals(f2), "1.0 ft should not equal 2.0 ft");
    }

    @Test
    void testEquality_NullComparison() {
    	QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(1.0);

        assertFalse(f1.equals(null), "1.0 ft should not equal null");
    }

    @Test
    void testEquality_NonNumericInput() {
    	QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(1.0);

        assertFalse(f1.equals("text"), "1.0 ft should not equal non-numeric input");
    }

    @Test
    void testEquality_SameReference() {
    	QuantityMeasurementApp.Feet f1 = new QuantityMeasurementApp.Feet(1.0);

        assertTrue(f1.equals(f1), "Object should equal itself");
    }
}
// INCH TESTS
@Test
void testInchEquality_SameValue() {
    assertTrue(QuantityMeasurementApp.compareInches(1.0, 1.0));
}

@Test
void testInchEquality_DifferentValue() {
    assertFalse(QuantityMeasurementApp.compareInches(1.0, 2.0));
}

@Test
void testInchEquality_NullComparison() {
    QuantityMeasurementApp.Inches i = new QuantityMeasurementApp.Inches(1.0);
    assertFalse(i.equals(null));
}

@Test
void testInchEquality_NonNumericInput() {
    QuantityMeasurementApp.Inches i = new QuantityMeasurementApp.Inches(1.0);
    assertFalse(i.equals("text"));
}

@Test
void testInchEquality_SameReference() {
    QuantityMeasurementApp.Inches i = new QuantityMeasurementApp.Inches(1.0);
    assertTrue(i.equals(i));
}
}

