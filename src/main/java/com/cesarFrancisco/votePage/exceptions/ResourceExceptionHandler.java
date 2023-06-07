package com.cesarFrancisco.votePage.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;
import java.time.LocalDate;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        String error = "Object Not Found";
        String path = request.getRequestURI();

        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), error, path, e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(VoteException.class)
    public ResponseEntity<StandardError> objectNotFound(VoteException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = "Vote Error";
        String path = request.getRequestURI();

        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), error, path, e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<StandardError> objectNotFound(AuthenticationException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = "Vote Error";
        String path = request.getRequestURI();

        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), error, path, e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

}
