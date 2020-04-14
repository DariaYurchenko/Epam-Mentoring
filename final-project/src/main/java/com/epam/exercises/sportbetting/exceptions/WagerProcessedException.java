package com.epam.exercises.sportbetting.exceptions;

public class WagerProcessedException extends RuntimeException {

    public WagerProcessedException() {
    }

    public WagerProcessedException(String message) {
        super(message);
    }
}
