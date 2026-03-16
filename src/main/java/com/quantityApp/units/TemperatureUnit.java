package com.quantityApp.units;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS {
        public double toBaseUnit(double value) {
            return value;
        }

        public double fromBaseUnit(double value) {
            return value;
        }

		@Override
		public double getConversionFactor() {
			// TODO Auto-generated method stub
			return 1.0;
		}

		@Override
		public String getUnitName() {
			// TODO Auto-generated method stub
			return "Celsius";
		}
    },

    FAHRENHEIT {
        public double toBaseUnit(double value) {
            return (value - 32) * 5 / 9;
        }

        public double fromBaseUnit(double value) {
            return (value * 9 / 5) + 32;
        }

		@Override
		public double getConversionFactor() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getUnitName() {
			// TODO Auto-generated method stub
			return "Fahrenheit";
		}
    },

    KELVIN {
        public double toBaseUnit(double value) {
            return value - 273.15;
        }

        public double fromBaseUnit(double value) {
            return value + 273.15;
        }

		@Override
		public double getConversionFactor() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getUnitName() {
			// TODO Auto-generated method stub
			return "Kelvin";
		}
    };

    SupportsArithmetic supportsArithmetic = () -> false;

    @Override
    public boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    @Override
    public void validateOperationSupport(String operation) {
        throw new UnsupportedOperationException(
                "Temperature does not support " + operation + " operation");
    }
}