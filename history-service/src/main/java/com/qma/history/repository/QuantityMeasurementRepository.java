package com.qma.history.repository;

import com.qma.history.entity.QuantityMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {

    List<QuantityMeasurementEntity> findByUserId(Long userId);

    List<QuantityMeasurementEntity> findByOperationAndUserId(String operation, Long userId);

    List<QuantityMeasurementEntity> findByThisMeasurementTypeAndUserId(String type, Long userId);

    List<QuantityMeasurementEntity> findByOperationAndThisMeasurementTypeAndUserId(String operation, String type, Long userId);

    long countByOperationAndUserId(String operation, Long userId);

    void deleteByUserId(Long userId);

    void deleteByOperationAndUserId(String operation, Long userId);

    void deleteByThisMeasurementTypeAndUserId(String type, Long userId);

    void deleteByOperationAndThisMeasurementTypeAndUserId(String operation, String type, Long userId);

    @Modifying
    @Query("DELETE FROM QuantityMeasurementEntity q WHERE q.id = :id AND q.userId = :userId")
    int deleteByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);
}