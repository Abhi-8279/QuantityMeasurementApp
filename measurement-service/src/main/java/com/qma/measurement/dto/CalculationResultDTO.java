package com.qma.measurement.dto;

public class CalculationResultDTO {
    private Double resultValue;
    private String resultUnit;

    public CalculationResultDTO() {
    }

    public CalculationResultDTO(Double resultValue, String resultUnit) {
        this.resultValue = resultValue;
        this.resultUnit = resultUnit;
    }

    public Double getResultValue() {
        return resultValue;
    }

    public void setResultValue(Double resultValue) {
        this.resultValue = resultValue;
    }

    public String getResultUnit() {
        return resultUnit;
    }

    public void setResultUnit(String resultUnit) {
        this.resultUnit = resultUnit;
    }
}