package com.gguilhermelopes.movieSphere.infra.exceptions;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message){
        super(message);
    }
}
