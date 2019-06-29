package com.ivan.pazar.web.model.binding;

import javax.validation.constraints.NotEmpty;

public class TownAddBindingModel {

    private static final String INVALID_TOWN_NAME = "Invalid town name";
    private static final String INVALID_REGION_NAME = "Invalid region name";

    @NotEmpty(message = INVALID_TOWN_NAME)
    private String name;

    @NotEmpty(message = INVALID_REGION_NAME)
    private String region;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
