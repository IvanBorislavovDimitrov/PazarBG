package com.ivan.pazar.persistence.exceptions.user;

public class UsernameTakenException extends UserException {

    private static final String MESSAGE = "Username is taken";

    public UsernameTakenException() {
        super(MESSAGE);
    }
}
