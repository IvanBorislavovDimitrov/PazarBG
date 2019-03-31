package com.ivan.pazar.persistence.model.service;

public class MessageServiceModel extends IdServiceModel {

    private String content;

    private String addedOn;

    private UserServiceModel sender;

    private UserServiceModel receiver;

    private AdvertisementServiceModel advertisement;

    public UserServiceModel getSender() {
        return sender;
    }

    public void setSender(UserServiceModel sender) {
        this.sender = sender;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public AdvertisementServiceModel getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(AdvertisementServiceModel advertisement) {
        this.advertisement = advertisement;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserServiceModel getReceiver() {
        return receiver;
    }

    public void setReceiver(UserServiceModel receiver) {
        this.receiver = receiver;
    }
}
