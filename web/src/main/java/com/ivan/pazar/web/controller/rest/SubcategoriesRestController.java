package com.ivan.pazar.web.controller.rest;

import com.ivan.pazar.persistence.dto.view.rest.SubcategoryRestViewModel;
import com.ivan.pazar.persistence.service.api.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/subcategories")
public class SubcategoriesRestController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public SubcategoriesRestController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<SubcategoryRestViewModel> subcategories() {
        return categoryService.getAll().stream()
                .map(categoryServiceModel -> modelMapper.map(categoryServiceModel, SubcategoryRestViewModel.class))
                .collect(Collectors.toList());
    }
}
