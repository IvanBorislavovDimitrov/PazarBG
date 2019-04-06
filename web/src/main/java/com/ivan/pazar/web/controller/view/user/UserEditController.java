package com.ivan.pazar.web.controller.view.user;

import com.ivan.pazar.persistence.exceptions.UserException;
import com.ivan.pazar.persistence.model.service.UserChangePassword;
import com.ivan.pazar.persistence.model.service.UserServiceModel;
import com.ivan.pazar.persistence.model.service.register.UserServiceBindingModel;
import com.ivan.pazar.persistence.service.api.UserService;
import com.ivan.pazar.web.config.UserConfiguration;
import com.ivan.pazar.web.constants.WebConstants;
import com.ivan.pazar.web.model.binding.UserChangePasswordBindingModel;
import com.ivan.pazar.web.model.binding.UserEditBindingModel;
import com.ivan.pazar.web.model.binding.UserRegisterBindingModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@PreAuthorize("isAuthenticated()")
public class UserEditController extends UserBaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserConfiguration userConfiguration;

    @Autowired
    public UserEditController(UserService userService, ModelMapper modelMapper, UserConfiguration userConfiguration) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userConfiguration = userConfiguration;
    }

    @GetMapping("/edit")
    public ModelAndView edit(Model model) {
        UserServiceModel userServiceModel = userService.findUserByUsername(userConfiguration.loggedUserUsername());
        UserRegisterBindingModel userRegisterBindingModel = modelMapper.map(userServiceModel, UserRegisterBindingModel.class);
        userRegisterBindingModel.setRegion(userServiceModel.getRegion().getName());
        userRegisterBindingModel.setTown(userServiceModel.getTown().getName());

        model.addAttribute(WebConstants.USER, userRegisterBindingModel);

        return renderView(WebConstants.VIEWS_USER_EDIT, model);
    }

    @PostMapping("/edit")
    public ModelAndView editConfirm(@ModelAttribute(WebConstants.USER) @Valid UserEditBindingModel userEditBindingModel, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return renderView(WebConstants.VIEWS_USER_EDIT, model);
        }

        try {
            userService.updateUser(userConfiguration.loggedUserUsername(),
                    modelMapper.map(userEditBindingModel, UserServiceBindingModel.class));
        } catch (UserException e) {
            model.addAttribute(WebConstants.INVALID_USER_FORM, e.getMessage());
            return renderView(WebConstants.VIEWS_USER_EDIT, model);
        }
        return redirect(WebConstants.REDIRECT_USER_PROFILE);
    }

    @GetMapping("/change-password")
    public ModelAndView changePassword(Model model) {
        return renderView(WebConstants.VIEWS_CHANGE_PASSWORD, model);
    }

    @PostMapping("/change-password")
    public ModelAndView changePasswordConfirm(@ModelAttribute(WebConstants.CHANGE_PASSWORD) @Valid UserChangePasswordBindingModel userChangePasswordBindingModel, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return renderView(WebConstants.VIEWS_CHANGE_PASSWORD, model);
        }
        try {
            userService.tryUpdatePassword(userConfiguration.loggedUserUsername(), modelMapper.map(userChangePasswordBindingModel,
                    UserChangePassword.class));
        } catch (UserException e) {
            model.addAttribute(WebConstants.INVALID_USER_FORM, e.getMessage());

            return renderView(WebConstants.VIEWS_CHANGE_PASSWORD, model);
        }
        return redirect(WebConstants.REDIRECT_USER_PROFILE);
    }

    @PostMapping("/edit/picture")
    public ModelAndView updateProfilePicture(@RequestParam("picture") MultipartFile picture) {

        userService.updateUserPicture(userConfiguration.loggedUserUsername(), picture);

        return redirect(WebConstants.REDIRECT_USER_PROFILE);
    }

    @ModelAttribute(WebConstants.CHANGE_PASSWORD)
    public UserChangePasswordBindingModel userChangePasswordBindingModel() {
        return new UserChangePasswordBindingModel();
    }
}
