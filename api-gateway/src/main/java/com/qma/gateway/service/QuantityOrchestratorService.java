package com.qma.gateway.service;

import com.qma.gateway.client.AuthServiceClient;
import com.qma.gateway.client.HistoryServiceClient;
import com.qma.gateway.client.MeasurementServiceClient;
import com.qma.gateway.dto.CalculationResultDTO;
import com.qma.gateway.dto.HistorySaveRequest;
import com.qma.gateway.dto.QuantityInputDTO;
import com.qma.gateway.dto.QuantityMeasurementEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityOrchestratorService {

    private final AuthServiceClient authServiceClient;
    private final MeasurementServiceClient measurementServiceClient;
    private final HistoryServiceClient historyServiceClient;

    public QuantityOrchestratorService(
            AuthServiceClient authServiceClient,
            MeasurementServiceClient measurementServiceClient,
            HistoryServiceClient historyServiceClient) {

        this.authServiceClient = authServiceClient;
        this.measurementServiceClient = measurementServiceClient;
        this.historyServiceClient = historyServiceClient;
    }

    public QuantityMeasurementEntity runOperation(String operation, QuantityInputDTO input, String email) {
        Long userId = authServiceClient.getUserIdByEmail(email);
        CalculationResultDTO result = measurementServiceClient.calculate(operation, input);

        HistorySaveRequest request = toHistoryRequest(userId, operation, input, result);
        return historyServiceClient.create(request);
    }

    public QuantityMeasurementEntity update(Long id, QuantityInputDTO input, String email) {
        Long userId = authServiceClient.getUserIdByEmail(email);
        QuantityMeasurementEntity existing = historyServiceClient.getById(id, userId);

        CalculationResultDTO result = measurementServiceClient.calculate(existing.getOperation(), input);
        HistorySaveRequest request = toHistoryRequest(userId, existing.getOperation(), input, result);

        return historyServiceClient.update(id, userId, request);
    }

    public List<QuantityMeasurementEntity> getFiltered(String email, String operation, String type) {
        Long userId = authServiceClient.getUserIdByEmail(email);
        return historyServiceClient.find(userId, operation, type);
    }

    public List<QuantityMeasurementEntity> getHistoryByOperation(String email, String operation) {
        Long userId = authServiceClient.getUserIdByEmail(email);
        return historyServiceClient.findByOperation(operation, userId);
    }

    public long getOperationCount(String email, String operation) {
        Long userId = authServiceClient.getUserIdByEmail(email);
        return historyServiceClient.countByOperation(operation, userId);
    }

    public QuantityMeasurementEntity getById(String email, Long id) {
        Long userId = authServiceClient.getUserIdByEmail(email);
        return historyServiceClient.getById(id, userId);
    }

    public void deleteById(String email, Long id) {
        Long userId = authServiceClient.getUserIdByEmail(email);
        historyServiceClient.deleteById(id, userId);
    }

    public void deleteAll(String email) {
        Long userId = authServiceClient.getUserIdByEmail(email);
        historyServiceClient.deleteAll(userId);
    }

    public void deleteFiltered(String email, String operation, String type) {
        Long userId = authServiceClient.getUserIdByEmail(email);
        historyServiceClient.deleteFiltered(userId, operation, type);
    }

    private HistorySaveRequest toHistoryRequest(
            Long userId,
            String operation,
            QuantityInputDTO input,
            CalculationResultDTO result) {

        HistorySaveRequest request = new HistorySaveRequest();
        request.setUserId(userId);
        request.setOperation(operation.toUpperCase());

        request.setThisValue(input.getThisQuantityDTO().getValue());
        request.setThisUnit(input.getThisQuantityDTO().getUnit());
        request.setThisMeasurementType(input.getThisQuantityDTO().getMeasurementType());

        if (input.getThatQuantityDTO() != null) {
            request.setThatValue(input.getThatQuantityDTO().getValue());
            request.setThatUnit(input.getThatQuantityDTO().getUnit());
            request.setThatMeasurementType(input.getThatQuantityDTO().getMeasurementType());
        }

        request.setResultValue(result.getResultValue());
        request.setResultUnit(result.getResultUnit());
        return request;
    }
}