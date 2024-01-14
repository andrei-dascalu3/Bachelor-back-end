package com.fii.backendapp.exceptions;

public class MissingTokenException extends RuntimeException{
    public MissingTokenException() {
    }

    public MissingTokenException(String message) {
        super(message);
    }
}
