package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Category;
import com.ivan.pazar.persistence.constants.Messages;
import com.ivan.pazar.persistence.model.service.CategoryAddServiceModel;
import com.ivan.pazar.persistence.model.service.CategoryServiceModel;
import com.ivan.pazar.persistence.repository.CategoryRepository;
import com.ivan.pazar.persistence.service.api.MessageService;
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
        LOGGER.info(Messages.ADDING_CATEGORY);
        return modelMapper.map(categoryRepository.save(modelMapper.map(categoryAddServiceModel, Category.class)), CategoryServiceModel.class);
    }

    @Override
    public List<CategoryServiceModel> findAll() {
        LOGGER.info(Messages.FINDING_ALL_CATEGORIES);
        return categoryRepository.findAll().stream()
                .map(category -> modelMapper.map(category, CategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Category findCategoryByName(String category) {
        LOGGER.info(Messages.FINDING_CATEGORY_BY_ID);
        return categoryRepository.findByName(category);
    }
}
