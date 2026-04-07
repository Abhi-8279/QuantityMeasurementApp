package com.quantityApp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.quantityApp.dto.QuantityInputDTO;
import com.quantityApp.model.ApiErrorResponse;
import com.quantityApp.model.QuantityMeasurementDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuantityMeasurementApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        assertThat(restTemplate).isNotNull();
    }

    @Test
    void shouldCompareQuantitiesThroughRestApi() {
        ResponseEntity<QuantityMeasurementDTO> response = post(
                "/api/v1/quantities/compare",
                TestData.compareInput(),
                QuantityMeasurementDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getOperation()).isEqualTo("COMPARE");
        assertThat(response.getBody().getResultString()).isEqualTo("true");
    }

    @Test
    void shouldConvertQuantitiesThroughRestApi() {
        ResponseEntity<QuantityMeasurementDTO> response = post(
                "/api/v1/quantities/convert",
                TestData.convertInput(),
                QuantityMeasurementDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getResultValue()).isEqualTo(12.0);
        assertThat(response.getBody().getResultUnit()).isEqualTo("INCHES");
    }

    @Test
    void shouldReturnBadRequestForDifferentMeasurementTypes() {
        ResponseEntity<ApiErrorResponse> response = post(
                "/api/v1/quantities/add",
                TestData.incompatibleInput(),
                ApiErrorResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).contains("different measurement categories");
    }

    @Test
    void shouldReturnInternalServerErrorForDivideByZero() {
        ResponseEntity<ApiErrorResponse> response = post(
                "/api/v1/quantities/divide",
                TestData.divideByZeroInput(),
                ApiErrorResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Divide by zero");
    }

    @Test
    void shouldExposeHealthEndpoint() {
        ResponseEntity<String> response = restTemplate.getForEntity(url("/actuator/health"), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("UP");
    }

    private <T> ResponseEntity<T> post(String path, QuantityInputDTO input, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return restTemplate.exchange(
                url(path),
                HttpMethod.POST,
                new HttpEntity<>(input, headers),
                responseType);
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }
}
