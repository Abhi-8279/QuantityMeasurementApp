package com.quantityApp.app;

import com.quantityApp.controller.QuantityMeasurementController;
import com.quantityApp.dto.QuantityDTO;
import com.quantityApp.repository.QuantityMeasurementCacheRepository;
import com.quantityApp.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        QuantityMeasurementCacheRepository repository =
                QuantityMeasurementCacheRepository.getInstance();

        QuantityMeasurementServiceImpl service =
                new QuantityMeasurementServiceImpl(repository);

        QuantityMeasurementController controller =
                new QuantityMeasurementController(service);

        QuantityDTO q1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO q2 = new QuantityDTO(12.0, "INCHES", "LENGTH");
        QuantityDTO q3 = new QuantityDTO(24.0,"INCHES","LENGTH");

        controller.performComparison(q1, q2);
        controller.performAddition(q1, q2);
        controller.performComparison(q2, q3);
    }
}