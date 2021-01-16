package com.example.gaff.exceptions;

public class LoggedUserIsNoSellerException extends RuntimeException {
    public LoggedUserIsNoSellerException(String s) {
        super("If you dont seller that you should be buyer ;D");
    }
}
