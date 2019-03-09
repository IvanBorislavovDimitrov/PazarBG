package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Category;
import com.ivan.pazar.persistence.dto.service.CategoryAddServiceModel;
import com.ivan.pazar.persistence.dto.service.CategoryServiceModel;
import com.ivan.pazar.persistence.repository.CategoryRepository;
import com.ivan.pazar.persistence.service.api.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryServiceModel save(CategoryAddServiceModel categoryAddServiceModel) {
        return modelMapper.map(categoryRepository.save(modelMapper.map(categoryAddServiceModel, Category.class)), CategoryServiceModel.class);
    }
}
