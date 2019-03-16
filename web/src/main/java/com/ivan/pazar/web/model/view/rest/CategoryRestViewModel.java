package com.ivan.pazar.web.model.view.rest;

public class CategoryRestViewModel {

    private String name;

    private byte[] picture;

    public CategoryRestViewModel() {
    }

    public CategoryRestViewModel(String name, byte[] picture) {
        this.name = name;
        this.picture = picture;
    }

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
