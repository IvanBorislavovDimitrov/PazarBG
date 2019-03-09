package com.ivan.pazar.persistence.dto.service;

public class CommentServiceModel extends IdServiceModel {

    private String content;

    private UserServiceModel author;

    private AdvertisementServiceModel advertisement;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserServiceModel getAuthor() {
        return author;
    }

    public void setAuthor(UserServiceModel author) {
        this.author = author;
    }

    public AdvertisementServiceModel getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(AdvertisementServiceModel advertisement) {
        this.advertisement = advertisement;
    }
}
