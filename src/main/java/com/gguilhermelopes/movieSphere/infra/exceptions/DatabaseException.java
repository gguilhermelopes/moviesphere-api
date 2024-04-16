package com.gguilhermelopes.movieSphere.infra.exceptions;

public class DatabaseException extends RuntimeException{
    public DatabaseException(String message){
        super(message);
    }
}
