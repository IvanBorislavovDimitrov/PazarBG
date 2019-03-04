package com.ivan.pazar.model.dto.service;

import java.util.ArrayList;
import java.util.List;

public class RegionServiceModel extends IdServiceModel {

    private String name;

    private List<TownServiceModel> towns;

    private List<UserServiceModel> users;

    public RegionServiceModel() {
        users = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TownServiceModel> getTowns() {
        return towns;
    }

    public void setTowns(List<TownServiceModel> towns) {
        this.towns = towns;
    }

    public List<UserServiceModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserServiceModel> users) {
        this.users = users;
    }
}
