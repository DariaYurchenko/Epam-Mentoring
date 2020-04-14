package com.epam.exercises.cleaningcode.exercise1.exceptions;

public class InvalidCoordinateException extends RuntimeException {

    public InvalidCoordinateException() {
    }

    public InvalidCoordinateException(String message) {
        super(message);
    }
}
