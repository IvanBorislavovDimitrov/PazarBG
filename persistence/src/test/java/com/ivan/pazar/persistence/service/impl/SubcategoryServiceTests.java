package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Category;
import com.ivan.pazar.domain.model.entity.Subcategory;
import com.ivan.pazar.persistence.model.service.SubcategoryAddServiceModel;
import com.ivan.pazar.persistence.repository.SubcategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class SubcategoryServiceTests {

    @Mock
    private SubcategoryRepository subcategoryRepository;

    @Mock
    private CategoryServiceImpl categoryService;

    private SubcategoryServiceImpl subcategoryService;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        modelMapper = new ModelMapper();
        subcategoryService = new SubcategoryServiceImpl(subcategoryRepository, modelMapper, categoryService);
    }

    @Test
    public void subcategoryService_save_subcategorySaved() {
        SubcategoryAddServiceModel subcategoryAddServiceModel = mock(SubcategoryAddServiceModel.class);
        when(categoryService.findCategoryByName(anyString())).thenReturn(mock((Category.class)));
        when(subcategoryRepository.save(any())).thenReturn(mock(Subcategory.class));
        subcategoryService.save(subcategoryAddServiceModel);
        verify(subcategoryRepository).save(any());
    }


}
