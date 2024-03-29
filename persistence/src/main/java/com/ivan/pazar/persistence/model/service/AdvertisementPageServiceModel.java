package com.ivan.pazar.persistence.model.service;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementPageServiceModel {

    private int pages;

    private List<AdvertisementViewServiceModel> advertisementViewServiceModels;

    public AdvertisementPageServiceModel() {
        advertisementViewServiceModels = new ArrayList<>();
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<AdvertisementViewServiceModel> getAdvertisementViewServiceModels() {
        return advertisementViewServiceModels;
    }

    public void setAdvertisementViewServiceModels(List<AdvertisementViewServiceModel> advertisementViewServiceModels) {
        this.advertisementViewServiceModels = advertisementViewServiceModels;
    }
}
