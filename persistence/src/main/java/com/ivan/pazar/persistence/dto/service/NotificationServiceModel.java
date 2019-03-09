package com.ivan.pazar.persistence.dto.service;

public class NotificationServiceModel extends IdServiceModel {

    private String content;

    private AdvertisementServiceModel advertisement;

    private UserServiceModel user;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AdvertisementServiceModel getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(AdvertisementServiceModel advertisement) {
        this.advertisement = advertisement;
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }
}
