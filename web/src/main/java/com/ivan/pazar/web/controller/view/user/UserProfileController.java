package com.ivan.pazar.web.controller.view.user;

import com.ivan.pazar.persistence.service.api.UserService;
import com.ivan.pazar.web.config.UserConfiguration;
import com.ivan.pazar.web.constants.ViewConstants;
import com.ivan.pazar.web.model.view.UserProfileViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserProfileController extends UserBaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserConfiguration userConfiguration;

    @Autowired
    public UserProfileController(UserService userService, ModelMapper modelMapper, UserConfiguration userConfiguration) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userConfiguration = userConfiguration;
    }

    // TODO: Add user register date
    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profile(Model model) {
        String loggedUserUsername = userConfiguration.loggedUserUsername();
        UserProfileViewModel userProfileViewModel =
                modelMapper.map(userService.findUserByUsername(loggedUserUsername), UserProfileViewModel.class);

        model.addAttribute(ViewConstants.USER, userProfileViewModel);

        return renderView(ViewConstants.VIEWS_USER_PROFILE, model);
    }

}
