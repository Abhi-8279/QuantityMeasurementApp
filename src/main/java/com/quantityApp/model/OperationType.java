package com.quantityApp.model;

public enum OperationType {
    COMPARE,
    CONVERT,
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE;

    public static OperationType from(String value) {
        return OperationType.valueOf(value.trim().toUpperCase());
    }
}
