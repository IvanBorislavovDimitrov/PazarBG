package com.ivan.pazar.web.model.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SubcategoryAddBindingModel {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
