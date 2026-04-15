package com.qma.gateway.client;

import com.qma.gateway.dto.HistorySaveRequest;
import com.qma.gateway.dto.QuantityMeasurementEntity;
import com.qma.gateway.service.DownstreamServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Component
public class HistoryServiceClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public HistoryServiceClient(
            RestTemplate restTemplate,
            @Value("${services.history.base-url}") String baseUrl) {

        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public QuantityMeasurementEntity create(HistorySaveRequest request) {
        try {
            return restTemplate.postForObject(baseUrl, request, QuantityMeasurementEntity.class);
        } catch (HttpStatusCodeException exception) {
            throw toDownstreamException(exception);
        }
    }

    public QuantityMeasurementEntity update(Long id, Long userId, HistorySaveRequest request) {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/{id}")
                .queryParam("userId", userId)
                .buildAndExpand(id)
                .encode()
                .toUri();

        try {
            ResponseEntity<QuantityMeasurementEntity> response = restTemplate.exchange(
                    uri,
                    HttpMethod.PUT,
                    new HttpEntity<>(request),
                    QuantityMeasurementEntity.class);

            return response.getBody();
        } catch (HttpStatusCodeException exception) {
            throw toDownstreamException(exception);
        }
    }

    public List<QuantityMeasurementEntity> find(Long userId, String operation, String type) {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("userId", userId)
                .queryParamIfPresent("operation", java.util.Optional.ofNullable(blankToNull(operation)))
                .queryParamIfPresent("type", java.util.Optional.ofNullable(blankToNull(type)))
                .build()
                .encode()
                .toUri();

        try {
            ResponseEntity<List<QuantityMeasurementEntity>> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    });
            return response.getBody();
        } catch (HttpStatusCodeException exception) {
            throw toDownstreamException(exception);
        }
    }

    public List<QuantityMeasurementEntity> findByOperation(String operation, Long userId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/operation/{operation}")
                .queryParam("userId", userId)
                .buildAndExpand(operation)
                .encode()
                .toUri();

        try {
            ResponseEntity<List<QuantityMeasurementEntity>> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    });
            return response.getBody();
        } catch (HttpStatusCodeException exception) {
            throw toDownstreamException(exception);
        }
    }

    public long countByOperation(String operation, Long userId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/count/{operation}")
                .queryParam("userId", userId)
                .buildAndExpand(operation)
                .encode()
                .toUri();

        try {
            Long response = restTemplate.getForObject(uri, Long.class);
            return response == null ? 0L : response;
        } catch (HttpStatusCodeException exception) {
            throw toDownstreamException(exception);
        }
    }

    public QuantityMeasurementEntity getById(Long id, Long userId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/{id}")
                .queryParam("userId", userId)
                .buildAndExpand(id)
                .encode()
                .toUri();

        try {
            return restTemplate.getForObject(uri, QuantityMeasurementEntity.class);
        } catch (HttpStatusCodeException exception) {
            throw toDownstreamException(exception);
        }
    }

    public void deleteById(Long id, Long userId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/{id}")
                .queryParam("userId", userId)
                .buildAndExpand(id)
                .encode()
                .toUri();

        try {
            restTemplate.delete(uri);
        } catch (HttpStatusCodeException exception) {
            throw toDownstreamException(exception);
        }
    }

    public void deleteAll(Long userId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/all")
                .queryParam("userId", userId)
                .build()
                .encode()
                .toUri();

        try {
            restTemplate.delete(uri);
        } catch (HttpStatusCodeException exception) {
            throw toDownstreamException(exception);
        }
    }

    public void deleteFiltered(Long userId, String operation, String type) {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/filtered")
                .queryParam("userId", userId)
                .queryParamIfPresent("operation", java.util.Optional.ofNullable(blankToNull(operation)))
                .queryParamIfPresent("type", java.util.Optional.ofNullable(blankToNull(type)))
                .build()
                .encode()
                .toUri();

        try {
            restTemplate.delete(uri);
        } catch (HttpStatusCodeException exception) {
            throw toDownstreamException(exception);
        }
    }

    private String blankToNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value;
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