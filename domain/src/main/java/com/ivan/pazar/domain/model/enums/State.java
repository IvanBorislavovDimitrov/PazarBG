package com.ivan.pazar.domain.model.enums;

public enum State {

    NEW, USED;

    @Override
    public String toString() {
        return name().substring(0, 1) + name().substring(1).toLowerCase();
    }
}
