package com.ivan.pazar.model.dto.service;

import java.util.ArrayList;
import java.util.List;

public class CategoryServiceModel extends IdServiceModel {

    private String name;

    private List<AdvertisementServiceModel> advertisements;

    private List<SubcategoryServiceModel> subcategories;

    public CategoryServiceModel() {
        advertisements = new ArrayList<>();
        subcategories = new ArrayList<>();
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

    public List<SubcategoryServiceModel> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<SubcategoryServiceModel> subcategories) {
        this.subcategories = subcategories;
    }
}
