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

    public void run() {

        System.out.println("Starting Quantity Measurement Application...");

        // Length comparison example
        QuantityDTO inch = new QuantityDTO(12.0, "INCHES", "LENGTH");
        QuantityDTO foot = new QuantityDTO(1.0, "FEET", "LENGTH");

        performComparison(inch, foot);

        // Length addition example
        QuantityDTO foot1 = new QuantityDTO(1, "FEET", "LENGTH");
        QuantityDTO foot2 = new QuantityDTO(2, "FEET", "LENGTH");

        performAddition(foot1, foot2);

        // Weight comparison example
        QuantityDTO gram = new QuantityDTO(1000, "GRAM", "WEIGHT");
        QuantityDTO kg = new QuantityDTO(1, "KILOGRAM", "WEIGHT");

        performComparison(gram, kg);

        System.out.println("Application execution completed.");
    }
}