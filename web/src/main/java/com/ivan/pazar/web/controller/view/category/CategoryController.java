package com.ivan.pazar.web.controller.view.category;

import com.ivan.pazar.persistence.model.service.CategoryAddServiceModel;
import com.ivan.pazar.persistence.service.api.CategoryService;
import com.ivan.pazar.web.constants.WebConstants;
import com.ivan.pazar.web.controller.view.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping(value = "/categories")
public class CategoryController extends BaseController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/new")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'ROOT')")
    public ModelAndView addCategory(Model model) {
        return renderView(WebConstants.VIEWS_CATEGORY_ADD, model);
    }

    @PostMapping("/new")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'ROOT')")
    public ModelAndView addCategoryConfirm(@RequestParam("name") String name,
                                           @RequestParam("multipartPicture") MultipartFile picture) throws IOException {
        CategoryAddServiceModel categoryAddServiceModel = new CategoryAddServiceModel();

        categoryAddServiceModel.setName(name);
        categoryAddServiceModel.setPicture(picture.getBytes());
        categoryService.save(categoryAddServiceModel);

        return redirect(WebConstants.REDIRECT_INDEX);
    }
}
