package com.ivan.pazar.persistence.dto.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CategoryAddBindingModel {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private byte[] picture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
