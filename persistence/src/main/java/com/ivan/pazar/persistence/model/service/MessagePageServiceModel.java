package com.ivan.pazar.persistence.model.service;

import java.util.ArrayList;
import java.util.List;

public class MessagePageServiceModel {

    private int pages;

    private List<MessageServiceModel> messageServiceModels;

    public MessagePageServiceModel() {
        messageServiceModels = new ArrayList<>();
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<MessageServiceModel> getMessageServiceModels() {
        return messageServiceModels;
    }

    public void setMessageServiceModels(List<MessageServiceModel> messageServiceModels) {
        this.messageServiceModels = messageServiceModels;
    }
}
