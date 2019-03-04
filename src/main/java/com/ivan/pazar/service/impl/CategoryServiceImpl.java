package com.ivan.pazar.service.impl;

import com.ivan.pazar.model.dto.service.CategoryServiceModel;
import com.ivan.pazar.repository.CategoryRepository;
import com.ivan.pazar.service.api.CategoryService;
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
