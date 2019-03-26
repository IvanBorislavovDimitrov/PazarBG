package com.ivan.pazar.web.model.view.rest;

import java.util.HashSet;
import java.util.Set;

public class ReviewRestViewModel {

    private String id;
    private String text;
    private String username;
    private String addedOn;
    private String loggedUserUsername;
    private Set<String> loggedUserRoles;

    public ReviewRestViewModel() {
        loggedUserRoles = new HashSet<>();
    }

    public Set<String> getLoggedUserRoles() {
        return loggedUserRoles;
    }

    public void setLoggedUserRoles(Set<String> loggedUserRoles) {
        this.loggedUserRoles = loggedUserRoles;
    }

    public String getLoggedUserUsername() {
        return loggedUserUsername;
    }

    public void setLoggedUserUsername(String loggedUserUsername) {
        this.loggedUserUsername = loggedUserUsername;
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
