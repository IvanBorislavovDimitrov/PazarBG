package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.dto.service.CategoryServiceModel;
import com.ivan.pazar.persistence.repository.CategoryRepository;
import com.ivan.pazar.persistence.service.api.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryServiceModel save(CategoryServiceModel categoryServiceModel) {
        return null;
    }
}
