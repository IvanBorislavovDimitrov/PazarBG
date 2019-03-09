package com.ivan.pazar.persistence.exceptions;

public abstract class UserException extends RuntimeException {

    protected UserException(String message) {
        super(message);
    }
}
