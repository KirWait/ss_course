package org.example.exception;

public class InvalidAccessException extends RuntimeException {
    public InvalidAccessException(String msg) {
        super(msg);
    }
}
