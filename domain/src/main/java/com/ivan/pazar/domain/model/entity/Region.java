package com.ivan.pazar.domain.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "regions")
public class Region extends IdEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "region", targetEntity = Town.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Town> towns;

    @OneToMany(mappedBy = "region", targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;

    public Region() {
        towns = new ArrayList<>();
        users = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Town> getTowns() {
        return towns;
    }

    public void setTowns(List<Town> towns) {
        this.towns = towns;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return name;
    }
}
