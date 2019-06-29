package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Category;
import com.ivan.pazar.persistence.model.service.CategoryAddServiceModel;
import com.ivan.pazar.persistence.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.*;

public class CategoryServiceTests {

    @Mock
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        modelMapper = new ModelMapper();
        categoryService = new CategoryServiceImpl(categoryRepository, modelMapper);
    }

    @Test
    public void categoryService_save_saveIsInvoked() {
        String categoryName = "auto";
        CategoryAddServiceModel categoryAddServiceModel = mock(CategoryAddServiceModel.class);
        when(categoryAddServiceModel.getName()).thenReturn(categoryName);
        Category category = mock(Category.class);
        when(categoryRepository.save(any())).thenReturn(category);
        categoryService.save(categoryAddServiceModel);
        verify(categoryRepository).save(any());
    }

    @Test
    public void categoryService_findAll_categoryRepositoryFindAllIsInvoked() {
        categoryService.findAll();
        verify(categoryRepository).findAll();
    }

    @Test
    public void categoryService_findByName_categoryRepositoryFindByNameIsInvoked() {
        categoryService.findCategoryByName("category");
        verify(categoryRepository).findByName("category");
    }
}
