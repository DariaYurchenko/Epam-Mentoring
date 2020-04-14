package com.epam.exercises.sportbetting.exceptions;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
    }

    public InvalidTokenException(String message) {
        super(message);
    }
}
