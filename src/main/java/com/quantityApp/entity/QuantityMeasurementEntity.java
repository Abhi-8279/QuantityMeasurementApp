package com.quantityApp.entity;

import java.io.Serializable;

public class QuantityMeasurementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String operation;
    private String measurementType;

    private String unit1;
    private String unit2;

    private double value1;
    private double value2;

    private boolean result;
    private boolean error;
    private String message;

    public QuantityMeasurementEntity() {}

    public QuantityMeasurementEntity(String operation,
                                     String measurementType,
                                     String unit1,
                                     double value1,
                                     String unit2,
                                     double value2,
                                     boolean result) {

        this.operation = operation;
        this.measurementType = measurementType;
        this.unit1 = unit1;
        this.value1 = value1;
        this.unit2 = unit2;
        this.value2 = value2;
        this.result = result;
        this.error = false;
    }

    public QuantityMeasurementEntity(String message) {
        this.error = true;
        this.message = message;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }

    public String getMeasurementType() { return measurementType; }
    public void setMeasurementType(String measurementType) { this.measurementType = measurementType; }

    public String getUnit1() { return unit1; }
    public void setUnit1(String unit1) { this.unit1 = unit1; }

    public String getUnit2() { return unit2; }
    public void setUnit2(String unit2) { this.unit2 = unit2; }

    public double getValue1() { return value1; }
    public void setValue1(double value1) { this.value1 = value1; }

    public double getValue2() { return value2; }
    public void setValue2(double value2) { this.value2 = value2; }

    public boolean getResult() { return result; }
    public void setResult(boolean result) { this.result = result; }

    public boolean hasError() { return error; }

    public String getMessage() { return message; }
}