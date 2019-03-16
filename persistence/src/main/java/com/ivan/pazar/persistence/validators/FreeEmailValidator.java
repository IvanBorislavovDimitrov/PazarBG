package com.ivan.pazar.persistence.validators;

import com.ivan.pazar.persistence.service.api.UserService;
import com.ivan.pazar.persistence.validation_annotations.FreeEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class FreeEmailValidator implements ConstraintValidator<FreeEmail, String> {

    private final UserService userService;

    @Autowired
    public FreeEmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return userService.isEmailFree(email);
    }
}
