package com.ivan.pazar.web.controller.view.subcategory;

import com.ivan.pazar.persistence.dto.binding.SubcategoryAddBindingModel;
import com.ivan.pazar.persistence.dto.service.SubcategoryAddServiceModel;
import com.ivan.pazar.persistence.service.api.SubcategoryService;
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

import javax.validation.Valid;

@Controller
@RequestMapping(value = "subcategories/")
public class SubcategoryController extends BaseController {

    private final SubcategoryService subcategoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public SubcategoryController(SubcategoryService subcategoryService, ModelMapper modelMapper) {
        this.subcategoryService = subcategoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/new")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ModelAndView newSubcategory(Model model) {
        return renderView(ViewConstants.VIEWS_SUBCATEGORY_ADD, model);
    }

    @PostMapping("/new")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ModelAndView newSubcategoryConfirm(@ModelAttribute @Valid SubcategoryAddBindingModel subcategoryAddBindingModel,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return redirect(ViewConstants.REDIRECT_SUBCATEGORY_ADD);
        }

        subcategoryService.save(modelMapper.map(subcategoryAddBindingModel, SubcategoryAddServiceModel.class));

        return redirect(ViewConstants.REDIRECT_INDEX);
    }
}
