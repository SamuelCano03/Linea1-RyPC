package com.tren.linea1_service.exception;

public class ResourceNotFoundException extends  RuntimeException {

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
