package com.quantityApp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VolumeUnitTest {

    private static final double EPS = 1e-6;

    @Test
    void testLitreEqualsLitre() {
        assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE)
                .equals(new Quantity<>(1.0, VolumeUnit.LITRE)));
    }

    @Test
    void testMillilitreEqualsMillilitre() {
        assertTrue(new Quantity<>(500.0, VolumeUnit.MILLILITRE)
                .equals(new Quantity<>(500.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    void testGallonEqualsGallon() {
        assertTrue(new Quantity<>(2.0, VolumeUnit.GALLON)
                .equals(new Quantity<>(2.0, VolumeUnit.GALLON)));
    }

    @Test
    void testDifferentLitreNotEqual() {
        assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE)
                .equals(new Quantity<>(2.0, VolumeUnit.LITRE)));
    }

    // ===============================
    // CROSS UNIT EQUALITY
    // ===============================

    @Test
    void test1LitreEquals1000Millilitre() {
        assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE)
                .equals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    void test1000MillilitreEquals1Litre() {
        assertTrue(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
                .equals(new Quantity<>(1.0, VolumeUnit.LITRE)));
    }

    @Test
    void test1GallonEquals3Point78541Litre() {
        assertTrue(new Quantity<>(1.0, VolumeUnit.GALLON)
                .equals(new Quantity<>(3.78541, VolumeUnit.LITRE)));
    }

    @Test
    void test3Point78541LitreEquals1Gallon() {
        assertTrue(new Quantity<>(3.78541, VolumeUnit.LITRE)
                .equals(new Quantity<>(1.0, VolumeUnit.GALLON)));
    }


    @Test
    void testConvertLitreToMillilitre() {

        Quantity<VolumeUnit> v =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result =
                v.convertTo(VolumeUnit.MILLILITRE);

        assertEquals(1000.0, result.getValue(), EPS);
    }

    @Test
    void testConvertMillilitreToLitre() {

        Quantity<VolumeUnit> v =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> result =
                v.convertTo(VolumeUnit.LITRE);

        assertEquals(1.0, result.getValue(), EPS);
    }

    @Test
    void testConvertLitreToGallon() {

        Quantity<VolumeUnit> v =
                new Quantity<>(3.78541, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result =
                v.convertTo(VolumeUnit.GALLON);

        assertEquals(1.0, result.getValue(), EPS);
    }


    @Test
    void testAddLitrePlusLitre() {

        Quantity<VolumeUnit> v1 =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> v2 =
                new Quantity<>(2.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result = v1.add(v2);

        assertEquals(3.0, result.getValue(), EPS);
    }

    @Test
    void testAddMillilitrePlusMillilitre() {

        Quantity<VolumeUnit> v1 =
                new Quantity<>(500.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> v2 =
                new Quantity<>(500.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> result = v1.add(v2);

        assertEquals(1000.0, result.getValue(), EPS);
    }

    @Test
    void testAddLitrePlusMillilitre() {

        Quantity<VolumeUnit> v1 =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> v2 =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> result = v1.add(v2);

        assertEquals(2.0, result.getValue(), EPS);
    }

    @Test
    void testAddMillilitrePlusLitre() {

        Quantity<VolumeUnit> v1 =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> v2 =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result = v1.add(v2);

        assertEquals(2000.0, result.getValue(), EPS);
    }

  

    @Test
    void testAddWithExplicitTargetUnitMillilitre() {

        Quantity<VolumeUnit> v1 =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> v2 =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> result =
                v1.add(v2, VolumeUnit.MILLILITRE);

        assertEquals(2000.0, result.getValue(), EPS);
    }

    @Test
    void testAddWithExplicitTargetUnitLitre() {

        Quantity<VolumeUnit> v1 =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> v2 =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result =
                v1.add(v2, VolumeUnit.LITRE);

        assertEquals(2.0, result.getValue(), EPS);
    }


    @Test
    void testZeroVolumeEquality() {
        assertTrue(new Quantity<>(0.0, VolumeUnit.LITRE)
                .equals(new Quantity<>(0.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    void testNegativeVolumeEquality() {
        assertTrue(new Quantity<>(-1.0, VolumeUnit.LITRE)
                .equals(new Quantity<>(-1000.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    void testLargeVolumeEquality() {
        assertTrue(new Quantity<>(1000000.0, VolumeUnit.MILLILITRE)
                .equals(new Quantity<>(1000.0, VolumeUnit.LITRE)));
    }

    @Test
    void testVerySmallVolume() {

        Quantity<VolumeUnit> v =
                new Quantity<>(0.001, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result =
                v.convertTo(VolumeUnit.MILLILITRE);

        assertEquals(1.0, result.getValue(), EPS);
    }

}