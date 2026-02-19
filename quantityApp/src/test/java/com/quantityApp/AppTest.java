package com.quantityApp;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void testEquality_SameValue() {
        App.Feet f1 = new App.Feet(1.0);
        App.Feet f2 = new App.Feet(1.0);

        assertTrue(f1.equals(f2), "1.0 ft should equal 1.0 ft");
    }

    @Test
    void testEquality_DifferentValue() {
        App.Feet f1 = new App.Feet(1.0);
        App.Feet f2 = new App.Feet(2.0);

        assertFalse(f1.equals(f2), "1.0 ft should not equal 2.0 ft");
    }

    @Test
    void testEquality_NullComparison() {
        App.Feet f1 = new App.Feet(1.0);

        assertFalse(f1.equals(null), "1.0 ft should not equal null");
    }

    @Test
    void testEquality_NonNumericInput() {
        App.Feet f1 = new App.Feet(1.0);

        assertFalse(f1.equals("text"), "1.0 ft should not equal non-numeric input");
    }

    @Test
    void testEquality_SameReference() {
        App.Feet f1 = new App.Feet(1.0);

        assertTrue(f1.equals(f1), "Object should equal itself");
    }
}
