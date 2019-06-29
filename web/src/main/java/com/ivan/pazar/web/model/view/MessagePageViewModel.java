package com.ivan.pazar.web.model.view;

import java.util.ArrayList;
import java.util.List;

public class MessagePageViewModel {

    private int pages;

    private List<MessageViewModel> messageServiceModels;

    public MessagePageViewModel() {
        messageServiceModels = new ArrayList<>();
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<MessageViewModel> getMessageServiceModels() {
        return messageServiceModels;
    }

    public void setMessageServiceModels(List<MessageViewModel> messageServiceModels) {
        this.messageServiceModels = messageServiceModels;
    }
}
