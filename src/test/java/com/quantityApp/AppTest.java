package com.quantityApp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    private static final double EPS = 1e-6;

    @Test
    void shouldCreateValidWeight() {
        QuantityWeight q = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        assertEquals(1.0, q.getValue());
        assertEquals(WeightUnit.KILOGRAM, q.getUnit());
    }

    @Test
    void shouldThrowIfUnitNull() {
        assertThrows(IllegalArgumentException.class,() -> new QuantityWeight(1.0, null));
    }

    @Test
    void shouldThrowIfValueNaN() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuantityWeight(Double.NaN, WeightUnit.KILOGRAM));
    }

    @Test
    void shouldThrowIfValueInfinite() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuantityWeight(Double.POSITIVE_INFINITY, WeightUnit.KILOGRAM));
    }

    @Test
    void constructorAcceptsNegative() {
        QuantityWeight q = new QuantityWeight(-5.0, WeightUnit.GRAM);
        assertEquals(-5.0, q.getValue());
    }


    @Test
    void kilogramEquality() {
        assertTrue(new QuantityWeight(2.0, WeightUnit.KILOGRAM)
                .equals(new QuantityWeight(2.0, WeightUnit.KILOGRAM)));
    }

    @Test
    void gramEquality() {
        assertTrue(new QuantityWeight(500.0, WeightUnit.GRAM)
                .equals(new QuantityWeight(500.0, WeightUnit.GRAM)));
    }

    @Test
    void poundEquality() {
        assertTrue(new QuantityWeight(3.0, WeightUnit.POUND)
                .equals(new QuantityWeight(3.0, WeightUnit.POUND)));
    }

    @Test
    void inequalitySameUnit() {
        assertFalse(new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                .equals(new QuantityWeight(2.0, WeightUnit.KILOGRAM)));
    }

    @Test
    void kilogramEqualsGram() {
        assertTrue(new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                .equals(new QuantityWeight(1000.0, WeightUnit.GRAM)));
    }

    @Test
    void gramEqualsKilogram() {
        assertTrue(new QuantityWeight(1000.0, WeightUnit.GRAM)
                .equals(new QuantityWeight(1.0, WeightUnit.KILOGRAM)));
    }



    @Test
    void gramEqualsPound() {
        assertTrue(new QuantityWeight(453.592, WeightUnit.GRAM)
                .equals(new QuantityWeight(1.0, WeightUnit.POUND)));
    }

    @Test
    void zeroAcrossUnits() {
        assertTrue(new QuantityWeight(0.0, WeightUnit.KILOGRAM)
                .equals(new QuantityWeight(0.0, WeightUnit.GRAM)));
    }

    @Test
    void negativeAcrossUnits() {
        assertTrue(new QuantityWeight(-1.0, WeightUnit.KILOGRAM)
                .equals(new QuantityWeight(-1000.0, WeightUnit.GRAM)));
    }

    @Test
    void largeAcrossUnits() {
        assertTrue(new QuantityWeight(1000000.0, WeightUnit.GRAM)
                .equals(new QuantityWeight(1000.0, WeightUnit.KILOGRAM)));
    }

    @Test
    void smallAcrossUnits() {
        assertTrue(new QuantityWeight(0.001, WeightUnit.KILOGRAM)
                .equals(new QuantityWeight(1.0, WeightUnit.GRAM)));
    }

    //  Equality Contract 

    @Test
    void reflexive() {
        QuantityWeight q = new QuantityWeight(5.0, WeightUnit.KILOGRAM);
        assertTrue(q.equals(q));
    }

    @Test
    void symmetric() {
        QuantityWeight a = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight b = new QuantityWeight(1000.0, WeightUnit.GRAM);
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
    }



    @Test
    void equalsNull() {
        assertFalse(new QuantityWeight(1.0, WeightUnit.KILOGRAM).equals(null));
    }

    @Test
    void equalsDifferentType() {
        assertFalse(new QuantityWeight(1.0, WeightUnit.KILOGRAM).equals("Test"));
    }


    @Test
    void kilogramToGram() {
        assertEquals(1000.0,
                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                        .convertTo(WeightUnit.GRAM).getValue(), EPS);
    }

    @Test
    void gramToKilogram() {
        assertEquals(1.0,
                new QuantityWeight(1000.0, WeightUnit.GRAM)
                        .convertTo(WeightUnit.KILOGRAM).getValue(), EPS);
    }

 

    @Test
    void poundToKilogram() {
        assertEquals(1.0,
                new QuantityWeight(2.20462, WeightUnit.POUND)
                        .convertTo(WeightUnit.KILOGRAM).getValue(), 0.001);
    }

    @Test
    void gramToPound() {
        assertEquals(1.0,
                new QuantityWeight(453.592, WeightUnit.GRAM)
                        .convertTo(WeightUnit.POUND).getValue(), 0.01);
    }

    @Test
    void sameUnitConversion() {
        assertEquals(5.0,
                new QuantityWeight(5.0, WeightUnit.KILOGRAM)
                        .convertTo(WeightUnit.KILOGRAM).getValue());
    }

    @Test
    void roundTripConversion() {
        QuantityWeight q = new QuantityWeight(1.5, WeightUnit.KILOGRAM);
        assertEquals(1.5,
                q.convertTo(WeightUnit.GRAM)
                        .convertTo(WeightUnit.KILOGRAM).getValue(), EPS);
    }

    @Test
    void convertZero() {
        assertEquals(0.0,
                new QuantityWeight(0.0, WeightUnit.KILOGRAM)
                        .convertTo(WeightUnit.GRAM).getValue());
    }


    @Test
    void addSameUnit() {
        assertEquals(3.0,
                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                        .add(new QuantityWeight(2.0, WeightUnit.KILOGRAM)).getValue(), EPS);
    }

    @Test
    void addCrossUnitImplicit() {
        assertEquals(2.0,
                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                        .add(new QuantityWeight(1000.0, WeightUnit.GRAM)).getValue(), EPS);
    }

    @Test
    void addImperialMetric() {
        assertEquals(4.40924,
                new QuantityWeight(2.20462, WeightUnit.POUND)
                        .add(new QuantityWeight(1.0, WeightUnit.KILOGRAM)).getValue(), 0.01);
    }

    @Test
    void addExplicitTarget() {
        assertEquals(2000.0,
                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                        .add(new QuantityWeight(1000.0, WeightUnit.GRAM),
                                WeightUnit.GRAM).getValue(), EPS);
    }

    @Test
    void commutativeAddition() {
        QuantityWeight a = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight b = new QuantityWeight(1000.0, WeightUnit.GRAM);
        assertTrue(a.add(b).equals(b.add(a)));
    }

    @Test
    void addZero() {
        assertEquals(5.0,
                new QuantityWeight(5.0, WeightUnit.KILOGRAM)
                        .add(new QuantityWeight(0.0, WeightUnit.GRAM)).getValue(), EPS);
    }

    @Test
    void addNegative() {
        assertEquals(3.0,
                new QuantityWeight(5.0, WeightUnit.KILOGRAM)
                        .add(new QuantityWeight(-2000.0, WeightUnit.GRAM)).getValue(), EPS);
    }

    @Test
    void addLargeValues() {
        assertEquals(2e6,
                new QuantityWeight(1e6, WeightUnit.KILOGRAM)
                        .add(new QuantityWeight(1e6, WeightUnit.KILOGRAM)).getValue(), EPS);
    }

    @Test
    void addNullShouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuantityWeight(1.0, WeightUnit.KILOGRAM).add(null));
    }

    @Test
    void addNullTargetShouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                        .add(new QuantityWeight(1.0, WeightUnit.KILOGRAM), null));
    }

   

    @Test
    void equalObjectsSameHashCode() {
        QuantityWeight a = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight b = new QuantityWeight(1000.0, WeightUnit.GRAM);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void hashCodeConsistency() {
        QuantityWeight a = new QuantityWeight(2.0, WeightUnit.KILOGRAM);
        int h1 = a.hashCode();
        int h2 = a.hashCode();
        assertEquals(h1, h2);
    }
}