package com.akinbobola.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity <ExceptionResponse> handleException (MethodArgumentNotValidException ex) {
        Set <String> errors = new HashSet <>();

        ex.getBindingResult().getAllErrors().forEach(error ->
                errors.add(error.getDefaultMessage())
        );

        return ResponseEntity.badRequest()
                .body(
                        ExceptionResponse.builder()
                                .validationErrors(errors)
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity <ExceptionResponse> handleException (Exception ex) {
        return ResponseEntity.
                status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .error("An unexpected error occurred: " + ex.getMessage())
                                .build()
                );
    }
}
