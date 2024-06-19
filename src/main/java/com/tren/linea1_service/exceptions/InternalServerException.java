package com.tren.linea1_service.exceptions;

public class InternalServerException extends RuntimeException {
    public InternalServerException() {
    }
    public InternalServerException(String message) {
        super(message);
    }
}