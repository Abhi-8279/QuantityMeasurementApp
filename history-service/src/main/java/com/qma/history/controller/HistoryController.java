package com.qma.history.controller;

import com.qma.history.dto.HistorySaveRequest;
import com.qma.history.entity.QuantityMeasurementEntity;
import com.qma.history.service.HistoryService;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/internal/history")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @PostMapping
    public QuantityMeasurementEntity create(@RequestBody HistorySaveRequest request) {
        return historyService.create(request);
    }

    @PutMapping("/{id}")
    public QuantityMeasurementEntity update(
            @PathVariable("id") Long id,
            @RequestParam("userId") Long userId,
            @RequestBody HistorySaveRequest request) {
        return historyService.update(id, userId, request);
    }

    @GetMapping
    public List<QuantityMeasurementEntity> find(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "operation", required = false) String operation,
            @RequestParam(value = "type", required = false) String type) {

        return historyService.find(userId, operation, type);
    }

    @GetMapping("/operation/{operation}")
    public List<QuantityMeasurementEntity> findByOperation(
            @PathVariable("operation") String operation,
            @RequestParam("userId") Long userId) {

        return historyService.findByOperation(operation, userId);
    }

    @GetMapping("/count/{operation}")
    public long countByOperation(
            @PathVariable("operation") String operation,
            @RequestParam("userId") Long userId) {

        return historyService.countByOperation(operation, userId);
    }

    @GetMapping("/{id}")
    public QuantityMeasurementEntity getById(
            @PathVariable("id") Long id,
            @RequestParam("userId") Long userId) {

        return historyService.getById(id, userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(
            @PathVariable("id") Long id,
            @RequestParam("userId") Long userId) {

        historyService.deleteById(id, userId);
        return ResponseEntity.ok(Map.of("message", "deleted"));
    }

    @DeleteMapping("/all")
    public ResponseEntity<Map<String, String>> deleteAll(@RequestParam("userId") Long userId) {
        historyService.deleteAllByUser(userId);
        return ResponseEntity.ok(Map.of("message", "All history deleted successfully"));
    }

    @DeleteMapping("/filtered")
    public ResponseEntity<Map<String, String>> deleteFiltered(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "operation", required = false) String operation,
            @RequestParam(value = "type", required = false) String type) {

        historyService.deleteFiltered(userId, operation, type);
        return ResponseEntity.ok(Map.of("message", "Filtered records deleted successfully"));
    }
}
