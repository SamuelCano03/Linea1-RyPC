package com.tren.linea1_service.exception;

public class ResourceDuplicateException extends RuntimeException{
    public ResourceDuplicateException() {
    }

    public ResourceDuplicateException(String message) {
        super(message);
    }
}
