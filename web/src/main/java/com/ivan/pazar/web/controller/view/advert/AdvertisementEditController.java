package com.ivan.pazar.web.controller.view.advert;

import com.ivan.pazar.domain.model.entity.Advertisement;
import com.ivan.pazar.persistence.model.service.AdvertisementAddServiceModel;
import com.ivan.pazar.persistence.model.service.AdvertisementViewServiceModel;
import com.ivan.pazar.persistence.service.api.AdvertisementService;
import com.ivan.pazar.web.constants.WebConstants;
import com.ivan.pazar.web.model.binding.AdvertisementBindingModel;
import com.ivan.pazar.web.model.view.AdvertisementViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AdvertisementEditController extends AdvertisementBaseController {

    private final AdvertisementService advertisementService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdvertisementEditController(AdvertisementService advertisementService, ModelMapper modelMapper) {
        this.advertisementService = advertisementService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView edit(@RequestParam("advertId") String advertId, Model model) {
        AdvertisementViewModel advertisementViewModel = modelMapper.map(advertisementService.findById(advertId), AdvertisementViewModel.class);
        model.addAttribute(WebConstants.ADVERT, advertisementViewModel);

        return renderView(WebConstants.VIEWS_EDIT_ADVERT, model);
    }

    @PatchMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editConfirm(@ModelAttribute(WebConstants.ADVERT) @Valid AdvertisementBindingModel advertisementBindingModel, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(WebConstants.ID, advertisementBindingModel.getId());
            return renderView(WebConstants.VIEWS_EDIT_ADVERT, model);
        }

        advertisementService.edit(modelMapper.map(advertisementBindingModel, AdvertisementAddServiceModel.class));

        return redirect(String.format(WebConstants.REDIRECT_TO_ADVERT, advertisementBindingModel.getId()));
    }
}
