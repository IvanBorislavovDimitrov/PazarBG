package com.ivan.pazar.web.controller.view.category;

import com.ivan.pazar.persistence.dto.binding.CategoryAddBindingModel;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
        if (!model.containsAttribute(ViewConstants.INVALID_CATEGORY_FORM)) {
            model.addAttribute(ViewConstants.INVALID_CATEGORY_FORM, new CategoryAddServiceModel());
        }

        return renderView(ViewConstants.VIEWS_CATEGORY_ADD, model);
    }

    @PostMapping("/new")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ModelAndView addCategoryConfirm(@ModelAttribute @Valid CategoryAddBindingModel categoryAddBindingModel,
                                    BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(ViewConstants.INVALID_CATEGORY_FORM, categoryAddBindingModel);

            return redirect(ViewConstants.REDIRECT_CATEGORY_ADD);
        }

        categoryService.save(modelMapper.map(categoryAddBindingModel, CategoryAddServiceModel.class));

        return redirect(ViewConstants.REDIRECT_INDEX);
    }
}
