package com.ivan.pazar.domain.model.dto.service;

import java.util.ArrayList;
import java.util.List;

public class RoleServiceModel extends IdServiceModel {

    private String name;

    private List<UserServiceModel> users;

    public RoleServiceModel() {
        users = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserServiceModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserServiceModel> users) {
        this.users = users;
    }
}
