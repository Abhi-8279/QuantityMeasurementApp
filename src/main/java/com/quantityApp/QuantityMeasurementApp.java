package com.quantityApp;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        // Length
        Quantity<LengthUnit> q1 =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(6.0, LengthUnit.INCHES);

        System.out.println(q1.subtract(q2));

        // Volume
        Quantity<VolumeUnit> v1 =
                new Quantity<>(5.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> v2 =
                new Quantity<>(500.0, VolumeUnit.MILLILITRE);

        System.out.println(v1.subtract(v2));

        // Temperature equality
        Quantity<TemperatureUnit> t1 =
                new Quantity<>(0.0, TemperatureUnit.CELSIUS);

        Quantity<TemperatureUnit> t2 =
                new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);

        System.out.println("Temperature equal: " + t1.equals(t2));

        // Temperature conversion
        System.out.println(
                new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                        .convertTo(TemperatureUnit.FAHRENHEIT)
        );

        // Unsupported operation demo
        try {

            Quantity<TemperatureUnit> a =
                    new Quantity<>(100, TemperatureUnit.CELSIUS);

            Quantity<TemperatureUnit> b =
                    new Quantity<>(50, TemperatureUnit.CELSIUS);

            System.out.println(a.add(b));

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }
}