package com.quantityApp.service;

import com.quantityApp.dto.QuantityDTO;
import com.quantityApp.entity.QuantityMeasurementEntity;
import com.quantityApp.repository.IQuantityMeasurementRepository;

public class QuantityMeasurementServiceImpl
        implements IQuantityMeasurementService {

    private IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    // ---------------- CONVERT ----------------

    @Override
    public QuantityDTO convert(QuantityDTO input, String targetUnit) {

        if (input == null)
            throw new IllegalArgumentException("Input cannot be null");

        return new QuantityDTO(
                input.getValue(),
                targetUnit,
                input.getMeasurementType()
        );
    }

    // ---------------- COMPARE ----------------

    @Override
    public boolean compare(QuantityDTO q1, QuantityDTO q2) {

        double value1 = q1.getValue();
        double value2 = q2.getValue();

        boolean result = value1 == value2;

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        "COMPARE",
                        q1.getMeasurementType(),
                        q1.getUnit(),
                        value1,
                        q2.getUnit(),
                        value2,
                        result
                );

        repository.save(entity);

        return result;
    }

    // ---------------- ADD ----------------

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {

        double result = q1.getValue() + q2.getValue();

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        "ADD",
                        q1.getMeasurementType(),
                        q1.getUnit(),
                        q1.getValue(),
                        q2.getUnit(),
                        q2.getValue(),
                        true
                );

        repository.save(entity);

        return new QuantityDTO(
                result,
                q1.getUnit(),
                q1.getMeasurementType()
        );
    }

    // ---------------- SUBTRACT ----------------

    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) {

        double result = q1.getValue() - q2.getValue();

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        "SUBTRACT",
                        q1.getMeasurementType(),
                        q1.getUnit(),
                        q1.getValue(),
                        q2.getUnit(),
                        q2.getValue(),
                        true
                );

        repository.save(entity);

        return new QuantityDTO(
                result,
                q1.getUnit(),
                q1.getMeasurementType()
        );
    }

    // ---------------- DIVIDE ----------------

    @Override
    public double divide(QuantityDTO q1, QuantityDTO q2) {

        if (q2.getValue() == 0)
            throw new ArithmeticException("Division by zero");

        double result = q1.getValue() / q2.getValue();

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        "DIVIDE",
                        q1.getMeasurementType(),
                        q1.getUnit(),
                        q1.getValue(),
                        q2.getUnit(),
                        q2.getValue(),
                        true
                );

        repository.save(entity);

        return result;
    }

    public IQuantityMeasurementRepository getRepository() {
        return repository;
    }

    public void setRepository(IQuantityMeasurementRepository repository) {
        this.repository = repository;
    }
}