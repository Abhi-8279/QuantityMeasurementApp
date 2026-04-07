package com.quantityApp.service;

import java.util.List;

import com.quantityApp.dto.QuantityInputDTO;
import com.quantityApp.model.QuantityMeasurementDTO;

public interface IQuantityMeasurementService {

    QuantityMeasurementDTO compare(QuantityInputDTO input);
    QuantityMeasurementDTO convert(QuantityInputDTO input);
    QuantityMeasurementDTO add(QuantityInputDTO input);
    QuantityMeasurementDTO subtract(QuantityInputDTO input);
    QuantityMeasurementDTO multiply(QuantityInputDTO input);
    QuantityMeasurementDTO divide(QuantityInputDTO input);
    List<QuantityMeasurementDTO> getHistoryByOperation(String operation);
    List<QuantityMeasurementDTO> getHistoryByMeasurementType(String measurementType);
    List<QuantityMeasurementDTO> getErrorHistory();
    long getOperationCount(String operation);
}
