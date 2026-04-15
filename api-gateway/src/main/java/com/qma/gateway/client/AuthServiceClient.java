package com.qma.gateway.client;

import com.qma.gateway.dto.LoginDTO;
import com.qma.gateway.dto.RegisterDTO;
import com.qma.gateway.dto.UserIdResponse;
import com.qma.gateway.service.DownstreamServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class AuthServiceClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public AuthServiceClient(
            RestTemplate restTemplate,
            @Value("${services.auth.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public String register(RegisterDTO request) {
        try {
            return restTemplate.postForObject(baseUrl + "/register", request, String.class);
        } catch (HttpStatusCodeException exception) {
            throw toDownstreamException(exception);
        }
    }

    public String login(LoginDTO request) {
        try {
            return restTemplate.postForObject(baseUrl + "/login", request, String.class);
        } catch (HttpStatusCodeException exception) {
            throw toDownstreamException(exception);
        }
    }

    public Long getUserIdByEmail(String email) {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/users/id")
                .queryParam("email", email)
                .build()
                .encode()
                .toUri();

        try {
            UserIdResponse response = restTemplate.getForObject(uri, UserIdResponse.class);
            if (response == null || response.getUserId() == null) {
                throw new RuntimeException("User not found");
            }

            return response.getUserId();
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