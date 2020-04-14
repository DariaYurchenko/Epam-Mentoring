package com.epam.exercises.sportbetting.exceptions;

public class NoActualOutcomeOddException extends RuntimeException {

    public NoActualOutcomeOddException() {
    }

    public NoActualOutcomeOddException(String message) {
        super(message);
    }
}
