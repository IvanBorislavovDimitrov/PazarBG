package com.ivan.pazar.web.model.view;

public class MessageViewModel {

    private String content;

    private String receiver;

    private String sender;

    private String addedOn;

    private AdvertisementViewModel advertisement;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public AdvertisementViewModel getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(AdvertisementViewModel advertisement) {
        this.advertisement = advertisement;
    }
}
