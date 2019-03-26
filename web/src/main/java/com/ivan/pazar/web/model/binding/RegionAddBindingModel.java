package com.ivan.pazar.web.model.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RegionAddBindingModel {

    private static final String INVALID_NAME = "Invalid name";

    @NotEmpty(message = INVALID_NAME)
    @NotNull(message = INVALID_NAME)
    private String name;

    public static String getInvalidName() {
        return INVALID_NAME;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
