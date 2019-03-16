package validators;

import com.ivan.pazar.persistence.service.api.UserService;
import com.ivan.pazar.persistence.validation_annotations.FreePhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class FreePhoneNumberValidator implements ConstraintValidator<FreePhoneNumber, String> {

    private final UserService userService;

    @Autowired
    public FreePhoneNumberValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        return userService.isPhoneNumberFree(phoneNumber);
    }
}
