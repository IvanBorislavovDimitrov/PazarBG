package com.ivan.pazar.persistence.model.service;

import java.util.HashSet;
import java.util.Set;

public class ReviewRestServiceModel {

    private String id;
    private String text;
    private String username;
    private String addedOn;
    private Set<String> roles;

    public ReviewRestServiceModel() {
        roles = new HashSet<>();
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }
}
