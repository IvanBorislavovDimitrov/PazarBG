package com.ivan.pazar.web.controller.view.about;

import com.ivan.pazar.persistence.model.service.ContactUsMessageServiceModel;
import com.ivan.pazar.persistence.service.api.ContactUsMessageService;
import com.ivan.pazar.persistence.service.api.UserService;
import com.ivan.pazar.web.config.UserConfiguration;
import com.ivan.pazar.web.constants.WebConstants;
import com.ivan.pazar.web.controller.view.BaseController;
import com.ivan.pazar.web.model.view.ContactUsMessageViewModel;
import com.ivan.pazar.web.model.view.UserProfileViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ContactUsController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserConfiguration userConfiguration;
    private final ContactUsMessageService contactUsMessageService;

    public ContactUsController(UserService userService, ModelMapper modelMapper, UserConfiguration userConfiguration, ContactUsMessageService contactUsMessageService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userConfiguration = userConfiguration;
        this.contactUsMessageService = contactUsMessageService;
    }

    @GetMapping("/contact-us")
    public ModelAndView contactUs(Model model) {
        if (WebConstants.ANONYMOUS_USER.equalsIgnoreCase(userConfiguration.loggedUserUsername())) {
            model.addAttribute(WebConstants.USER, new UserProfileViewModel());
        } else {
            UserProfileViewModel userProfileViewModel =
                    modelMapper.map(userService.findUserByUsername(userConfiguration.loggedUserUsername()), UserProfileViewModel.class);
            model.addAttribute(WebConstants.USER, userProfileViewModel);
        }



        return renderView(WebConstants.VIEWS_CONTACT_US, model);
    }

    @PostMapping("/contact-us")
    public ModelAndView contactUsConfirm(@ModelAttribute(WebConstants.CONTACT_US_FORM) @Valid ContactUsMessageViewModel contactUsMessageViewModel, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return renderView(WebConstants.VIEWS_CONTACT_US, model);
        }

        ContactUsMessageServiceModel contactUsMessageServiceModel = modelMapper.map(contactUsMessageViewModel, ContactUsMessageServiceModel.class);

        contactUsMessageService.save(contactUsMessageServiceModel);

        return redirect(WebConstants.REDIRECT_INDEX);
    }

    @ModelAttribute(WebConstants.USER)
    public UserProfileViewModel userProfileViewModel() {
        return new UserProfileViewModel();
    }
}
