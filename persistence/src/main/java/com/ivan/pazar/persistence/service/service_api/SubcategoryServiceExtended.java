package com.ivan.pazar.persistence.service.service_api;

import com.ivan.pazar.domain.model.entity.Subcategory;
import com.ivan.pazar.persistence.service.api.SubcategoryService;

public interface SubcategoryServiceExtended extends SubcategoryService {
    Subcategory getSubcategoryByName(String subcategory);
}
