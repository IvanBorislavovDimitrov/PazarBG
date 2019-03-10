package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Subcategory;
import com.ivan.pazar.persistence.dto.service.SubcategoryAddServiceModel;
import com.ivan.pazar.persistence.dto.service.SubcategoryServiceModel;
import com.ivan.pazar.persistence.repository.CategoryRepository;
import com.ivan.pazar.persistence.repository.SubcategoryRepository;
import com.ivan.pazar.persistence.service.api.SubcategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public SubcategoryServiceModel save(SubcategoryAddServiceModel subcategoryAddServiceModel) {
        Subcategory subcategory = modelMapper.map(subcategoryAddServiceModel, Subcategory.class);
        subcategory.setCategory(categoryRepository.findByName(subcategoryAddServiceModel.getCategory()));

        return modelMapper.map(subcategoryRepository.save(subcategory), SubcategoryServiceModel.class);
    }

    @Override
    public List<SubcategoryServiceModel> getAll() {
        return subcategoryRepository.findAll().stream()
                .map(subcategory -> modelMapper.map(subcategory, SubcategoryServiceModel.class))
                .collect(Collectors.toList());
    }
}
