package com.qma.gateway.client;

import com.qma.gateway.dto.CalculationResultDTO;
import com.qma.gateway.dto.QuantityInputDTO;
import com.qma.gateway.service.DownstreamServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Component
public class MeasurementServiceClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public MeasurementServiceClient(
            RestTemplate restTemplate,
            @Value("${services.measurement.base-url}") String baseUrl) {

        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public CalculationResultDTO calculate(String operation, QuantityInputDTO input) {
        try {
            return restTemplate.postForObject(
                    baseUrl + "/" + operation.toUpperCase(),
                    input,
                    CalculationResultDTO.class);
        } catch (HttpStatusCodeException exception) {
            throw toDownstreamException(exception);
        }
    }

    private DownstreamServiceException toDownstreamException(HttpStatusCodeException exception) {
        HttpStatusCode statusCode = exception.getStatusCode();
        String message = exception.getResponseBodyAsString();
        if (message == null || message.isBlank()) {
            message = exception.getStatusText();
        }

        return new DownstreamServiceException(statusCode, message);
    }
}