package com.ivan.pazar.web.controller.view.about;

import com.ivan.pazar.persistence.service.api.UserService;
import com.ivan.pazar.web.config.UserConfiguration;
import com.ivan.pazar.web.constants.WebConstants;
import com.ivan.pazar.web.controller.view.BaseController;
import com.ivan.pazar.web.model.view.UserProfileViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactUsController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserConfiguration userConfiguration;

    public ContactUsController(UserService userService, ModelMapper modelMapper, UserConfiguration userConfiguration) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userConfiguration = userConfiguration;
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

    @ModelAttribute(WebConstants.USER)
    public UserProfileViewModel userProfileViewModel() {
        return new UserProfileViewModel();
    }
}
