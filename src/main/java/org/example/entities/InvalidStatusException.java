package org.example.entities;

public class InvalidStatusException extends RuntimeException{

    public InvalidStatusException(String msg){
        super(msg);
    }
}
