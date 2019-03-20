package com.ivan.pazar.web.controller.view.advert;

import com.ivan.pazar.web.constants.ViewConstants;
import com.ivan.pazar.web.model.binding.AdvertisementBindingModel;
import com.ivan.pazar.web.model.view.AdvertisementViewModel;
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
public class AdvertNewController extends AdvertBaseController {

    @GetMapping("/new")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView advertNew(Model model) {
        model.addAttribute(ViewConstants.ADVERT, advertisementViewModel());
        return renderView(ViewConstants.VIEWS_NEW_ADVERT, model);
    }

    @ModelAttribute(ViewConstants.ADVERT)
    public AdvertisementBindingModel advertisementViewModel() {
        return new AdvertisementBindingModel();
    }

    @PostMapping("/new")
    public ModelAndView advertNewConfirm(@ModelAttribute(ViewConstants.ADVERT) @Valid AdvertisementBindingModel advertisementBindingModel, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return renderView(ViewConstants.VIEWS_NEW_ADVERT, model);
        }

        return redirect(ViewConstants.REDIRECT_INDEX);
    }
}
