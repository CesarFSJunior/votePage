package com.cesarFrancisco.votePage.exceptions;

public class NonAuthorizedException extends RuntimeException{

    public NonAuthorizedException(String message) {
        super(message);
    }
}
