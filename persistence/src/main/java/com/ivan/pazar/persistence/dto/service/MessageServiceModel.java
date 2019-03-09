package com.ivan.pazar.persistence.dto.service;

public class MessageServiceModel extends IdServiceModel {

    private String content;

    private UserServiceModel sender;

    private UserServiceModel receiver;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserServiceModel getSender() {
        return sender;
    }

    public void setSender(UserServiceModel sender) {
        this.sender = sender;
    }

    public UserServiceModel getReceiver() {
        return receiver;
    }

    public void setReceiver(UserServiceModel receiver) {
        this.receiver = receiver;
    }
}
