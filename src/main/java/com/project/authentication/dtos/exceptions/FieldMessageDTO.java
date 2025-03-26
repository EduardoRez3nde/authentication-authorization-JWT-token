package com.project.authentication.dtos.exceptions;

import java.util.Objects;

public class FieldMessageDTO {

    private String fieldName;
    private String message;

    public FieldMessageDTO() {}

    public FieldMessageDTO(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FieldMessageDTO that = (FieldMessageDTO) o;
        return Objects.equals(fieldName, that.fieldName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(fieldName);
    }
}
