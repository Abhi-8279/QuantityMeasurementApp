package com.qma.history.service;

import com.qma.history.dto.HistorySaveRequest;
import com.qma.history.entity.QuantityMeasurementEntity;
import com.qma.history.repository.QuantityMeasurementRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class HistoryService {

    private final QuantityMeasurementRepository repository;

    public HistoryService(QuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    public QuantityMeasurementEntity create(HistorySaveRequest request) {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        apply(request, entity);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return repository.save(entity);
    }

    public QuantityMeasurementEntity update(Long id, Long userId, HistorySaveRequest request) {
        QuantityMeasurementEntity entity = getById(id, userId);
        apply(request, entity);
        entity.setId(id);
        entity.setUserId(userId);
        entity.setUpdatedAt(LocalDateTime.now());
        return repository.save(entity);
    }

    public List<QuantityMeasurementEntity> find(Long userId, String operation, String type) {
        String normalizedOperation = normalize(operation);
        String normalizedType = normalize(type);

        if (normalizedOperation == null && normalizedType == null) {
            return repository.findByUserId(userId);
        }

        if (normalizedOperation != null && normalizedType == null) {
            return repository.findByOperationAndUserId(normalizedOperation, userId);
        }

        if (normalizedOperation == null) {
            return repository.findByThisMeasurementTypeAndUserId(normalizedType, userId);
        }

        return repository.findByOperationAndThisMeasurementTypeAndUserId(normalizedOperation, normalizedType, userId);
    }

    public List<QuantityMeasurementEntity> findByOperation(String operation, Long userId) {
        return repository.findByOperationAndUserId(requireText(operation, "operation is required").toUpperCase(), userId);
    }

    public long countByOperation(String operation, Long userId) {
        return repository.countByOperationAndUserId(requireText(operation, "operation is required").toUpperCase(), userId);
    }

    public QuantityMeasurementEntity getById(Long id, Long userId) {
        return repository.findById(id)
                .filter(item -> item.getUserId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Not found or unauthorized"));
    }

    public void deleteById(Long id, Long userId) {
        int removed = repository.deleteByIdAndUserId(id, userId);
        if (removed == 0) {
            throw new RuntimeException("Not found or unauthorized");
        }
    }

    public void deleteAllByUser(Long userId) {
        repository.deleteByUserId(userId);
    }

    public void deleteFiltered(Long userId, String operation, String type) {
        String normalizedOperation = normalize(operation);
        String normalizedType = normalize(type);

        if (normalizedOperation != null && normalizedType != null) {
            repository.deleteByOperationAndThisMeasurementTypeAndUserId(normalizedOperation, normalizedType, userId);
            return;
        }

        if (normalizedOperation != null) {
            repository.deleteByOperationAndUserId(normalizedOperation, userId);
            return;
        }

        if (normalizedType != null) {
            repository.deleteByThisMeasurementTypeAndUserId(normalizedType, userId);
            return;
        }

        repository.deleteByUserId(userId);
    }

    private void apply(HistorySaveRequest request, QuantityMeasurementEntity entity) {
        entity.setUserId(require(request.getUserId(), "userId is required"));
        entity.setThisValue(request.getThisValue());
        entity.setThisUnit(request.getThisUnit());
        entity.setThisMeasurementType(normalize(requireText(request.getThisMeasurementType(), "thisMeasurementType is required")));
        entity.setThatValue(request.getThatValue());
        entity.setThatUnit(request.getThatUnit());
        entity.setThatMeasurementType(normalize(request.getThatMeasurementType()));
        entity.setOperation(normalize(requireText(request.getOperation(), "operation is required")));
        entity.setResultValue(require(request.getResultValue(), "resultValue is required"));
        entity.setResultUnit(requireText(request.getResultUnit(), "resultUnit is required"));
    }

    private String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim().toUpperCase();
    }

    private String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    private <T> T require(T value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }
}