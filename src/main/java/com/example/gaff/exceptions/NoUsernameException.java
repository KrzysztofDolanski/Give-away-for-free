package com.example.gaff.exceptions;

import java.util.EmptyStackException;

public class NoUsernameException extends RuntimeException {
    public NoUsernameException(String usenrame_must_be_write) {
        super(usenrame_must_be_write);
    }
}
