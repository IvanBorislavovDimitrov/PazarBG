package com.ivan.pazar.web.controller.view.region;

import com.ivan.pazar.persistence.model.service.RegionAddServiceModel;
import com.ivan.pazar.persistence.service.api.RegionService;
import com.ivan.pazar.web.constants.WebConstants;
import com.ivan.pazar.web.model.binding.RegionAddBindingModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegionAddController extends RegionBaseController {

    private final RegionService regionService;
    private final ModelMapper modelMapper;

    @Autowired
    public RegionAddController(RegionService regionService, ModelMapper modelMapper) {
        this.regionService = regionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'ROOT')")
    public ModelAndView addRegion(Model model) {
        return renderView(WebConstants.VIEWS_ADD_REGION, model);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'ROOT')")
    public ModelAndView addRegionConfirm(@Valid @ModelAttribute(WebConstants.REGION) RegionAddBindingModel regionAddBindingModel, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return renderView(WebConstants.VIEWS_ADD_REGION, model);
        }

        regionService.save(modelMapper.map(regionAddBindingModel, RegionAddServiceModel.class));

        return redirect(WebConstants.REDIRECT_INDEX);
    }

}
