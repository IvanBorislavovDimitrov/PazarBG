package com.ivan.pazar.web.controller.view.advert;

import com.ivan.pazar.persistence.model.service.AdvertisementAddServiceModel;
import com.ivan.pazar.persistence.service.api.AdvertisementService;
import com.ivan.pazar.web.config.UserConfiguration;
import com.ivan.pazar.web.constants.WebConstants;
import com.ivan.pazar.web.model.binding.AdvertisementBindingModel;
import org.modelmapper.ModelMapper;
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
public class AdvertisementNewController extends AdvertisementBaseController {

    private final AdvertisementService advertisementService;
    private final ModelMapper modelMapper;
    private final UserConfiguration userConfiguration;

    public AdvertisementNewController(AdvertisementService advertisementService, ModelMapper modelMapper, UserConfiguration userConfiguration) {
        this.advertisementService = advertisementService;
        this.modelMapper = modelMapper;
        this.userConfiguration = userConfiguration;
    }

    @GetMapping("/new")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView advertNew(Model model) {
        model.addAttribute(WebConstants.ADVERT, advertisementViewModel());
        return renderView(WebConstants.VIEWS_NEW_ADVERT, model);
    }

    @ModelAttribute(WebConstants.ADVERT)
    public AdvertisementBindingModel advertisementViewModel() {
        return new AdvertisementBindingModel();
    }

    @PostMapping("/new")
    public ModelAndView advertNewConfirm(@ModelAttribute(WebConstants.ADVERT) @Valid AdvertisementBindingModel advertisementBindingModel, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return renderView(WebConstants.VIEWS_NEW_ADVERT, model);
        }

        advertisementService.save(userConfiguration.loggedUserUsername(), modelMapper.map(advertisementBindingModel, AdvertisementAddServiceModel.class));

        return redirect(WebConstants.REDIRECT_INDEX);
    }
}
