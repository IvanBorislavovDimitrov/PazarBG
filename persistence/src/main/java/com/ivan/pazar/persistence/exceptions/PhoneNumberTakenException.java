package com.ivan.pazar.persistence.exceptions;

public class PhoneNumberTakenException extends UserException {

    private static final String MESSAGE = "Phone number is taken";

    public PhoneNumberTakenException() {
        super(MESSAGE);
    }
}
