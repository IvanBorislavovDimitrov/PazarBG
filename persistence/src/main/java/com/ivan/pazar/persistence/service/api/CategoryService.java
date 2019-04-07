package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.persistence.model.service.CategoryAddServiceModel;
import com.ivan.pazar.persistence.model.service.CategoryServiceModel;

import java.util.List;

public interface CategoryService {

    CategoryServiceModel save(CategoryAddServiceModel categoryAddServiceModel);

    List<CategoryServiceModel> findAll();
}
