package com.ivan.pazar.web.controller.rest;

import com.ivan.pazar.web.model.view.rest.CategoryRestViewModel;
import com.ivan.pazar.persistence.service.api.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/categories")
public class CategoryRestController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public CategoryRestController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<CategoryRestViewModel> categories() throws IOException {

        return categoryService.findAll().stream()
                .map(categoryServiceModel -> modelMapper.map(categoryServiceModel, CategoryRestViewModel.class))
                .collect(Collectors.toList());
    }
}
