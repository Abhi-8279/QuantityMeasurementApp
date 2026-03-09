package com.quantityApp;

public class QuantityMeasurement{

    public static <U extends IMeasurable> void demonstrateEquality(
    		QuantityWeight<U> q1, QuantityWeight<U> q2) {

        System.out.println(q1 + " equals " + q2 + " ? → " + q1.equals(q2));
    }

    public static <U extends IMeasurable> void demonstrateConversion(
    		QuantityWeight<U> q, U target) {

        System.out.println(q + " converted → " + q.convertTo(target));
    }

    public static <U extends IMeasurable> void demonstrateAddition(
    		QuantityWeight<U> q1, QuantityWeight<U> q2, U target) {

        System.out.println(q1 + " + " + q2 + " → " + q1.add(q2, target));
    }
    public static void main(String[] args) {

        System.out.println("===== LENGTH OPERATIONS =====");

        QuantityWeight<LengthUnit> l1 = new QuantityWeight<>(1.0, LengthUnit.FEET);
        QuantityWeight<LengthUnit> l2 = new QuantityWeight<>(12.0, LengthUnit.INCHES);

        demonstrateEquality(l1, l2);
        demonstrateConversion(l1, LengthUnit.INCHES);
        demonstrateAddition(l1, l2, LengthUnit.FEET);

        System.out.println("\n===== WEIGHT OPERATIONS =====");
        QuantityWeight<WeightUnit> w1 = new QuantityWeight<>(1.0, WeightUnit.KILOGRAM);
        QuantityWeight<WeightUnit> w2 = new QuantityWeight<>(1000.0, WeightUnit.GRAM);

        demonstrateEquality(w1, w2);
        demonstrateConversion(w1, WeightUnit.GRAM);
        demonstrateAddition(w1, w2, WeightUnit.KILOGRAM);
    }
}