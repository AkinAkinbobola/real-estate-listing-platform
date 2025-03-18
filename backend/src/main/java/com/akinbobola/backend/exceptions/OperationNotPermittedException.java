package com.akinbobola.backend.exceptions;

public class OperationNotPermittedException extends RuntimeException {
    public OperationNotPermittedException (String message) {
        super(message);
    }
}
