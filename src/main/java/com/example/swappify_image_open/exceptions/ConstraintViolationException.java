package com.example.swappify_image_open.exceptions;

public class ConstraintViolationException extends RuntimeException {
    public ConstraintViolationException(String message) {
        super(message);
    }

    public ConstraintViolationException() {
        super(ExceptionMessages.INVALID_REQUEST);
    }
}
