package com.flab.mars.exception;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import java.util.Map;

@Getter
public class BadWebClientRequestException extends RuntimeException {
    private final HttpStatusCode statusCode;
    private String errorDescription;

    public BadWebClientRequestException(String message, HttpStatusCode statusCode, ObjectMapper objectMapper) {
        super(message);
        this.statusCode = statusCode;

        try {
            Map<String, String> errorMap = objectMapper.readValue(message, Map.class);
            errorDescription = errorMap.getOrDefault("error_description", "Unknown error description");
        } catch (JsonProcessingException e) {
            errorDescription = "Failed to parse error description";
        }
    }
}
