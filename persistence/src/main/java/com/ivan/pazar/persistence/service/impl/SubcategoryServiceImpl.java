package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Subcategory;
import com.ivan.pazar.persistence.constants.Messages;
import com.ivan.pazar.persistence.model.service.SubcategoryAddServiceModel;
import com.ivan.pazar.persistence.model.service.SubcategoryServiceModel;
import com.ivan.pazar.persistence.repository.SubcategoryRepository;
import com.ivan.pazar.persistence.service.service_api.SubcategoryServiceExtended;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SubcategoryServiceImpl implements SubcategoryServiceExtended {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubcategoryServiceImpl.class);

    private final SubcategoryRepository subcategoryRepository;
    private final ModelMapper modelMapper;
    private final CategoryServiceImpl categoryService;

    @Autowired
    public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository, ModelMapper modelMapper, CategoryServiceImpl categoryService) {
        this.subcategoryRepository = subcategoryRepository;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
    }

    @Override
    public SubcategoryServiceModel save(SubcategoryAddServiceModel subcategoryAddServiceModel) {
        LOGGER.info(Messages.ADDING_SUBCATEGORY);
        Subcategory subcategory = modelMapper.map(subcategoryAddServiceModel, Subcategory.class);
        subcategory.setCategory(categoryService.findByName(subcategoryAddServiceModel.getCategory()));

        return modelMapper.map(subcategoryRepository.save(subcategory), SubcategoryServiceModel.class);
    }

    @Override
    public List<SubcategoryServiceModel> getAll() {
        return subcategoryRepository.findAll().stream()
                .map(subcategory -> modelMapper.map(subcategory, SubcategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SubcategoryServiceModel> getAllByCategory(String region) {
        return subcategoryRepository.findAllByCategoryNameLike(region)
                .stream()
                .map(subcategory -> modelMapper.map(subcategory, SubcategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Subcategory getSubcategoryByName(String subcategory) {
        return subcategoryRepository.findByName(subcategory);
    }
}
