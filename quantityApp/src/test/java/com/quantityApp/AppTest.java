package com.quantityApp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    private static final double EPSILON = 1e-6;

    @Test
    void testAddition_SameUnit() {
        App.QuantityLength q1 =
                new App.QuantityLength(1.0, App.LengthUnit.FEET);
        App.QuantityLength q2 =
                new App.QuantityLength(2.0, App.LengthUnit.FEET);

        App.QuantityLength result = q1.add(q2);

        assertEquals(3.0, result.getValue(), EPSILON);
        assertEquals(App.LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testAddition_CrossUnit_FeetPlusInches() {
        App.QuantityLength feet =
                new App.QuantityLength(1.0, App.LengthUnit.FEET);
        App.QuantityLength inches =
                new App.QuantityLength(12.0, App.LengthUnit.INCHES);

        App.QuantityLength result = feet.add(inches);

        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(App.LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testAddition_CrossUnit_InchesPlusFeet() {
        App.QuantityLength inches =
                new App.QuantityLength(12.0, App.LengthUnit.INCHES);
        App.QuantityLength feet =
                new App.QuantityLength(1.0, App.LengthUnit.FEET);

        App.QuantityLength result = inches.add(feet);

        assertEquals(24.0, result.getValue(), EPSILON);
        assertEquals(App.LengthUnit.INCHES, result.getUnit());
    }

    @Test
    void testAddition_WithZero() {
        App.QuantityLength q1 =
                new App.QuantityLength(5.0, App.LengthUnit.FEET);
        App.QuantityLength zero =
                new App.QuantityLength(0.0, App.LengthUnit.INCHES);

        App.QuantityLength result = q1.add(zero);

        assertEquals(5.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_NegativeValues() {
        App.QuantityLength q1 =
                new App.QuantityLength(5.0, App.LengthUnit.FEET);
        App.QuantityLength q2 =
                new App.QuantityLength(-2.0, App.LengthUnit.FEET);

        App.QuantityLength result = q1.add(q2);

        assertEquals(3.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_Null_Throws() {
        App.QuantityLength q1 =
                new App.QuantityLength(1.0, App.LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class,
                () -> q1.add(null));
    }
}