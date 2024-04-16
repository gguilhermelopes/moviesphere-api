package com.gguilhermelopes.movieSphere.infra;

import com.gguilhermelopes.movieSphere.infra.exceptions.DataNotFoundException;
import com.gguilhermelopes.movieSphere.infra.exceptions.DatabaseException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<StandardException> entityNotFound(DataNotFoundException exception, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        
        StandardException error = new StandardException();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Resource not found.");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardException> database(DatabaseException exception, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardException error = new StandardException();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Database exception.");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }
}
