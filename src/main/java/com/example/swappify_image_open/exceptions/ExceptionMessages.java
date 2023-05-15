package com.example.swappify_image_open.exceptions;

public enum ExceptionMessages {
    INVALID_REQUEST("bad request"),
    AUTHORIZATION_HEADER_NOT_FOUND("authorization header not found.");

    final String message;

    ExceptionMessages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
