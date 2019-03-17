package com.ivan.pazar.web.model.view.rest;

import java.util.HashSet;
import java.util.Set;

public class UserRestViewModel {

    private String username;

    private Set<String> roles;

    public UserRestViewModel() {
        roles = new HashSet<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
