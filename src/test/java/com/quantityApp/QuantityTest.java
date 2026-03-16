package com.quantityApp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class QuantityTest {

    // ADD TESTS

    @Test
    void testAdd_SameUnit() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(5.0, LengthUnit.FEET);

        Quantity<LengthUnit> result = q1.add(q2);

        assertEquals(15.0, result.getValue());
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testAdd_CrossUnit() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(12.0, LengthUnit.INCHES);

        Quantity<LengthUnit> result = q1.add(q2);

        assertEquals(2.0, result.getValue());
    }

    @Test
    void testAdd_ExplicitTargetUnit() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(12.0, LengthUnit.INCHES);

        Quantity<LengthUnit> result =
                q1.add(q2, LengthUnit.INCHES);

        assertEquals(24.0, result.getValue());
        assertEquals(LengthUnit.INCHES, result.getUnit());
    }

    // SUBTRACT TESTS

    @Test
    void testSubtract_SameUnit() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(5.0, LengthUnit.FEET);

        Quantity<LengthUnit> result = q1.subtract(q2);

        assertEquals(5.0, result.getValue());
    }

    @Test
    void testSubtract_CrossUnit() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(6.0, LengthUnit.INCHES);

        Quantity<LengthUnit> result = q1.subtract(q2);

        assertEquals(9.5, result.getValue());
    }

    @Test
    void testSubtract_ExplicitTargetUnit() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(6.0, LengthUnit.INCHES);

        Quantity<LengthUnit> result =
                q1.subtract(q2, LengthUnit.INCHES);

        assertEquals(114.0, result.getValue());
        assertEquals(LengthUnit.INCHES, result.getUnit());
    }

    // DIVISION TESTS

    @Test
    void testDivide_SameUnit() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(2.0, LengthUnit.FEET);

        double result = q1.divide(q2);

        assertEquals(5.0, result);
    }

    @Test
    void testDivide_CrossUnit() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(24.0, LengthUnit.INCHES);

        Quantity<LengthUnit> q2 =
                new Quantity<>(2.0, LengthUnit.FEET);

        double result = q1.divide(q2);

        assertEquals(1.0, result);
    }

    @Test
    void testDivide_RatioLessThanOne() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(5.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(10.0, LengthUnit.FEET);

        double result = q1.divide(q2);

        assertEquals(0.5, result);
    }

    // VALIDATION TESTS

    @Test
    void testValidation_NullOperand_Add() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(10.0, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class,
                () -> q1.add(null));
    }

    @Test
    void testValidation_NullOperand_Subtract() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(10.0, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class,
                () -> q1.subtract(null));
    }

    @Test
    void testValidation_NullOperand_Divide() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(10.0, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class,
                () -> q1.divide(null));
    }

    // DIVISION BY ZERO

    @Test
    void testDivision_ByZero() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(0.0, LengthUnit.FEET);

        assertThrows(ArithmeticException.class,
                () -> q1.divide(q2));
    }

    // IMMUTABILITY TESTS

    @Test
    void testImmutability_AfterAdd() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(5.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(5.0, LengthUnit.FEET);

        q1.add(q2);

        assertEquals(5.0, q1.getValue());
    }

    @Test
    void testImmutability_AfterSubtract() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(5.0, LengthUnit.FEET);

        q1.subtract(q2);

        assertEquals(10.0, q1.getValue());
    }

    // EQUALITY TEST

    @Test
    void testEquality_CrossUnit() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(12.0, LengthUnit.INCHES);

        assertTrue(q1.equals(q2));
    }
}