package com.ivan.pazar.persistence.model.service;

import com.ivan.pazar.domain.model.enums.UserRole;

import java.util.Set;

public class UserChangeRoleServiceModel {

    private String username;

    private Set<UserRole> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
}
