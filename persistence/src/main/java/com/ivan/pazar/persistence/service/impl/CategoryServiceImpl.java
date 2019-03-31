package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Category;
import com.ivan.pazar.persistence.model.service.CategoryAddServiceModel;
import com.ivan.pazar.persistence.model.service.CategoryServiceModel;
import com.ivan.pazar.persistence.repository.CategoryRepository;
import com.ivan.pazar.persistence.service.service_api.CategoryServiceExtended;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryServiceExtended {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

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

    @Override
    public List<CategoryServiceModel> getAll() {
        return categoryRepository.findAll().stream()
                .map(category -> modelMapper.map(category, CategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    public Category findByName(String category) {
        return categoryRepository.findByName(category);
    }

    @Override
    public Category getCategoryByName(String category) {
        return categoryRepository.findByName(category);
    }
}
