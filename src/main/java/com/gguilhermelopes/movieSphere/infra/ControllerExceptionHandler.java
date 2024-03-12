package com.gguilhermelopes.movieSphere.infra;

import com.gguilhermelopes.movieSphere.infra.exceptions.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardException> entityNotFound(EntityNotFoundException exception, HttpServletRequest request){
        StandardException error = new StandardException();
        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError("Resource not found.");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(error.getStatus()).body(error);
    }
}
