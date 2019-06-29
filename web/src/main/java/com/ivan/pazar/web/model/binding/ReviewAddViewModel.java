package com.ivan.pazar.web.model.binding;

import javax.validation.constraints.NotEmpty;

public class ReviewAddViewModel {

    private static final String INVALID_TEXT = "Invalid text";

    @NotEmpty(message = INVALID_TEXT)
    private String text;

    private String username;

    private String advertisementId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(String advertisementId) {
        this.advertisementId = advertisementId;
    }
}
