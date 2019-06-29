package com.ivan.pazar.persistence.service.service_api;

import com.ivan.pazar.domain.model.entity.Category;
import com.ivan.pazar.persistence.service.api.CategoryService;

public interface CategoryServiceExtended extends CategoryService {
    Category findCategoryByName(String category);
}
