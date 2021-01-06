package com.example.gaff.exceptions;

public class ApiUserAlreadyExistsException extends RuntimeException {
    public ApiUserAlreadyExistsException(String message) {
        super(message);
    }
}
