package org.example.exception;

public class InvalidVersionException extends RuntimeException {

    public InvalidVersionException(String msg){
        super(msg);
    }
}
