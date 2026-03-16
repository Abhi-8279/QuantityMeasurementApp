package com.quantityApp;

public class QuantityMeasurementApp{
	public static void main(String[] args) {
		Quantity<LengthUnit> q1 =
		        new Quantity<>(10.0, LengthUnit.FEET);

		Quantity<LengthUnit> q2 =
		        new Quantity<>(6.0, LengthUnit.INCHES);

		System.out.println(q1.subtract(q2));
		System.out.println(q1.add(q2));
		System.out.println(q1.divide(q2));
	}
}