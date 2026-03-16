package com.quantityApp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class QuantityTest {

    // ---------- SUBTRACTION TESTS ----------

    @Test
    void testSubtraction_SameUnit_FeetMinusFeet() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(5.0, LengthUnit.FEET);

        Quantity<LengthUnit> result = a.subtract(b);

        assertEquals(5.0, result.getValue());
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testSubtraction_SameUnit_LitreMinusLitre() {
        Quantity<VolumeUnit> a = new Quantity<>(10.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> b = new Quantity<>(3.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result = a.subtract(b);

        assertEquals(7.0, result.getValue());
    }

    @Test
    void testSubtraction_CrossUnit_FeetMinusInches() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(6.0, LengthUnit.INCHES);

        Quantity<LengthUnit> result = a.subtract(b);

        assertEquals(9.5, result.getValue());
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testSubtraction_CrossUnit_InchesMinusFeet() {
        Quantity<LengthUnit> a = new Quantity<>(120.0, LengthUnit.INCHES);
        Quantity<LengthUnit> b = new Quantity<>(5.0, LengthUnit.FEET);

        Quantity<LengthUnit> result = a.subtract(b);

        assertEquals(60.0, result.getValue());
        assertEquals(LengthUnit.INCHES, result.getUnit());
    }

    @Test
    void testSubtraction_ResultingInNegative() {
        Quantity<LengthUnit> a = new Quantity<>(5.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> result = a.subtract(b);

        assertEquals(-5.0, result.getValue());
    }

    @Test
    void testSubtraction_ResultingInZero() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(120.0, LengthUnit.INCHES);

        Quantity<LengthUnit> result = a.subtract(b);

        assertEquals(0.0, result.getValue());
    }

    @Test
    void testSubtraction_WithZeroOperand() {
        Quantity<LengthUnit> a = new Quantity<>(5.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(0.0, LengthUnit.INCHES);

        Quantity<LengthUnit> result = a.subtract(b);

        assertEquals(5.0, result.getValue());
    }

    @Test
    void testSubtraction_NullOperand() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class, () -> a.subtract(null));
    }


    // ---------- DIVISION TESTS ----------

    @Test
    void testDivision_SameUnit_FeetDividedByFeet() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(2.0, LengthUnit.FEET);

        double result = a.divide(b);

        assertEquals(5.0, result);
    }

    @Test
    void testDivision_SameUnit_LitreDividedByLitre() {
        Quantity<VolumeUnit> a = new Quantity<>(10.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> b = new Quantity<>(5.0, VolumeUnit.LITRE);

        double result = a.divide(b);

        assertEquals(2.0, result);
    }

    @Test
    void testDivision_CrossUnit_FeetDividedByInches() {
        Quantity<LengthUnit> a = new Quantity<>(24.0, LengthUnit.INCHES);
        Quantity<LengthUnit> b = new Quantity<>(2.0, LengthUnit.FEET);

        double result = a.divide(b);

        assertEquals(1.0, result);
    }

    @Test
    void testDivision_RatioLessThanOne() {
        Quantity<LengthUnit> a = new Quantity<>(5.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(10.0, LengthUnit.FEET);

        double result = a.divide(b);

        assertEquals(0.5, result);
    }

    @Test
    void testDivision_RatioEqualToOne() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(10.0, LengthUnit.FEET);

        double result = a.divide(b);

        assertEquals(1.0, result);
    }

    @Test
    void testDivision_ByZero() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(0.0, LengthUnit.FEET);

        assertThrows(ArithmeticException.class, () -> a.divide(b));
    }

    @Test
    void testDivision_NullOperand() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class, () -> a.divide(null));
    }

}