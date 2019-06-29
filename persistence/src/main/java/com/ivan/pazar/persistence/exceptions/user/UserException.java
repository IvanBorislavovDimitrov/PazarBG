package com.ivan.pazar.persistence.exceptions.user;

public abstract class UserException extends RuntimeException {

    protected UserException(String message) {
        super(message);
    }
}
