package com.ivan.pazar.persistence.dto.service;

import com.ivan.pazar.domain.model.enums.UserRole;

import java.util.ArrayList;
import java.util.List;

public class RoleServiceModel extends IdServiceModel {

    private UserRole userRole;

    private List<UserServiceModel> users;

    public RoleServiceModel() {
        users = new ArrayList<>();
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public List<UserServiceModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserServiceModel> users) {
        this.users = users;
    }
}
