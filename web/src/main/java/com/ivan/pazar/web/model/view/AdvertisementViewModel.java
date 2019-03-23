package com.ivan.pazar.web.model.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AdvertisementViewModel {

    private String title;
    private BigDecimal price;
    private String description;
    private List<String> pictures;

    public AdvertisementViewModel() {
        pictures = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }
}
