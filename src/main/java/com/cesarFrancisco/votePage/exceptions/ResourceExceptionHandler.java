package com.cesarFrancisco.votePage.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


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
        String error = "Authentication Exception";
        String path = request.getRequestURI();

        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), error, path, e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(NonAuthorizedException.class)
    public ResponseEntity<StandardError> objectNotFound(NonAuthorizedException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.FORBIDDEN;
        String error = "Not allowed";
        String path = request.getRequestURI();

        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), error, path, e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> objectNotFound(MethodArgumentNotValidException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = "Argument not valid";
        String path = request.getRequestURI();

        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), error, path, e.getMessage());
        return ResponseEntity.status(status).body(err);
    }

}
