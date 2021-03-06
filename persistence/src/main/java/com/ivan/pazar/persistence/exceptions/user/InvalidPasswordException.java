package com.ivan.pazar.persistence.exceptions.user;

public class InvalidPasswordException extends UserException {

    private static final String MESSAGE = "Invalid password";

    public InvalidPasswordException() {
        super(MESSAGE);
    }
}
