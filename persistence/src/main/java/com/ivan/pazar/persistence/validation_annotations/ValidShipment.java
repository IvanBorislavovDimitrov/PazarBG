package com.ivan.pazar.persistence.validation_annotations;

import com.ivan.pazar.persistence.validators.FreeUsernameValidator;
import com.ivan.pazar.persistence.validators.ValidShipmentValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidShipmentValidator.class)
public @interface ValidShipment {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
