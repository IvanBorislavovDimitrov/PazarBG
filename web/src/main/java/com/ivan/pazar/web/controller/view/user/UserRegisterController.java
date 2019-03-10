package com.ivan.pazar.web.controller.view.user;

import com.ivan.pazar.persistence.dto.binding.UserRegisterBindingModel;
import com.ivan.pazar.persistence.dto.service.register.UserRegisterServiceModel;
import com.ivan.pazar.persistence.dto.view.UserRegisterError;
import com.ivan.pazar.persistence.exceptions.UserException;
import com.ivan.pazar.persistence.service.api.UserService;
import com.ivan.pazar.web.constants.ViewConstants;
import com.ivan.pazar.web.controller.view.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserRegisterController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRegisterController(UserService userService, ModelMapper modelMapper,
                                  @Qualifier(value = "sha256PasswordEncoder") PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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
    public ModelAndView registerConfirm(@ModelAttribute @Valid UserRegisterBindingModel userRegisterBindingModel,
                                        BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(ViewConstants.INVALID_USER_FORM, userRegisterBindingModel);
            redirectAttributes.addFlashAttribute(ViewConstants.ERRORS, mapErrors(bindingResult.getAllErrors()));

            return redirect(ViewConstants.REDIRECT_USERS_REGISTER);
        }

        try {
            encodePasswords(userRegisterBindingModel);
            userService.save(modelMapper.map(userRegisterBindingModel, UserRegisterServiceModel.class));
        } catch (UserException e) {
            redirectAttributes.addFlashAttribute(ViewConstants.INVALID_USER_FORM, userRegisterBindingModel);
            redirectAttributes.addFlashAttribute(ViewConstants.ERROR_MESSAGE, e.getMessage());

            return redirect(ViewConstants.REDIRECT_USERS_REGISTER);
        }

        return redirect(ViewConstants.REDIRECT_INDEX);
    }

    private Set<UserRegisterError> mapErrors(List<ObjectError> allErrors) {
        return allErrors.stream()
                .map(objectError -> new UserRegisterError() {{
                    setFieldName(((FieldError) objectError).getField());
                    setErrorMessage(objectError.getDefaultMessage());
                }})
                .collect(Collectors.toSet());
    }

    private void encodePasswords(UserRegisterBindingModel userRegisterBindingModel) {
        userRegisterBindingModel.setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()));
        userRegisterBindingModel.setConfirmPassword(passwordEncoder.encode(userRegisterBindingModel.getConfirmPassword()));
    }
}