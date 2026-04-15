package com.qma.measurement.controller;

import com.qma.measurement.dto.CalculationResultDTO;
import com.qma.measurement.dto.QuantityInputDTO;
import com.qma.measurement.service.MeasurementCalculatorService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/measurements")
public class MeasurementController {

    private final MeasurementCalculatorService measurementCalculatorService;

    public MeasurementController(MeasurementCalculatorService measurementCalculatorService) {
        this.measurementCalculatorService = measurementCalculatorService;
    }

    @PostMapping("/{operation}")
    public CalculationResultDTO calculate(
            @PathVariable("operation") String operation,
            @RequestBody QuantityInputDTO input) {

        return measurementCalculatorService.calculate(operation, input);
    }
}
