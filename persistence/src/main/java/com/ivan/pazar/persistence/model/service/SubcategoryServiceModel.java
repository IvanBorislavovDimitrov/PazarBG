package com.ivan.pazar.persistence.model.service;

import com.ivan.pazar.domain.model.entity.Category;

import java.util.ArrayList;
import java.util.List;

public class SubcategoryServiceModel extends IdServiceModel {

    private String name;

    private Category category;

    private List<AdvertisementServiceModel> advertisements;

    public SubcategoryServiceModel() {
        advertisements = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<AdvertisementServiceModel> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<AdvertisementServiceModel> advertisements) {
        this.advertisements = advertisements;
    }
}
