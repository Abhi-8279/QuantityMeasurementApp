package com.qma.gateway.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qma.gateway.service.DownstreamServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(DownstreamServiceException.class)
    public ResponseEntity<Map<String, String>> handleDownstream(DownstreamServiceException exception) {
        return ResponseEntity.status(exception.getStatusCode())
                .body(Map.of("message", simplifyMessage(exception.getMessage())));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", exception.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException exception) {
        HttpStatus status = "Unauthorized".equalsIgnoreCase(exception.getMessage())
                ? HttpStatus.UNAUTHORIZED
                : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(Map.of("message", exception.getMessage()));
    }

    private String simplifyMessage(String message) {
        if (message == null || message.isBlank()) {
            return "Downstream service call failed";
        }

        try {
            JsonNode node = objectMapper.readTree(message);
            if (node.has("message")) {
                return node.get("message").asText();
            }
        } catch (Exception ignored) {
            // fall back to raw message
        }

        return message;
    }
}