package org.example.entities;

public class InvalidVersionException extends RuntimeException {

    public InvalidVersionException(String msg){
        super(msg);
    }
}
