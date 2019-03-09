package com.ivan.pazar.persistence.exceptions;

public class EmailTakenException  extends UserException {

    private static final String MESSAGE = "Email is taken";

    public EmailTakenException() {
        super(MESSAGE);
    }
}
