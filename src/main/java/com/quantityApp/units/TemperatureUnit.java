package com.quantityApp.units;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS {
        @Override
        public double toBaseUnit(double value) {
            return value;
        }

        @Override
        public double fromBaseUnit(double value) {
            return value;
        }
    },

    FAHRENHEIT {
        @Override
        public double toBaseUnit(double value) {
            return (value - 32) * 5 / 9;
        }

        @Override
        public double fromBaseUnit(double value) {
            return (value * 9 / 5) + 32;
        }
    },

    KELVIN {
        @Override
        public double toBaseUnit(double value) {
            return value - 273.15;
        }

        @Override
        public double fromBaseUnit(double value) {
            return value + 273.15;
        }
    };

    @Override
    public double getConversionFactor() {
        return 1.0;
    }

    @Override
    public String getUnitName() {
        return name();
    }
}
