package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.persistence.dto.service.CategoryAddServiceModel;
import com.ivan.pazar.persistence.dto.service.CategoryServiceModel;

import java.util.List;

public interface CategoryService {

    CategoryServiceModel save(CategoryAddServiceModel categoryAddServiceModel);

    List<CategoryServiceModel> getAll();
}
