package com.ivan.pazar.web.controller.view.category;

import com.ivan.pazar.persistence.dto.service.CategoryAddServiceModel;
import com.ivan.pazar.persistence.service.api.CategoryService;
import com.ivan.pazar.web.constants.ViewConstants;
import com.ivan.pazar.web.controller.view.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping(value = "/categories")
public class CategoryController extends BaseController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/new")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ModelAndView addCategory(Model model) {
        return renderView(ViewConstants.VIEWS_CATEGORY_ADD, model);
    }

    @PostMapping("/new")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ModelAndView addCategoryConfirm(@RequestParam("name") String name,
                                           @RequestParam("multipartPicture") MultipartFile picture) throws IOException {
        CategoryAddServiceModel categoryAddServiceModel = new CategoryAddServiceModel();

        categoryAddServiceModel.setName(name);
        categoryAddServiceModel.setPicture(picture.getBytes());
        categoryService.save(categoryAddServiceModel);

        return redirect(ViewConstants.REDIRECT_INDEX);
    }
}
