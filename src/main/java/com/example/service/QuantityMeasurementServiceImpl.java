package com.example.service;
import com.example.dto.QuantityDTO;
import com.example.dto.QuantityInputDTO;
import com.example.entity.QuantityMeasurementEntity;
import com.example.repository.QuantityMeasurementRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
@Service
@Transactional
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    @Autowired
    private QuantityMeasurementRepository repository;

    @Autowired
    private UserRepository userRepository;

    // ---------------- USER ----------------

    public Long getLoggedInUserId() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();
    }

    // ---------------- BASE ENTITY ----------------

    private QuantityMeasurementEntity createBaseEntity(QuantityInputDTO input) {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setThisValue(input.getThisQuantityDTO().getValue());
        entity.setThisUnit(input.getThisQuantityDTO().getUnit());
        entity.setThisMeasurementType(input.getThisQuantityDTO().getMeasurementType());

        entity.setThatValue(input.getThatQuantityDTO().getValue());
        entity.setThatUnit(input.getThatQuantityDTO().getUnit());
        entity.setThatMeasurementType(input.getThatQuantityDTO().getMeasurementType());

        entity.setUserId(getLoggedInUserId());
        entity.setCreatedAt(LocalDateTime.now());   // ✅ ADD
        entity.setUpdatedAt(LocalDateTime.now()); 

        return entity;
    }

    // ---------------- LENGTH ----------------


 // ---------------- LENGTH ----------------

    private double toMillimeter(double value, String unit) {
        switch (unit.toUpperCase()) {

            case "MILLIMETER":
            case "MILLIMETERS":
                return value;

            case "CENTIMETER":
            case "CENTIMETERS":
                return value * 10;

            case "METER":
            case "METERS":
                return value * 1000;

            case "KILOMETER":
            case "KILOMETERS":
                return value * 1_000_000;

            case "INCH":
            case "INCHES":
                return value * 25.4;

            case "FEET":
            case "FOOT":
                return value * 304.8;

            case "YARD":
            case "YARDS":
                return value * 914.4;

            default:
                throw new RuntimeException("Invalid length unit: " + unit);
        }
    }

    private double fromMillimeter(double value, String unit) {
        switch (unit.toUpperCase()) {

            case "MILLIMETER":
            case "MILLIMETERS":
                return value;

            case "CENTIMETER":
            case "CENTIMETERS":
                return value / 10;

            case "METER":
            case "METERS":
                return value / 1000;

            case "KILOMETER":
            case "KILOMETERS":
                return value / 1_000_000;

            case "INCH":
            case "INCHES":
                return value / 25.4;

            case "FEET":
            case "FOOT":
                return value / 304.8;

            case "YARD":
            case "YARDS":
                return value / 914.4;

            default:
                throw new RuntimeException("Invalid length unit: " + unit);
        }
    }

    // ---------------- TEMPERATURE (FIXED) ----------------

    private double toKelvin(double value, String unit) {
        switch (unit.toUpperCase()) {
            case "CELSIUS": return value + 273.15;
            case "FAHRENHEIT": return (value - 32) * 5 / 9 + 273.15;
            case "KELVIN": return value;
            default: throw new RuntimeException("Invalid temperature unit");
        }
    }

    private double fromKelvin(double value, String unit) {
        switch (unit.toUpperCase()) {
            case "CELSIUS": return value - 273.15;
            case "FAHRENHEIT": return (value - 273.15) * 9 / 5 + 32;
            case "KELVIN": return value;
            default: throw new RuntimeException("Invalid temperature unit");
        }
    }

    // ---------------- VOLUME ----------------

    private double toMilliliter(double value, String unit) {
        switch (unit.toUpperCase()) {
            case "LITER": 
                return value * 1000;

            case "MILLILITER": 
                return value;

            case "CUBIC_METER": 
                return value * 1_000_000;

            case "GALLON": 
                return value * 3785.41; // ADDED

            default: 
                throw new RuntimeException("Invalid volume unit: " + unit);
        }
    }

    private double fromMilliliter(double value, String unit) {
        switch (unit.toUpperCase()) {
            case "LITER": 
                return value / 1000;

            case "MILLILITER": 
                return value;

            case "CUBIC_METER": 
                return value / 1_000_000;

            case "GALLON": 
                return value / 3785.41; //  ADDED

            default: 
                throw new RuntimeException("Invalid volume unit: " + unit);
        }
    }
    
 // ---------------- WEIGHT ----------------

    private double toGrams(double value, String unit) {
        switch (unit.toUpperCase()) {

            case "MILLIGRAM": return value / 1000;

            case "GRAM": return value;

            case "KILOGRAM": return value * 1000;

            case "TON": return value * 1_000_000;

            case "OUNCE": return value * 28.3495;

            case "POUND": return value * 453.592;

            default: throw new RuntimeException("Invalid weight unit: " + unit);
        }
    }

    private double fromGrams(double value, String unit) {
        switch (unit.toUpperCase()) {

            case "MILLIGRAM": return value * 1000;

            case "GRAM": return value;

            case "KILOGRAM": return value / 1000;

            case "TON": return value / 1_000_000;

            case "OUNCE": return value / 28.3495;

            case "POUND": return value / 453.592;

            default: throw new RuntimeException("Invalid weight unit: " + unit);
        }
    }

    // ---------------- ADD ----------------

    @Override
    public QuantityMeasurementEntity add(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = createBaseEntity(input);
        String type = input.getThisQuantityDTO().getMeasurementType();

        double a = input.getThisQuantityDTO().getValue();
        double b = input.getThatQuantityDTO().getValue();
        String unit = input.getThisQuantityDTO().getUnit();

        double result = 0;

        if ("LENGTH".equalsIgnoreCase(type)) {
            result = toMillimeter(a, unit) + toMillimeter(b, input.getThatQuantityDTO().getUnit());
            result = fromMillimeter(result, unit);
        }

        else if ("TEMPERATURE".equalsIgnoreCase(type)) {
            result = toKelvin(a, unit) + toKelvin(b, input.getThatQuantityDTO().getUnit());
            result = fromKelvin(result, unit);
        }

        else if ("VOLUME".equalsIgnoreCase(type)) {
            result = toMilliliter(a, unit) + toMilliliter(b, input.getThatQuantityDTO().getUnit());
            result = fromMilliliter(result, unit);
        }

        else if ("WEIGHT".equalsIgnoreCase(type)) {
            result = toGrams(a, unit) + toGrams(b, input.getThatQuantityDTO().getUnit());
            result = fromGrams(result, unit);
        }

        entity.setResultValue(round(result));
        entity.setResultUnit(unit);
        entity.setOperation("ADD");

        return repository.save(entity);
    }

    // ---------------- SUBTRACT ----------------

    @Override
    public QuantityMeasurementEntity subtract(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = createBaseEntity(input);
        String type = input.getThisQuantityDTO().getMeasurementType();

        double a = input.getThisQuantityDTO().getValue();
        double b = input.getThatQuantityDTO().getValue();
        String unit = input.getThisQuantityDTO().getUnit();

        double result = 0;

        if ("LENGTH".equalsIgnoreCase(type)) {
            result = toMillimeter(a, unit) - toMillimeter(b, input.getThatQuantityDTO().getUnit());
            result = fromMillimeter(result, unit);
        }

        else if ("TEMPERATURE".equalsIgnoreCase(type)) {
            result = toKelvin(a, unit) - toKelvin(b, input.getThatQuantityDTO().getUnit());
            result = fromKelvin(result, unit);
        }

        else if ("VOLUME".equalsIgnoreCase(type)) {
            result = toMilliliter(a, unit) - toMilliliter(b, input.getThatQuantityDTO().getUnit());
            result = fromMilliliter(result, unit);
        }

        else if ("WEIGHT".equalsIgnoreCase(type)) {
            result = toGrams(a, unit) - toGrams(b, input.getThatQuantityDTO().getUnit());
            result = fromGrams(result, unit);
        }

        entity.setResultValue(round(result));
        entity.setResultUnit(unit);
        entity.setOperation("SUBTRACT");

        return repository.save(entity);
    }

    // ---------------- MULTIPLY ----------------

    @Override
    public QuantityMeasurementEntity multiply(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = createBaseEntity(input);

        double a = input.getThisQuantityDTO().getValue();
        double b = input.getThatQuantityDTO().getValue();
        String unit = input.getThisQuantityDTO().getUnit();

        double result = a * b;

        entity.setResultValue(round(result));
        entity.setResultUnit(unit + "²"); // optional
        entity.setOperation("MULTIPLY");

        return repository.save(entity);
    }

    // ---------------- DIVIDE ----------------

