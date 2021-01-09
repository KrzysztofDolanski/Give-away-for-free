package com.example.gaff.exceptions;

public class UserIdNotFoundException extends RuntimeException {
    public UserIdNotFoundException(String s) {
        super(s);
    }
}
