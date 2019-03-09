package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.persistence.dto.service.SubcategoryServiceModel;
import com.ivan.pazar.persistence.repository.SubcategoryRepository;
import com.ivan.pazar.persistence.service.api.SubcategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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
