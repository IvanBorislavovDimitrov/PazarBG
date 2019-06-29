package com.ivan.pazar.web.model.view;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementPageViewModel {

    private int pages;

    private List<AdvertisementViewModel> advertisementViewServiceModels;

    public AdvertisementPageViewModel() {
        advertisementViewServiceModels = new ArrayList<>();
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<AdvertisementViewModel> getAdvertisementViewServiceModels() {
        return advertisementViewServiceModels;
    }

    public void setAdvertisementViewServiceModels(List<AdvertisementViewModel> advertisementViewServiceModels) {
        this.advertisementViewServiceModels = advertisementViewServiceModels;
    }
}
