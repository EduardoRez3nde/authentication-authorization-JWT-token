package com.project.authentication.controllers.handlers;

import com.project.authentication.dtos.exceptions.CustomErrorDTO;
import com.project.authentication.dtos.exceptions.ValidationErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorDTO> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ValidationErrorDTO err = new ValidationErrorDTO(Instant.now(), status.value(), "Dados Invalidos", request.getRequestURI());

        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            err.addError(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return ResponseEntity.status(status).body(err);
    }
}
