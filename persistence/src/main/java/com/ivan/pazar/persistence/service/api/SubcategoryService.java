package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.persistence.model.service.SubcategoryAddServiceModel;
import com.ivan.pazar.persistence.model.service.SubcategoryServiceModel;

import java.util.List;

public interface SubcategoryService {

    SubcategoryServiceModel save(SubcategoryAddServiceModel subcategoryAddServiceModel);

    List<SubcategoryServiceModel> getAll();
}
