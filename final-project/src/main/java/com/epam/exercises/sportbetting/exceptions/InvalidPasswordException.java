package com.epam.exercises.sportbetting.exceptions;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
    }

    public InvalidPasswordException(String message) {
        super(message);
    }
}
