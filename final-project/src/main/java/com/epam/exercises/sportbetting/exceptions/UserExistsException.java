package com.epam.exercises.sportbetting.exceptions;

public class UserExistsException extends RuntimeException {
    public UserExistsException() {
    }

    public UserExistsException(String message) {
        super(message);
    }
}
