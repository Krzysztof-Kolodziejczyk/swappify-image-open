package com.example.swappify_image_open.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import static com.example.swappify_image_open.exceptions.ExceptionMessages.INVALID_REQUEST;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ConstraintViolationException extends RuntimeException {
    public ConstraintViolationException(String message) {
        super(message);
    }

    public ConstraintViolationException() {
        super(INVALID_REQUEST.toString());
    }
}
