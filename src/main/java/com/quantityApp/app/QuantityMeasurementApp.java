package com.quantityApp.app;

import com.quantityApp.controller.QuantityMeasurementController;
import com.quantityApp.repository.IQuantityMeasurementRepository;
import com.quantityApp.repository.QuantityMeasurementDatabaseRepository;
import com.quantityApp.service.IQuantityMeasurementService;
import com.quantityApp.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

    	IQuantityMeasurementRepository repository =
    	        new QuantityMeasurementDatabaseRepository();

    	IQuantityMeasurementService service =
    	        new QuantityMeasurementServiceImpl(repository);

    	QuantityMeasurementController controller =
    	        new QuantityMeasurementController(service);

    	controller.run();

    }
}