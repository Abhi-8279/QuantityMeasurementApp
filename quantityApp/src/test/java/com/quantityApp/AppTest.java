package com.quantityApp;

import org.junit.jupiter.api.Test;

import com.quantityApp.App.LengthUnit;
import com.quantityApp.App.QuantityLength;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    private static final double EPSILON = 1e-6;

    // 1Explicit Target Unit: FEET
    @Test
    void testAddition_ExplicitTargetUnit_Feet() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        QuantityLength result = q1.add(q2, LengthUnit.FEET);

        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    // Explicit Target Unit: INCHES
    @Test
    void testAddition_ExplicitTargetUnit_Inches() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        QuantityLength result = q1.add(q2, LengthUnit.INCHES);

        assertEquals(24.0, result.getValue(), EPSILON);
        assertEquals(LengthUnit.INCHES, result.getUnit());
    }

    //  Explicit Target Unit: YARDS
    @Test
    void testAddition_ExplicitTargetUnit_Yards() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        QuantityLength result = q1.add(q2, LengthUnit.YARDS);

        assertEquals(0.6666667, result.getValue(), EPSILON);
        assertEquals(LengthUnit.YARDS, result.getUnit());
    }

    //  Explicit Target Unit: CENTIMETERS
    @Test
    void testAddition_ExplicitTargetUnit_Centimeters() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.INCHES);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.INCHES);

        QuantityLength result = q1.add(q2, LengthUnit.CENTIMETERS);

        assertEquals(5.08, result.getValue(), 0.01);
        assertEquals(LengthUnit.CENTIMETERS, result.getUnit());
    }

    //  Target Same As First Operand
    @Test
    void testAddition_ExplicitTargetUnit_SameAsFirstOperand() {
        QuantityLength q1 = new QuantityLength(2.0, LengthUnit.YARDS);
        QuantityLength q2 = new QuantityLength(3.0, LengthUnit.FEET);

        QuantityLength result = q1.add(q2, LengthUnit.YARDS);

        assertEquals(3.0, result.getValue(), EPSILON);
        assertEquals(LengthUnit.YARDS, result.getUnit());
    }

    //  Target Same As Second Operand
    @Test
    void testAddition_ExplicitTargetUnit_SameAsSecondOperand() {
        QuantityLength q1 = new QuantityLength(2.0, LengthUnit.YARDS);
        QuantityLength q2 = new QuantityLength(3.0, LengthUnit.FEET);

        QuantityLength result = q1.add(q2, LengthUnit.FEET);

        assertEquals(9.0, result.getValue(), EPSILON);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    //  Commutativity
    @Test
    void testAddition_ExplicitTargetUnit_Commutativity() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        QuantityLength result1 = q1.add(q2, LengthUnit.YARDS);
        QuantityLength result2 = q2.add(q1, LengthUnit.YARDS);

        assertEquals(result1.getValue(), result2.getValue(), EPSILON);
        assertEquals(result1.getUnit(), result2.getUnit());
    }

    //  With Zero
    @Test
    void testAddition_ExplicitTargetUnit_WithZero() {
        QuantityLength q1 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(0.0, LengthUnit.INCHES);

        QuantityLength result = q1.add(q2, LengthUnit.YARDS);

        assertEquals(1.6666667, result.getValue(), EPSILON);
        assertEquals(LengthUnit.YARDS, result.getUnit());
    }

    //  Negative Values
    @Test
    void testAddition_ExplicitTargetUnit_NegativeValues() {
        QuantityLength q1 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(-2.0, LengthUnit.FEET);

        QuantityLength result = q1.add(q2, LengthUnit.INCHES);

        assertEquals(36.0, result.getValue(), EPSILON);
        assertEquals(LengthUnit.INCHES, result.getUnit());
    }

    //  Null Target Unit
    @Test
    void testAddition_ExplicitTargetUnit_NullTargetUnit() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        assertThrows(IllegalArgumentException.class, () ->
                q1.add(q2, null));
    }

    //  Large to Small Scale
    @Test
    void testAddition_ExplicitTargetUnit_LargeToSmallScale() {
        QuantityLength q1 = new QuantityLength(1000.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(500.0, LengthUnit.FEET);

        QuantityLength result = q1.add(q2, LengthUnit.INCHES);

        assertEquals(18000.0, result.getValue(), EPSILON);
        assertEquals(LengthUnit.INCHES, result.getUnit());
    }

    // Small to Large Scale
    @Test
    void testAddition_ExplicitTargetUnit_SmallToLargeScale() {
        QuantityLength q1 = new QuantityLength(12.0, LengthUnit.INCHES);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        QuantityLength result = q1.add(q2, LengthUnit.YARDS);

        assertEquals(0.6666667, result.getValue(), EPSILON);
        assertEquals(LengthUnit.YARDS, result.getUnit());
    }

    //  All Unit Combinations (Representative Coverage)
    @Test
    void testAddition_ExplicitTargetUnit_AllUnitCombinations() {

        for (LengthUnit u1 : LengthUnit.values()) {
            for (LengthUnit u2 : LengthUnit.values()) {
                for (LengthUnit target : LengthUnit.values()) {

                    QuantityLength q1 = new QuantityLength(1.0, u1);
                    QuantityLength q2 = new QuantityLength(2.0, u2);

                    QuantityLength result = q1.add(q2, target);

                    assertEquals(target, result.getUnit());
                }
            }
        }
    }

    // Precision Tolerance
    @Test
    void testAddition_ExplicitTargetUnit_PrecisionTolerance() {

        QuantityLength q1 = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.INCHES);

        QuantityLength result = q1.add(q2, LengthUnit.CENTIMETERS);

        assertEquals(5.08, result.getValue(), 0.01);
    }
}