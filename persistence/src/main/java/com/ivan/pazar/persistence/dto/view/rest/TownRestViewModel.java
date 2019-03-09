package com.ivan.pazar.persistence.dto.view.rest;

public class TownRestViewModel implements Comparable<TownRestViewModel> {

    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(TownRestViewModel townRestViewModel) {
        return this.name.compareTo(townRestViewModel.name);
    }
}
