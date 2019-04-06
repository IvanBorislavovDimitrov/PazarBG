package com.ivan.pazar.web.controller.view.user;

import com.ivan.pazar.persistence.exceptions.user.UserException;
import com.ivan.pazar.persistence.model.service.UserServiceModel;
import com.ivan.pazar.persistence.model.service.register.UserServiceBindingModel;
import com.ivan.pazar.persistence.service.api.UserService;
import com.ivan.pazar.web.constants.WebConstants;
import com.ivan.pazar.web.model.binding.UserRegisterBindingModel;
import com.ivan.pazar.web.service.api.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
                                  PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @GetMapping("/register")
    public ModelAndView register(Model model) {
        if (!model.containsAttribute(WebConstants.INVALID_USER_FORM)) {
            model.addAttribute(WebConstants.INVALID_USER_FORM, new UserRegisterBindingModel());
            model.addAttribute(WebConstants.ERRORS, Collections.emptyList());
        }

        return renderView(WebConstants.VIEWS_USER_REGISTER, model);
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute(WebConstants.USER) @Valid UserRegisterBindingModel userRegisterBindingModel,
                                        BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return renderView(WebConstants.VIEWS_USER_REGISTER, model);
        }

        try {
            UserServiceModel savedUser = userService.save(modelMapper.map(userRegisterBindingModel, UserServiceBindingModel.class));
            emailService.sendNotificationForRegistering(userRegisterBindingModel, savedUser.getId());

        } catch (UserException e) {
            model.addAttribute(WebConstants.PASSWORDS_NOT_MATCH, e.getMessage());
            return renderView(WebConstants.VIEWS_USER_REGISTER, model);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return redirect(WebConstants.REDIRECT_INDEX);
    }

    @GetMapping("/activate/{id}")
    public ModelAndView activateUser(@PathVariable("id") String id) {
        userService.activateUser(id);

        return redirect(WebConstants.REDIRECT_INDEX);
    }

    @ModelAttribute(WebConstants.USER)
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }
}