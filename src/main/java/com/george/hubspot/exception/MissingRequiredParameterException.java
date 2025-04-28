package com.george.hubspot.exception;

public class MissingRequiredParameterException extends RuntimeException{
    public MissingRequiredParameterException(String message) {
        super(message);
    }
}
