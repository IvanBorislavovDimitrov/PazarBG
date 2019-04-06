package com.ivan.pazar.persistence.exceptions.user;

public class PasswordsMismatchException extends UserException {

    private static final String MESSAGE = "Passwords are not equal";

    public PasswordsMismatchException() {
        super(MESSAGE);
    }
}
