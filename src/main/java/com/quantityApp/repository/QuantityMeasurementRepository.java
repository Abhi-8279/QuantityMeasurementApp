package com.quantityApp.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quantityApp.model.OperationType;
import com.quantityApp.model.QuantityMeasurementEntity;

@Repository
public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {

    List<QuantityMeasurementEntity> findByOperationOrderByCreatedAtDesc(OperationType operation);

    List<QuantityMeasurementEntity> findByThisMeasurementTypeIgnoreCaseOrderByCreatedAtDesc(String measurementType);

    List<QuantityMeasurementEntity> findByCreatedAtAfterOrderByCreatedAtDesc(LocalDateTime createdAt);

    long countByOperationAndErrorFalse(OperationType operation);

    List<QuantityMeasurementEntity> findByErrorTrueOrderByCreatedAtDesc();
}
