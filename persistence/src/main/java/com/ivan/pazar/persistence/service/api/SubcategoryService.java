package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.persistence.dto.service.SubcategoryAddServiceModel;
import com.ivan.pazar.persistence.dto.service.SubcategoryServiceModel;

import java.util.List;

public interface SubcategoryService {

    SubcategoryServiceModel save(SubcategoryAddServiceModel subcategoryAddServiceModel);

    List<SubcategoryServiceModel> getAll();
}
