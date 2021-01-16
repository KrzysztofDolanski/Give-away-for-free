package com.example.gaff.exceptions;

public class LoggedUserIsNoBuyerException extends RuntimeException{
    public LoggedUserIsNoBuyerException(String s) {
        super("If you dont buyer that you should be seller ;D");
    }
}
