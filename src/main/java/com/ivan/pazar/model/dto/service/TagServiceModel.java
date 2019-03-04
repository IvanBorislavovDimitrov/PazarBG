package com.ivan.pazar.model.dto.service;

import java.util.ArrayList;
import java.util.List;

public class TagServiceModel extends IdServiceModel {

    private String name;

    private List<AdvertisementServiceModel> advertisements;

    public TagServiceModel() {
        advertisements = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AdvertisementServiceModel> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<AdvertisementServiceModel> advertisements) {
        this.advertisements = advertisements;
    }
}
