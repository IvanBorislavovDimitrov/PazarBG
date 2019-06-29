package com.ivan.pazar.persistence.validation_annotations;

import com.ivan.pazar.persistence.validators.FreePhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FreePhoneNumberValidator.class)
public @interface FreePhoneNumber {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
