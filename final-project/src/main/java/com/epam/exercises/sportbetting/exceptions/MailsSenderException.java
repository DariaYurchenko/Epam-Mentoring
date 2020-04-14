package com.epam.exercises.sportbetting.exceptions;

public class MailsSenderException extends RuntimeException {

    public MailsSenderException() {
    }

    public MailsSenderException(String message) {
        super(message);
    }
}
