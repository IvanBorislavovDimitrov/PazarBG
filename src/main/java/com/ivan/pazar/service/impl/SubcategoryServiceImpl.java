package com.ivan.pazar.service.impl;

import com.ivan.pazar.model.dto.service.SubcategoryServiceModel;
import com.ivan.pazar.repository.SubcategoryRepository;
import com.ivan.pazar.service.api.SubcategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;
    private final ModelMapper modelMapper;

    public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository, ModelMapper modelMapper) {
        this.subcategoryRepository = subcategoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SubcategoryServiceModel save(SubcategoryServiceModel subcategoryServiceModel) {
        return null;
    }
}
