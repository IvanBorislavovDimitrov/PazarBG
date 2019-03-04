package com.ivan.pazar.model.dto.service;

import java.util.ArrayList;
import java.util.List;

public class TownServiceModel extends IdServiceModel {

    private String name;

    private RegionServiceModel region;

    private List<UserServiceModel> users;

    private List<AdvertisementServiceModel> advertisements;

    public TownServiceModel() {
        users = new ArrayList<>();
        advertisements = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RegionServiceModel getRegion() {
        return region;
    }

    public void setRegion(RegionServiceModel region) {
        this.region = region;
    }

    public List<UserServiceModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserServiceModel> users) {
        this.users = users;
    }

    public List<AdvertisementServiceModel> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<AdvertisementServiceModel> advertisements) {
        this.advertisements = advertisements;
    }
}
