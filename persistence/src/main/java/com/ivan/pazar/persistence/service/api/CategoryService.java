package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.persistence.dto.service.CategoryAddServiceModel;
import com.ivan.pazar.persistence.dto.service.CategoryServiceModel;

public interface CategoryService {

    CategoryServiceModel save(CategoryAddServiceModel categoryAddServiceModel);
}
