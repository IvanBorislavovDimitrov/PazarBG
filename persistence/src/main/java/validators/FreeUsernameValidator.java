package validators;

import com.ivan.pazar.persistence.service.api.UserService;
import com.ivan.pazar.persistence.validation_annotations.FreeUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class FreeUsernameValidator implements ConstraintValidator<FreeUsername, String> {

    private final UserService userService;

    @Autowired
    public FreeUsernameValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return userService.isUsernameFree(username);
    }
}
