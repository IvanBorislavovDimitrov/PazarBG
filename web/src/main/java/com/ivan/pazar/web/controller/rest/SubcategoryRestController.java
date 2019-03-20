package com.ivan.pazar.web.controller.rest;

import com.ivan.pazar.persistence.service.api.SubcategoryService;
import com.ivan.pazar.web.model.view.rest.SubcategoryRestViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/subcategories")
public class SubcategoryRestController {

    private final SubcategoryService subcategoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public SubcategoryRestController(SubcategoryService subcategoryService, ModelMapper modelMapper) {
        this.subcategoryService = subcategoryService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "/all", produces = "application/json")
    public List<SubcategoryRestViewModel> getSubcategoriesByCategory(@RequestParam(name = "category", defaultValue = "") String category) {
        return subcategoryService.getAllByCategory(category)
                .stream()
                .map(subcategoryServiceModel -> modelMapper.map(subcategoryServiceModel, SubcategoryRestViewModel.class))
                .sorted()
                .collect(Collectors.toList());
    }
}
