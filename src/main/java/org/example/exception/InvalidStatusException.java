package org.example.exception;

public class InvalidStatusException extends RuntimeException{

    public InvalidStatusException(String msg){
        super(msg);
    }
}
