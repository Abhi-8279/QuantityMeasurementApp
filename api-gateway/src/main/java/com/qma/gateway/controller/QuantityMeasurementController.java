package com.qma.gateway.controller;

import com.qma.gateway.dto.QuantityInputDTO;
import com.qma.gateway.dto.QuantityMeasurementEntity;
import com.qma.gateway.service.QuantityOrchestratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/quantities")
public class QuantityMeasurementController {

    private final QuantityOrchestratorService quantityOrchestratorService;

    public QuantityMeasurementController(QuantityOrchestratorService quantityOrchestratorService) {
        this.quantityOrchestratorService = quantityOrchestratorService;
    }

    @PostMapping("/compare")
    public QuantityMeasurementEntity compare(@RequestBody QuantityInputDTO input) {
        return quantityOrchestratorService.runOperation("COMPARE", input, getCurrentUserEmail());
    }

    @PostMapping("/convert")
    public QuantityMeasurementEntity convert(@RequestBody QuantityInputDTO input) {
        return quantityOrchestratorService.runOperation("CONVERT", input, getCurrentUserEmail());
    }

    @PostMapping("/add")
    public QuantityMeasurementEntity add(@RequestBody QuantityInputDTO input) {
        return quantityOrchestratorService.runOperation("ADD", input, getCurrentUserEmail());
    }

    @PostMapping("/subtract")
    public QuantityMeasurementEntity subtract(@RequestBody QuantityInputDTO input) {
        return quantityOrchestratorService.runOperation("SUBTRACT", input, getCurrentUserEmail());
    }

    @PostMapping("/multiply")
    public QuantityMeasurementEntity multiply(@RequestBody QuantityInputDTO input) {
        return quantityOrchestratorService.runOperation("MULTIPLY", input, getCurrentUserEmail());
    }

    @PostMapping("/divide")
    public QuantityMeasurementEntity divide(@RequestBody QuantityInputDTO input) {
        return quantityOrchestratorService.runOperation("DIVIDE", input, getCurrentUserEmail());
    }

    @GetMapping("/history/operation/{operation}")
    public List<QuantityMeasurementEntity> getHistoryByOperation(@PathVariable("operation") String operation) {
        return quantityOrchestratorService.getHistoryByOperation(getCurrentUserEmail(), operation);
    }

    @GetMapping("/count/{operation}")
    public long getCount(@PathVariable("operation") String operation) {
        return quantityOrchestratorService.getOperationCount(getCurrentUserEmail(), operation);
    }

    @GetMapping("/all")
    public List<QuantityMeasurementEntity> getAll(
            @RequestParam(value = "operation", required = false) String operation,
            @RequestParam(value = "type", required = false) String type) {

        return quantityOrchestratorService.getFiltered(getCurrentUserEmail(), operation, type);
    }

    @GetMapping("/{id}")
    public QuantityMeasurementEntity getById(@PathVariable("id") Long id) {
        return quantityOrchestratorService.getById(getCurrentUserEmail(), id);
    }

    @PutMapping("/update/{id}")
    public QuantityMeasurementEntity update(@PathVariable("id") Long id, @RequestBody QuantityInputDTO input) {
        return quantityOrchestratorService.update(id, input, getCurrentUserEmail());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable("id") Long id) {
        quantityOrchestratorService.deleteById(getCurrentUserEmail(), id);
        return ResponseEntity.ok(Map.of("message", "deleted"));
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<Map<String, String>> deleteAll() {
        quantityOrchestratorService.deleteAll(getCurrentUserEmail());
        return ResponseEntity.ok(Map.of("message", "All history deleted successfully"));
    }

    @DeleteMapping("/delete-filtered")
    public ResponseEntity<Map<String, String>> deleteFiltered(
            @RequestParam(value = "operation", required = false) String operation,
            @RequestParam(value = "type", required = false) String type) {

        quantityOrchestratorService.deleteFiltered(getCurrentUserEmail(), operation, type);
        return ResponseEntity.ok(Map.of("message", "Filtered records deleted successfully"));
    }

    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Unauthorized");
        }
        return authentication.getName();
    }
}
