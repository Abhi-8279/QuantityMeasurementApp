package com.quantityApp.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quantityApp.dto.QuantityInputDTO;
import com.quantityApp.model.QuantityMeasurementDTO;
import com.quantityApp.service.IQuantityMeasurementService;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping(value = "/api/v1/quantities", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    @PostMapping(value = "/compare", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public QuantityMeasurementDTO compare(@Valid @RequestBody QuantityInputDTO input) {
        return service.compare(input);
    }

    @PostMapping(value = "/convert", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public QuantityMeasurementDTO convert(@Valid @RequestBody QuantityInputDTO input) {
        return service.convert(input);
    }

    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public QuantityMeasurementDTO add(@Valid @RequestBody QuantityInputDTO input) {
        return service.add(input);
    }

    @PostMapping(value = "/subtract", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public QuantityMeasurementDTO subtract(@Valid @RequestBody QuantityInputDTO input) {
        return service.subtract(input);
    }

    @PostMapping(value = "/multiply", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public QuantityMeasurementDTO multiply(@Valid @RequestBody QuantityInputDTO input) {
        return service.multiply(input);
    }

    @PostMapping(value = "/divide", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public QuantityMeasurementDTO divide(@Valid @RequestBody QuantityInputDTO input) {
        return service.divide(input);
    }

    @GetMapping("/history/operation/{operation}")
    public List<QuantityMeasurementDTO> getHistoryByOperation(@PathVariable String operation) {
        return service.getHistoryByOperation(operation);
    }

    @GetMapping("/history/type/{measurementType}")
    public List<QuantityMeasurementDTO> getHistoryByMeasurementType(@PathVariable String measurementType) {
        return service.getHistoryByMeasurementType(measurementType);
    }

    @GetMapping("/history/errored")
    public List<QuantityMeasurementDTO> getErrorHistory() {
        return service.getErrorHistory();
    }

    @GetMapping("/count/{operation}")
    public long getOperationCount(@PathVariable String operation) {
        return service.getOperationCount(operation);
    }
}
