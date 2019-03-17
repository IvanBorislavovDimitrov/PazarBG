package com.ivan.pazar.web.controller.view.user;

import com.ivan.pazar.persistence.model.service.UserServiceModel;
import com.ivan.pazar.persistence.model.service.register.UserServiceBindingModel;
import com.ivan.pazar.persistence.service.api.UserService;
import com.ivan.pazar.web.config.UserConfiguration;
import com.ivan.pazar.web.constants.ViewConstants;
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

        model.addAttribute(ViewConstants.USER, userRegisterBindingModel);

        return renderView(ViewConstants.VIEWS_USER_EDIT, model);
    }

    @PostMapping("/edit")
    public ModelAndView editConfirm(@ModelAttribute(ViewConstants.USER) @Valid UserEditBindingModel userEditBindingModel, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return renderView(ViewConstants.VIEWS_USER_EDIT, model);
        }

        userService.updateUser(userConfiguration.loggedUserUsername(),
                modelMapper.map(userEditBindingModel , UserServiceBindingModel.class));

        return redirect(ViewConstants.REDIRECT_USER_EDIT);
    }
}
