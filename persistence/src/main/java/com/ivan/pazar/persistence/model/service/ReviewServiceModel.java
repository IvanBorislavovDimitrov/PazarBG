package com.ivan.pazar.persistence.model.service;

public class ReviewServiceModel {

    private String id;

    private String text;

    private UserServiceModel user;

    private AdvertisementServiceModel advertisement;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public AdvertisementServiceModel getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(AdvertisementServiceModel advertisement) {
        this.advertisement = advertisement;
    }
}
