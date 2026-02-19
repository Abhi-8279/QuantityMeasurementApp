package com.quantityApp;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class AppTest {

    // FEET TESTS
    @Test
    void testFeetEquality_SameValue() {
        assertTrue(App.compareFeet(1.0, 1.0));
    }

    @Test
    void testFeetEquality_DifferentValue() {
        assertFalse(App.compareFeet(1.0, 2.0));
    }

    @Test
    void testFeetEquality_NullComparison() {
        App.Feet f = new App.Feet(1.0);
        assertFalse(f.equals(null));
    }

    @Test
    void testFeetEquality_NonNumericInput() {
        App.Feet f = new App.Feet(1.0);
        assertFalse(f.equals("text"));
    }

    @Test
    void testFeetEquality_SameReference() {
        App.Feet f = new App.Feet(1.0);
        assertTrue(f.equals(f));
    }

    // INCH TESTS
    @Test
    void testInchEquality_SameValue() {
        assertTrue(App.compareInches(1.0, 1.0));
    }

    @Test
    void testInchEquality_DifferentValue() {
        assertFalse(App.compareInches(1.0, 2.0));
    }

    @Test
    void testInchEquality_NullComparison() {
        App.Inches i = new App.Inches(1.0);
        assertFalse(i.equals(null));
    }

    @Test
    void testInchEquality_NonNumericInput() {
        App.Inches i = new App.Inches(1.0);
        assertFalse(i.equals("text"));
    }

    @Test
    void testInchEquality_SameReference() {
        App.Inches i = new App.Inches(1.0);
        assertTrue(i.equals(i));
    }
}
