package com.ivan.pazar.model.enums;

public enum Shipment {
    PAID_BY_THE_SELLER, PAID_BY_THE_BUYER;

    @Override
    public String toString() {
        return name().substring(0, 1) + name().substring(1).toLowerCase().replace("_", " ");
    }
}
