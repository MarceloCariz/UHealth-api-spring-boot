package com.api.uhealth.exception;

public class DuplicateKeyException extends RuntimeException{
    public DuplicateKeyException(String message) {
        super(message);
    }
}
