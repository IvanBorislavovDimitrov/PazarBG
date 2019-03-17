package com.ivan.pazar.web.controller.view.user;

import com.ivan.pazar.persistence.exceptions.UserException;
import com.ivan.pazar.persistence.model.service.register.UserServiceBindingModel;
import com.ivan.pazar.persistence.service.api.UserService;
import com.ivan.pazar.web.constants.ViewConstants;
import com.ivan.pazar.web.model.binding.UserRegisterBindingModel;
import com.ivan.pazar.web.service.api.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@PreAuthorize("isAnonymous()")
public class UserRegisterController extends UserBaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    public UserRegisterController(UserService userService, ModelMapper modelMapper,
                                  @Qualifier(value = "sha256PasswordEncoder") PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @GetMapping("/register")
    public ModelAndView register(Model model) {
        if (!model.containsAttribute(ViewConstants.INVALID_USER_FORM)) {
            model.addAttribute(ViewConstants.INVALID_USER_FORM, new UserRegisterBindingModel());
            model.addAttribute(ViewConstants.ERRORS, Collections.emptyList());
        }

        return renderView(ViewConstants.VIEWS_USER_REGISTER, model);
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute(ViewConstants.USER) @Valid UserRegisterBindingModel userRegisterBindingModel,
                                        BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return renderView(ViewConstants.VIEWS_USER_REGISTER, model);
        }

        try {
            encodePasswords(userRegisterBindingModel);
            userService.save(modelMapper.map(userRegisterBindingModel, UserServiceBindingModel.class));
        } catch (UserException e) {
            model.addAttribute(ViewConstants.PASSWORDS_NOT_MATCH, e.getMessage());
            return renderView(ViewConstants.VIEWS_USER_REGISTER, model);
        }
        try {
            emailService.sendNotificationForRegistering(userRegisterBindingModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return redirect(ViewConstants.REDIRECT_INDEX);
    }

    @ModelAttribute(ViewConstants.USER)
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }

    private void encodePasswords(UserRegisterBindingModel userRegisterBindingModel) {
        userRegisterBindingModel.setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()));
        userRegisterBindingModel.setConfirmPassword(passwordEncoder.encode(userRegisterBindingModel.getConfirmPassword()));
    }
}