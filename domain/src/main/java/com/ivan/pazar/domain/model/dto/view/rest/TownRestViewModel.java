package com.ivan.pazar.domain.model.dto.view.rest;

import java.util.Objects;

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
