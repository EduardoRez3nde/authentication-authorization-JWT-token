package com.project.authentication.dtos.exceptions;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ValidationErrorDTO extends CustomErrorDTO {

    private final Map<String, String> errors = new HashMap<>();

    public ValidationErrorDTO() {}

    public ValidationErrorDTO(Instant timestamp, Integer status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String message) {
        errors.put(fieldName, message);
    }
}
