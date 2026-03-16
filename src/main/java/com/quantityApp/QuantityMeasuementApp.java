package com.quantityApp;

public class QuantityMeasurementApp{
	public static void main(String[] args) {
		Quantity<LengthUnit> q1 =
		        new Quantity<>(10.0, LengthUnit.FEET);

		Quantity<LengthUnit> q2 =
		        new Quantity<>(6.0, LengthUnit.INCHES);

		System.out.println(q1.subtract(q2));

		System.out.println(q1.subtract(q2, LengthUnit.INCHES));
		Quantity<LengthUnit> q3 =
		        new Quantity<>(10.0, LengthUnit.FEET);

		Quantity<LengthUnit> q4 =
		        new Quantity<>(6.0, LengthUnit.INCHES);

		System.out.println(q1.subtract(q2));

		System.out.println(q3.subtract(q4, LengthUnit.INCHES));
		Quantity<VolumeUnit> v1 =
		        new Quantity<>(5.0, VolumeUnit.LITRE);

		Quantity<VolumeUnit> v2 =
		        new Quantity<>(500.0, VolumeUnit.MILLILITRE);

		System.out.println(v1.subtract(v2));

		System.out.println(v1.divide(v2));
	}
}