package com.quantityApp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class QuantityTest {

    // ---------- LENGTH TESTS ----------

    @Test
    void testLengthEquality_FeetToInches() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCHES);
        assertTrue(q1.equals(q2));
    }

    @Test
    void testLengthEquality_YardToFeet() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.YARDS);
        Quantity<LengthUnit> q2 = new Quantity<>(3.0, LengthUnit.FEET);
        assertTrue(q1.equals(q2));
    }

    @Test
    void testLengthSubtraction_SameUnit() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(5.0, LengthUnit.FEET);
        assertEquals(new Quantity<>(5.0, LengthUnit.FEET), q1.subtract(q2));
    }

    @Test
    void testLengthSubtraction_CrossUnit() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(6.0, LengthUnit.INCHES);
        assertEquals(new Quantity<>(9.5, LengthUnit.FEET), q1.subtract(q2));
    }

    @Test
    void testLengthSubtraction_NegativeResult() {
        Quantity<LengthUnit> q1 = new Quantity<>(5.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(10.0, LengthUnit.FEET);
        assertEquals(new Quantity<>(-5.0, LengthUnit.FEET), q1.subtract(q2));
    }

   

    // ---------- WEIGHT TESTS ----------

    @Test
    void testWeightEquality_KgToGram() {
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);
        assertTrue(w1.equals(w2));
    }

    @Test
    void testWeightSubtraction_KgMinusGram() {
        Quantity<WeightUnit> w1 = new Quantity<>(2.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(500.0, WeightUnit.GRAM);
        assertEquals(new Quantity<>(1.5, WeightUnit.KILOGRAM), w1.subtract(w2));
    }

    @Test
    void testWeightSubtraction_SameUnit() {
        Quantity<WeightUnit> w1 = new Quantity<>(10.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(4.0, WeightUnit.KILOGRAM);
        assertEquals(new Quantity<>(6.0, WeightUnit.KILOGRAM), w1.subtract(w2));
    }

    

    // ---------- VOLUME TESTS ----------

    @Test
    void testVolumeEquality_LitreToMillilitre() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        assertTrue(v1.equals(v2));
    }

    @Test
    void testVolumeSubtraction_LitreMinusML() {
        Quantity<VolumeUnit> v1 = new Quantity<>(5.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);
        assertEquals(new Quantity<>(4.5, VolumeUnit.LITRE), v1.subtract(v2));
    }

    @Test
    void testVolumeSubtraction_SameUnit() {
        Quantity<VolumeUnit> v1 = new Quantity<>(8.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(3.0, VolumeUnit.LITRE);
        assertEquals(new Quantity<>(5.0, VolumeUnit.LITRE), v1.subtract(v2));
    }

   
   

   

    @Test
    void testTemperatureConversion() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> result = t1.convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT), result);
    }

    @Test
    void testTemperatureAdditionNotSupported() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(30.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(20.0, TemperatureUnit.CELSIUS);

        assertThrows(UnsupportedOperationException.class, () -> t1.add(t2));
    }

    // ---------- EDGE CASE TESTS ----------

    @Test
    void testZeroSubtraction() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(0.0, LengthUnit.FEET);
        assertEquals(new Quantity<>(10.0, LengthUnit.FEET), q1.subtract(q2));
    }

    
  
   

  

    
   

    // ---------- MATHEMATICAL PROPERTIES ----------

    @Test
    void testSubtractionNonCommutative() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(5.0, LengthUnit.FEET);

        assertNotEquals(a.subtract(b), b.subtract(a));
    }

    @Test
    void testDivisionNonCommutative() {
        Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(5.0, LengthUnit.FEET);

        assertNotEquals(a.divide(b), b.divide(a));
    }

    @Test
    void testLargeValues() {
        Quantity<WeightUnit> w1 = new Quantity<>(1000000.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(500000.0, WeightUnit.KILOGRAM);

        assertEquals(new Quantity<>(500000.0, WeightUnit.KILOGRAM), w1.subtract(w2));
    }

    @Test
    void testSmallValues() {
        Quantity<LengthUnit> q1 = new Quantity<>(0.001, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(0.0005, LengthUnit.FEET);

        assertEquals(new Quantity<>(0.0005, LengthUnit.FEET), q1.subtract(q2));
    }

}