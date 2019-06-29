package com.ivan.pazar.persistence.model.service;

public class VideoServiceModel extends IdServiceModel {

    private String name;

    private AdvertisementServiceModel advertisement;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AdvertisementServiceModel getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(AdvertisementServiceModel advertisement) {
        this.advertisement = advertisement;
    }
}