//    @Override
//    public QuantityMeasurementEntity divide(QuantityInputDTO input) {
//
//        QuantityMeasurementEntity entity = createBaseEntity(input);
//        String type = input.getThisQuantityDTO().getMeasurementType();
//
//        double a = input.getThisQuantityDTO().getValue();
//        double b = input.getThatQuantityDTO().getValue();
//
//        if (b == 0) throw new ArithmeticException("Cannot divide by zero");
//
//        double result = 0;
//
//        // ---------------- LENGTH ----------------
//        if ("LENGTH".equalsIgnoreCase(type)) {
//
//            double val1 = toMillimeter(a, input.getThisQuantityDTO().getUnit());
//            double val2 = toMillimeter(b, input.getThatQuantityDTO().getUnit());
//
//            result = val1 / val2;
//
//            entity.setResultValue(result);
//            entity.setResultUnit("RATIO");
//        }
//
//        // ---------------- TEMPERATURE ----------------
//        else if ("TEMPERATURE".equalsIgnoreCase(type)) {
//
//            double val1 = toKelvin(a, input.getThisQuantityDTO().getUnit());
//            double val2 = toKelvin(b, input.getThatQuantityDTO().getUnit());
//
//            result = val1 / val2;
//
//            entity.setResultValue(result);
//            entity.setResultUnit("RATIO");
//        }
//
//        // ---------------- VOLUME ----------------
//        else if ("VOLUME".equalsIgnoreCase(type)) {
//
//            double val1 = toMilliliter(a, input.getThisQuantityDTO().getUnit());
//            double val2 = toMilliliter(b, input.getThatQuantityDTO().getUnit());
//
//            result = val1 / val2;
//
//            entity.setResultValue(result);
//            entity.setResultUnit("RATIO");
//        }
//
//        // ---------------- WEIGHT ----------------
//        else if ("WEIGHT".equalsIgnoreCase(type)) {
//
//            double val1 = toGrams(a, input.getThisQuantityDTO().getUnit());
//            double val2 = toGrams(b, input.getThatQuantityDTO().getUnit());
//
//            result = val1 / val2;
//
//            entity.setResultValue(result);
//            entity.setResultUnit("RATIO");
//        }
//
//        entity.setOperation("DIVIDE");
//        return repository.save(entity);
//    }

    
    @Override
    public QuantityMeasurementEntity divide(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = createBaseEntity(input);

        double a = input.getThisQuantityDTO().getValue();
        double b = input.getThatQuantityDTO().getValue();

        if (b == 0) throw new ArithmeticException("Cannot divide by zero");

        double result = a / b;

        entity.setResultValue(round(result));
        entity.setResultUnit("RATIO");
        entity.setOperation("DIVIDE");

        return repository.save(entity);
    }
    // ---------------- CONVERT ----------------
    @Override
    public QuantityMeasurementEntity convert(QuantityInputDTO input) {

        //  Step 1: Validate input
        if (input == null || input.getThisQuantityDTO() == null) {
            throw new RuntimeException("Input or thisQuantityDTO cannot be null");
        }

        if (input.getThatQuantityDTO() == null || input.getThatQuantityDTO().getUnit() == null) {
            throw new RuntimeException("Target unit (thatQuantityDTO.unit) is required");
        }

        //  Step 2: Extract safely
        QuantityDTO fromDTO = input.getThisQuantityDTO();
        QuantityDTO toDTO = input.getThatQuantityDTO();

        if (fromDTO.getValue() == null) {
            throw new RuntimeException("Source value is required for conversion");
        }

        double value = fromDTO.getValue();
        String fromUnit = fromDTO.getUnit();
        String toUnit = toDTO.getUnit();
        String type = fromDTO.getMeasurementType();

        double result = 0;

        // Step 3: Conversion logic
        if ("TEMPERATURE".equalsIgnoreCase(type)) {
            result = fromKelvin(toKelvin(value, fromUnit), toUnit);
        }

        else if ("LENGTH".equalsIgnoreCase(type)) {
            result = fromMillimeter(toMillimeter(value, fromUnit), toUnit);
        }

        else if ("VOLUME".equalsIgnoreCase(type)) {
            result = fromMilliliter(toMilliliter(value, fromUnit), toUnit);
        }

        else if ("WEIGHT".equalsIgnoreCase(type)) {
            result = fromGrams(toGrams(value, fromUnit), toUnit);
        }

        else {
            throw new RuntimeException("Unsupported measurement type: " + type);
        }

        // Step 4: Create entity AFTER validation (important)
        QuantityMeasurementEntity entity = createBaseEntity(input);

        
    
        entity.setResultValue(round(result));
        entity.setResultUnit(toUnit);
        entity.setOperation("CONVERT");
        

        return repository.save(entity);
    }

    @Override
    public List<QuantityMeasurementEntity> getHistoryByOperation(String operation) {

        return repository.findByOperationAndUserId(
                operation.toUpperCase(),
                getLoggedInUserId()
        );
    }

    @Override
    public long getOperationCount(String operation) {

        return repository.countByOperationAndUserId(
                operation.toUpperCase(),
                getLoggedInUserId()
        );
    }

    // ---------------- COMPARE ----------------

    @Override
    public QuantityMeasurementEntity compare(QuantityInputDTO input) {

        QuantityMeasurementEntity entity = createBaseEntity(input);

        String type = input.getThisQuantityDTO().getMeasurementType();

        double a = input.getThisQuantityDTO().getValue();
        double b = input.getThatQuantityDTO().getValue();

        boolean isEqual = false;

        // LENGTH
        if ("LENGTH".equalsIgnoreCase(type)) {
            double val1 = toMillimeter(a, input.getThisQuantityDTO().getUnit());
            double val2 = toMillimeter(b, input.getThatQuantityDTO().getUnit());
            isEqual = Math.abs(val1 - val2) < 0.0001;
        }

        // TEMPERATURE
        else if ("TEMPERATURE".equalsIgnoreCase(type)) {
            double val1 = toKelvin(a, input.getThisQuantityDTO().getUnit());
            double val2 = toKelvin(b, input.getThatQuantityDTO().getUnit());
            isEqual = Math.abs(val1 - val2) < 0.0001;
        }

        // VOLUME
        else if ("VOLUME".equalsIgnoreCase(type)) {
            double val1 = toMilliliter(a, input.getThisQuantityDTO().getUnit());
            double val2 = toMilliliter(b, input.getThatQuantityDTO().getUnit());
            isEqual = Math.abs(val1 - val2) < 0.0001;
        }

        // WEIGHT
        else if ("WEIGHT".equalsIgnoreCase(type)) {
            double val1 = toGrams(a, input.getThisQuantityDTO().getUnit());
            double val2 = toGrams(b, input.getThatQuantityDTO().getUnit());
            isEqual = Math.abs(val1 - val2) < 0.0001;
        }

        // FINAL OUTPUT FORMAT
        entity.setResultValue(isEqual ? 1.0 : 0.0);
        entity.setResultUnit(isEqual ? "TRUE" : "FALSE");
        entity.setOperation("COMPARE");

        return repository.save(entity);
    }

    // ---------------- GET ALL ----------------

    @Override
    public List<QuantityMeasurementEntity> getAll() {

        return repository.findByUserId(getLoggedInUserId());
    }

    // ---------------- GET BY ID ----------------

    @Override
    public QuantityMeasurementEntity getById(Long id) {

        return repository.findById(id)
                .filter(q -> q.getUserId().equals(getLoggedInUserId()))
                .orElseThrow(() -> new RuntimeException("Not found or unauthorized"));
    }

    // ---------------- UPDATE ----------------

    @Override
    public QuantityMeasurementEntity update(Long id, QuantityInputDTO input) {

        QuantityMeasurementEntity existing = repository.findById(id)
                .filter(q -> q.getUserId().equals(getLoggedInUserId()))
                .orElseThrow(() -> new RuntimeException("Not found or unauthorized"));

        QuantityMeasurementEntity updated = createBaseEntity(input);

        // Preserve ID
        updated.setId(existing.getId());

        //  OPTIONAL: Recalculate operation if needed
        updated.setOperation(existing.getOperation());
        updated.setCreatedAt(existing.getCreatedAt()); // keep old
        updated.setUpdatedAt(LocalDateTime.now()); 

        return repository.save(updated);
    }

    // ---------------- DELETE ----------------
    @Override
    public void delete(Long id) {

        repository.forceDelete(id);

        System.out.println("🔥 FORCE DELETE EXECUTED");
    }
    @Transactional
    public void deleteAllByUser() {
         // your existing logic
        repository.deleteByUserId(getLoggedInUserId());
    }
    private double round(double value) {
        return Math.round(value * 1000000.0) / 1000000.0;
    }
    @Transactional
    public void deleteFiltered(String operation, String type) {

        Long userId = getLoggedInUserId();  // ✅ GET HERE

        if (operation != null && !operation.isEmpty() &&
            type != null && !type.isEmpty()) {

            repository.deleteByOperationAndThisMeasurementTypeAndUserId(
                    operation, type, userId);
            return;
        }

        if (operation != null && !operation.isEmpty()) {
            repository.deleteByOperationAndUserId(operation, userId);
            return;
        }

        if (type != null && !type.isEmpty()) {
            repository.deleteByThisMeasurementTypeAndUserId(type, userId);
            return;
        }

        repository.deleteByUserId(userId);
    }

    @Override
    public List<QuantityMeasurementEntity> getFiltered(String operation, String type) {

        Long userId = getCurrentUserId();

        // ✅ No filters → return all
        if ((operation == null || operation.isEmpty()) &&
            (type == null || type.isEmpty())) {

            return repository.findByUserId(userId);
        }

        // ✅ Only operation
        if (operation != null && !operation.isEmpty() &&
            (type == null || type.isEmpty())) {

            return repository.findByOperationAndUserId(operation, userId);
        }

        // ✅ Only type
        if ((operation == null || operation.isEmpty()) &&
            type != null && !type.isEmpty()) {

            return repository.findByThisMeasurementTypeAndUserId(type, userId);
        }

        // ✅ Both filters
        return repository.findByOperationAndThisMeasurementTypeAndUserId(
                operation, type, userId);
    }

private Long getCurrentUserId() {
    return getLoggedInUserId(); // ✅ FIXED
}



}