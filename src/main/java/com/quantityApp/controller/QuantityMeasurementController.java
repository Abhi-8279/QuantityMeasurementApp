package com.quantityApp.controller;

import com.quantityApp.dto.QuantityDTO;
import com.quantityApp.service.IQuantityMeasurementService;

public class QuantityMeasurementController {

    private IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    public void performAddition(QuantityDTO q1, QuantityDTO q2) {

        QuantityDTO result = service.add(q1, q2);

        System.out.println("Addition Result: " + result.getValue() + " " + result.getUnit());
    }

    public void performComparison(QuantityDTO q1, QuantityDTO q2) {

        boolean result = service.compare(q1, q2);

        System.out.println("Are equal: " + result);
    }
}