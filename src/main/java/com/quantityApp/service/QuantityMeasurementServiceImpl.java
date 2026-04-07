package com.quantityApp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.quantityApp.dto.QuantityDTO;
import com.quantityApp.dto.QuantityInputDTO;
import com.quantityApp.exception.QuantityMeasurementException;
import com.quantityApp.model.OperationType;
import com.quantityApp.model.QuantityMeasurementDTO;
import com.quantityApp.model.QuantityMeasurementEntity;
import com.quantityApp.model.QuantityModel;
import com.quantityApp.repository.QuantityMeasurementRepository;
import com.quantityApp.units.ArithmeticOperation;
import com.quantityApp.units.IMeasurable;
import com.quantityApp.units.LengthUnit;
import com.quantityApp.units.TemperatureUnit;
import com.quantityApp.units.VolumeUnit;
import com.quantityApp.units.WeightUnit;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private final QuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    @Override
    public QuantityMeasurementDTO compare(QuantityInputDTO input) {
        QuantityMeasurementEntity entity = createBaseEntity(input, OperationType.COMPARE);
        try {
            QuantityModel<? extends IMeasurable> first = convertDtoToModel(input.getThisQuantityDTO());
            QuantityModel<? extends IMeasurable> second = convertDtoToModel(input.getThatQuantityDTO());
            validateMatchingMeasurementTypes(input);
            boolean isEqual = areEqual(first, second);
            entity.setResultString(Boolean.toString(isEqual));
            entity.setError(false);
            return saveAndMap(entity);
        } catch (RuntimeException ex) {
            throw recordAndRethrow(entity, ex);
        }
    }

    @Override
    public QuantityMeasurementDTO convert(QuantityInputDTO input) {
        QuantityMeasurementEntity entity = createBaseEntity(input, OperationType.CONVERT);
        try {
            validateMatchingMeasurementTypes(input);
            QuantityModel<? extends IMeasurable> source = convertDtoToModel(input.getThisQuantityDTO());
            IMeasurable targetUnit = resolveUnit(input.getThatQuantityDTO().getMeasurementType(), input.getThatQuantityDTO().getUnit());
            double baseValue = source.getUnit().toBaseUnit(source.getValue());
            double convertedValue = targetUnit.fromBaseUnit(baseValue);
            entity.setResultValue(convertedValue);
            entity.setResultUnit(targetUnit.getUnitName());
            entity.setResultMeasurementType(input.getThatQuantityDTO().getMeasurementType());
            entity.setError(false);
            return saveAndMap(entity);
        } catch (RuntimeException ex) {
            throw recordAndRethrow(entity, ex);
        }
    }

    @Override
    public QuantityMeasurementDTO add(QuantityInputDTO input) {
        return applyArithmetic(input, OperationType.ADD, ArithmeticOperation.ADD);
    }

    @Override
    public QuantityMeasurementDTO subtract(QuantityInputDTO input) {
        return applyArithmetic(input, OperationType.SUBTRACT, ArithmeticOperation.SUBTRACT);
    }

    @Override
    public QuantityMeasurementDTO multiply(QuantityInputDTO input) {
        return applyArithmetic(input, OperationType.MULTIPLY, ArithmeticOperation.MULTIPLY);
    }

    @Override
    public QuantityMeasurementDTO divide(QuantityInputDTO input) {
        return applyArithmetic(input, OperationType.DIVIDE, ArithmeticOperation.DIVIDE);
    }

    @Override
    public List<QuantityMeasurementDTO> getHistoryByOperation(String operation) {
        return QuantityMeasurementDTO.fromEntityList(repository.findByOperationOrderByCreatedAtDesc(OperationType.from(operation)));
    }

    @Override
    public List<QuantityMeasurementDTO> getHistoryByMeasurementType(String measurementType) {
        return QuantityMeasurementDTO.fromEntityList(repository.findByThisMeasurementTypeIgnoreCaseOrderByCreatedAtDesc(measurementType));
    }

    @Override
    public List<QuantityMeasurementDTO> getErrorHistory() {
        return QuantityMeasurementDTO.fromEntityList(repository.findByErrorTrueOrderByCreatedAtDesc());
    }

    @Override
    public long getOperationCount(String operation) {
        return repository.countByOperationAndErrorFalse(OperationType.from(operation));
    }

    private QuantityMeasurementDTO applyArithmetic(QuantityInputDTO input, OperationType operationType, ArithmeticOperation arithmeticOperation) {
        QuantityMeasurementEntity entity = createBaseEntity(input, operationType);
        try {
            validateMatchingMeasurementTypes(input);
            QuantityModel<? extends IMeasurable> first = convertDtoToModel(input.getThisQuantityDTO());
            QuantityModel<? extends IMeasurable> second = convertDtoToModel(input.getThatQuantityDTO());
            double firstBase = first.getUnit().toBaseUnit(first.getValue());
            double secondBase = second.getUnit().toBaseUnit(second.getValue());
            if (arithmeticOperation == ArithmeticOperation.DIVIDE && Math.abs(secondBase) < 0.0000001d) {
                throw new ArithmeticException("Divide by zero");
            }
            double resultBase = arithmeticOperation.apply(firstBase, secondBase);
            double resultValue = first.getUnit().fromBaseUnit(resultBase);
            entity.setResultValue(resultValue);
            entity.setResultUnit(first.getUnit().getUnitName());
            entity.setResultMeasurementType(input.getThisQuantityDTO().getMeasurementType());
            entity.setError(false);
            return saveAndMap(entity);
        } catch (RuntimeException ex) {
            throw recordAndRethrow(entity, ex);
        }
    }

    private boolean areEqual(QuantityModel<? extends IMeasurable> first, QuantityModel<? extends IMeasurable> second) {
        double firstBase = first.getUnit().toBaseUnit(first.getValue());
        double secondBase = second.getUnit().toBaseUnit(second.getValue());
        return Math.abs(firstBase - secondBase) < 0.0000001d;
    }

    private void validateMatchingMeasurementTypes(QuantityInputDTO input) {
        String firstType = normalizeMeasurementType(input.getThisQuantityDTO().getMeasurementType());
        String secondType = normalizeMeasurementType(input.getThatQuantityDTO().getMeasurementType());
        if (!firstType.equals(secondType)) {
            throw new QuantityMeasurementException(
                    "Cannot perform operation between different measurement categories: "
                            + input.getThisQuantityDTO().getMeasurementType()
                            + " and "
                            + input.getThatQuantityDTO().getMeasurementType());
        }
    }

    private QuantityModel<? extends IMeasurable> convertDtoToModel(QuantityDTO quantityDTO) {
        IMeasurable unit = resolveUnit(quantityDTO.getMeasurementType(), quantityDTO.getUnit());
        return new QuantityModel<>(quantityDTO.getValue(), unit);
    }

    private IMeasurable resolveUnit(String measurementType, String unitName) {
        String normalizedType = normalizeMeasurementType(measurementType);
        String normalizedUnit = unitName.trim().toUpperCase();
        try {
            return switch (normalizedType) {
                case "LENGTHUNIT", "LENGTH" -> LengthUnit.valueOf(normalizedUnit);
                case "VOLUMEUNIT", "VOLUME" -> VolumeUnit.valueOf(normalizedUnit);
                case "WEIGHTUNIT", "WEIGHT" -> WeightUnit.valueOf(normalizedUnit);
                case "TEMPERATUREUNIT", "TEMPERATURE" -> TemperatureUnit.valueOf(normalizedUnit);
                default -> throw new QuantityMeasurementException("Unsupported measurement type: " + measurementType);
            };
        } catch (IllegalArgumentException ex) {
            throw new QuantityMeasurementException("Unit must be valid for the specified measurement type", ex);
        }
    }

    private String normalizeMeasurementType(String measurementType) {
        return measurementType.trim().toUpperCase();
    }

    private QuantityMeasurementEntity createBaseEntity(QuantityInputDTO input, OperationType operationType) {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setThisValue(input.getThisQuantityDTO().getValue());
        entity.setThisUnit(input.getThisQuantityDTO().getUnit());
        entity.setThisMeasurementType(input.getThisQuantityDTO().getMeasurementType());
        entity.setThatValue(input.getThatQuantityDTO().getValue());
        entity.setThatUnit(input.getThatQuantityDTO().getUnit());
        entity.setThatMeasurementType(input.getThatQuantityDTO().getMeasurementType());
        entity.setOperation(operationType);
        entity.setError(false);
        return entity;
    }

    private QuantityMeasurementException recordAndRethrow(QuantityMeasurementEntity entity, RuntimeException exception) {
        entity.setError(true);
        entity.setErrorMessage(exception.getMessage());
        repository.save(entity);
        if (exception instanceof QuantityMeasurementException quantityMeasurementException) {
            return quantityMeasurementException;
        }
        return new QuantityMeasurementException(exception.getMessage(), exception);
    }

    private QuantityMeasurementDTO saveAndMap(QuantityMeasurementEntity entity) {
        return QuantityMeasurementDTO.fromEntity(repository.save(entity));
    }
}
